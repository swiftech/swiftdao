package org.swiftdao.pojo;

import javax.persistence.*;

/**
 * 统一定义id的entity基类
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