package org.swiftdao.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 具有类型为Long的主键的实体类基础类。且主键生成策略为自动。
 * 集成该类的实体类必须用annotation方式提供一个名为SEQ_GEN的主键生成器，如：
 * <pre>
 *   @SequenceGenerator(name="SEQ_GEN", sequenceName="ORDER_SQ", allocationSize=1)
 * 或者
 *   @GenericGenerator(name="SEQ_GEN", strategy = "increment")
 * </pre>
 *
 * @author Yuxing Wang
 */
@MappedSuperclass
public class BaseKeyedEntity implements KeyedPersistable<Long> {

	private static final long serialVersionUID = -8279629072801989809L;
	
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_GEN")
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}