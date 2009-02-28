package org.swiftdao;

import org.swiftdao.pojo.KeyedPojo;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Wang Yuxing
 * 
 */
@Entity()
@Table(name = "UT_SINGLE_KEY_TABLE")
public class MockSingleKeyEntity implements KeyedPojo<Long> {

	protected static final long serialVersionUID = 1L;
	@Id()
	@Column(name = "UT_ID")
	protected Long id;
	@Column(name = "UT_KEY", length = 32, nullable = false)
	protected String key;
	@Column(name = "UT_S_VALUE", length = 64, nullable = true)
	protected String strValue;
	@Column(name = "UT_I_VALUE", nullable = true)
	protected int intValue;
	@Column(name = "UT_D_VALUE", nullable = true)
	protected Calendar creationTime;

	public MockSingleKeyEntity() {
		super();
	}

	public MockSingleKeyEntity(String key) {
		super();
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public Calendar getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Calendar timeValue) {
		this.creationTime = timeValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
