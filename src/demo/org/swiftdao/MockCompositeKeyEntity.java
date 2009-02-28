package org.swiftdao;

import org.swiftdao.pojo.KeyedPojo;
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
@Table(name = "DEMO_MULTI_KEY")
public class MockCompositeKeyEntity implements KeyedPojo<MockCompositeKey> {

	private static final long serialVersionUID = 1L;
	@Id
	private MockCompositeKey id;
	@Column(name = "NDEGREE", nullable = false)
	private int degree = 0;
	@Column(name = "NINDIVIDULE_DEGREE", nullable = false)
	private int individualDegree = 0;

	public MockCompositeKeyEntity() {
	}

	public MockCompositeKeyEntity(MockCompositeKey id, int degree, int individualDegree) {
		super();
		this.id = id;
		this.degree = degree;
		this.individualDegree = individualDegree;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getIndividualDegree() {
		return individualDegree;
	}

	public void setIndividualDegree(int individualDegree) {
		this.individualDegree = individualDegree;
	}

	public MockCompositeKey getId() {
		return id;
	}

	public void setId(MockCompositeKey id) {
		this.id = id;
	}
}
