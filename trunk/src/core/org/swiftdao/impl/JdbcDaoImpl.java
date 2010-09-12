package org.swiftdao.impl;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import org.swiftdao.JdbcDao;
import org.swiftdao.exception.InvalidParameterException;

/**
 * 基于JDBC的DAO基础实现，提供了一些用于JDBC操作的常用方法。<BR>
 * 所有方法都必须声明为抛出Spring统一的DataAccessException异常，以便于做统一的异常处理。<BR>
 * 可用的异常类如下：
 * 
 * <pre>
 * DataAccessException
 *    CleanupFailureDataAccessException
 *    ConcurrencyFailureException
 *         OptimisticLockingFailureException
 *              ObjectOptimisticLockingFailureException
 *                   HibernateOptimisticLockingFailureException
 *         PessimisticLockingFailureException
 *              CannotAcquireLockException
 *              CannotSerializeTransactionException
 *              DeadlockLoserDataAccessException
 *    DataAccessResourceFailureException
 *         CannotCreateRecordException
 *         CannotGetCciConnectionException
 *         CannotGetJdbcConnectionException
 *    DataIntegrityViolationException
 *    DataRetrievalFailureException
 *         IncorrectResultSetColumnCountException
 *         IncorrectResultSizeDataAccessException
 *              EmptyResultDataAccessException
 *         LobRetrievalFailureException
 *         ObjectRetrievalFailureException
 *              HibernateObjectRetrievalFailureException
 *    DataSourceLookupFailureException
 *    InvalidDataAccessApiUsageException
 *    InvalidDataAccessResourceUsageException
 *         BadSqlGrammarException
 *         CciOperationNotSupportedException
 *         HibernateQueryException
 *         IncorrectUpdateSemanticsDataAccessException
 *              JdbcUpdateAffectedIncorrectNumberOfRowsException
 *         InvalidResultSetAccessException
 *         RecordTypeNotSupportedException
 *         TypeMismatchDataAccessException
 *    PermissionDeniedDataAccessException
 *    UncategorizedDataAccessException
 *         HibernateJdbcException
 *         HibernateSystemException
 *         SQLWarningException
 *         UncategorizedSQLException
 * </pre>
 * 
 * @author Wang Yuxing
 * @version 1.0
 */
public abstract class JdbcDaoImpl extends SimpleJdbcDaoSupport implements JdbcDao {

	protected Logger logger = null;
	protected static final String DEFAULT_DB_ENCODING = "ISO-8859-1";
	protected static final String DEFAULT_UI_ENCODING = "GBK";
	private static final int ORACLE_TYPE_CURSOR = -10;
//	protected static DataSource dataSource = null;
	protected SimpleJdbcCall simpleJdbcCall;

	public JdbcDaoImpl() {
		logger = LogManager.getLogger(this.getClass().getName());
	}

	public int[] batchUpdate(String sql, final List<List<Object>> parameters) throws DataAccessException {
		if (sql == null) {
			throw new BadSqlGrammarException("Batch Update", sql, null);
		}
		if (parameters == null || parameters.size() <= 0) {
			throw new InvalidParameterException("Invalid parameters for batch update");
		}
		int[] counts = this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			public int getBatchSize() {
				return parameters.size();
			}

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				List<Object> row = parameters.get(i);
				for (int j = 0; j < row.size(); j++) {
					ps.setObject(j + 1, row.get(j));
				}
			}
		});
		return counts;
	}

	public void execute(String spName) throws DataAccessException {
		// this.execute(spName, null);
		this.getSimpleJdbcTemplate().update("call " + spName + "()");
	}

	public void execute(String spName, Map<String, Object> parameters) throws DataAccessException {
		this.executeWithResult(spName, parameters, null);
	}

	public Map<String, Object> executeWithResult(String spName, Map<String, Object> parameters,
			Map<String, Integer> outParams, String cursorName) throws DataAccessException {
		return executeWithResult(null, spName, parameters, outParams, cursorName);
	}

	public Map<String, Object> executeWithResult(Connection conn, String spName, Map<String, Object> parameters,
			Map<String, Integer> outParams, String cursorName) throws DataAccessException {
		if (StringUtils.isEmpty(spName)) {
			throw new InvalidParameterException("Invalid stored procedure name");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Call SP: " + spName);
			logger.debug("with parameters: " + parameters);
		}

		CallableStatement cmt = null;
		ResultSet set = null;
		if (conn == null) {
			conn = this.getConnection();
		}
		int pCount = ((parameters == null) ? 0 : parameters.size()) + ((outParams == null) ? 0 : outParams.size());
		StringBuilder sb = new StringBuilder(50);

		sb.append("{ call ").append(spName).append("(");
		for (int i = 0; i < pCount; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("?");
		}
		sb.append(") }");

		String sql = sb.toString();
		Map<String, Object> ret = new TreeMap<String, Object>();
		try {
			cmt = conn.prepareCall(sql);
			if (parameters != null && parameters.size() > 0) {
				Set<String> keys = parameters.keySet();
				for (String key : keys) {
					Object value = parameters.get(key);
					cmt.setObject(key, value, JdbcHelper.translateType(value));
				}
			}
			if (outParams != null && outParams.size() > 0) {
				Set<String> keys = outParams.keySet();
				for (String key : keys) {
					Integer value = outParams.get(key);
					// logger.debug("Register Out Parameter: " + key + " " + value);
					cmt.registerOutParameter(key, value.intValue());
				}
			}
			try {
				cmt.execute();
			} catch (RuntimeException e) {
				logger.error(e.getMessage() + e.getStackTrace());
				return null;
			}
			if (outParams != null && outParams.size() > 0) {
				Set<String> keys = outParams.keySet();
				for (String key : keys) {
					// logger.debug("Out param key:" + key);
					if (key.equals(cursorName)) {
						continue;
					}
					Object value = cmt.getObject(key);
					if (value != null) {
						// logger.debug("Type of value: " + value.getClass());
						ret.put(key, value);
					}
				}
			}
			// cusor to a map.
			if (cursorName != null) {
				ResultSet rs;
				try {
					rs = (ResultSet) cmt.getObject(cursorName);
				} catch (SQLException e) {
					logger.info("存储过程没有可以打开的游标");
					return ret;
				}
				if (rs != null) {
					List<Map> data = new ArrayList<Map>();
					while (rs.next()) {
						Map<String, Object> row = new HashMap<String, Object>();
						int n = rs.getMetaData().getColumnCount();
						for (int i = 0; i < n; i++) {
							row.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1));
						}
						data.add(row);
					}
					ret.put(cursorName, data);
				}
			}
		} catch (Throwable e) {
			throw new DataRetrievalFailureException("ERROR", e);
		} finally {
			try {
				if (cmt != null) {
					cmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();//关闭Statement异常
			}
		}
		return ret;
	}

	// This method dosn't work. fix it while Oracle driver is OK!
	public Map<String, Object> executeWithResult(
			String spName,
			Map<String, Object> parameters,
			Map<String, Integer> outParams) throws DataAccessException {
		if (spName == null) {
			throw new InvalidParameterException("Invalid stored procedure name");
		}
		Map<String, Object> ret = new TreeMap<String, Object>();
		if (simpleJdbcCall == null || !spName.equals(simpleJdbcCall.getProcedureName())) {
			simpleJdbcCall = new SimpleJdbcCall(this.getDataSource()).withProcedureName(spName).withoutProcedureColumnMetaDataAccess();
		}

		MapSqlParameterSource in = null;
		if (parameters != null && parameters.size() > 0) {
			Set<String> keys = parameters.keySet();
			in = new MapSqlParameterSource();
			for (String key : keys) {
				Object value = parameters.get(key);
				logger.debug("Register In Parameter: " + key + " " + JdbcHelper.translateType(value));
				// simpleJdbcCall.declareParameters(new SqlParameter(key, JdbcHelper.translateType(value)));
				in.addValue(key, value);
			}
		}

		if (outParams != null && outParams.size() > 0) {
			Set<String> keys = outParams.keySet();
			for (String key : keys) {
				Integer value = outParams.get(key);
				logger.debug("Register Out Parameter: " + key + " " + value);
				simpleJdbcCall.declareParameters(new SqlOutParameter(key, value.intValue()));
			}
		}

		Map<String, Object> listByCursor = null;
		simpleJdbcCall.returningResultSet("prCursor", new ParameterizedRowMapper<Map<String, Object>>() {

			public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				logger.debug("Map Rows: " + rs.getFetchSize());
				Map<String, Object> list = new HashMap<String, Object>();
				while (rs.next()) {
					int n = rs.getMetaData().getColumnCount();
					for (int i = 0; i < n; i++) {
						list.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(1 + 1));
					}
				}
				return list;
			}
		});
		if (in == null) {
			ret = simpleJdbcCall.execute();
		} else {
			ret = simpleJdbcCall.execute(in);
			listByCursor = simpleJdbcCall.executeFunction(Map.class, Collections.EMPTY_MAP);
		}
		ret.put("prCursor", listByCursor);
		return ret;
	}

	@SuppressWarnings("unchecked")
	public List executeWithResultset(String spName) throws DataAccessException {
		return executeWithResultset(spName, null);
	}

	@SuppressWarnings("unchecked")
	public List executeWithResultset(String spName, Map<String, Object> parameters) throws DataAccessException {
		throw new RuntimeException("Not implemented yet");
	}

	/**
	 *
	 * Tools for converting encoding of text from db.
	 * @param src
	 * @param targetEncoding
	 * @return
	 */
	protected String convertFromDB(String src, String targetEncoding) {
		if(StringUtils.isEmpty(src)) {
			return StringUtils.EMPTY;
		}
		String result = null;
		try {
			result = new String(src.getBytes(DEFAULT_DB_ENCODING), targetEncoding);
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("Convert string from db error!");
		}
		return result;
	}

	/**
	 * Tools for converting encoding of text from db.
	 * @param src
	 * @return
	 */
	protected String convertFromDB(String src) {
		if(StringUtils.isEmpty(src)) {
			return StringUtils.EMPTY;
		}
		String result = null;
		try {
			result = new String(src.getBytes(DEFAULT_DB_ENCODING), DEFAULT_UI_ENCODING);
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("Convert string from db error!");
		}
		return result;
	}

	/**
	 * Tools for converting encoding of text to db.
	 * @param src
	 */
	protected String convertToDB(String src) {
		if(StringUtils.isEmpty(src)) {
			return StringUtils.EMPTY;
		}
		String result = null;
		try {
			result = new String(src.getBytes(DEFAULT_UI_ENCODING), DEFAULT_DB_ENCODING);
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("Convert string to db error!");
		}
		return result;
	}
}
