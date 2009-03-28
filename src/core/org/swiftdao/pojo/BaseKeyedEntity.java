package org.swiftdao.pojo;

import javax.persistence.*;

/**
 * 具有类型为Long的主键的实体类基础类。且主键生成策略为自动。
 * 集成该类的实体类必须用annotation方式提供一个名为主键生成器，如：
 * <pre>
 *   @SequenceGenerator(name = "SEQ_GEN", sequenceName = "ORDER_SQ", allocationSize = 1)
 * 或者
 *   @GenericGenerator(name="SEQ_GEN", strategy = "increment")
 * </pre>
 *
 * @author Wang Yuxing
 */
@MappedSuperclass
public class BaseKeyedEntity implements KeyedPojo<Long> {

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
}