package org.swiftdao.pojo;

import javax.persistence.*;

/**
 * 统一定义id的entity基类
 *
 * @author sun.xc
 */
@MappedSuperclass
public class BaseKeyedEntity implements KeyedPojo<Long> {

	/**
	 * 主键ID - N_ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_GEN")
	@Column(name = "N_ID")
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}