package org.swiftdao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Wang Yuxing
 * 
 */
@Embeddable
public class MockCompositeKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NPAGE_ID", nullable = false)
	private int pageId = 0;
	
	@Column(name = "DTIME_PERIOD", nullable = false)
	private Date timePeriod;

	public MockCompositeKey() {

	}

	public MockCompositeKey(int pageId, Date timePeriod) {
		super();
		this.pageId = pageId;
		this.timePeriod = timePeriod;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public Date getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(Date timePeriod) {
		this.timePeriod = timePeriod;
	}

	@Override
	public boolean equals(Object obj) {
		MockCompositeKey target = (MockCompositeKey) obj;
		return this.pageId == target.getPageId() && this.timePeriod.equals(target.getTimePeriod());
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(this.pageId).hashCode() ^ this.getTimePeriod().hashCode();
	}
}
