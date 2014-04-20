/**
 * 
 */
package com.monitor.app.query;

import java.util.Date;

import com.monitor.app.dao.common.Pagination;

/**
 * @author ibm
 *
 */
public class NoticeQuery extends Pagination {

	private static final long serialVersionUID = 3180461278973247102L;

	private long toCustomerId;
	private Date noticeStart;
	private Date noticeEnd;
	
	public Date getNoticeStart() {
		return noticeStart;
	}
	public void setNoticeStart(Date noticeStart) {
		this.noticeStart = noticeStart;
	}
	public Date getNoticeEnd() {
		return noticeEnd;
	}
	public void setNoticeEnd(Date noticeEnd) {
		this.noticeEnd = noticeEnd;
	}
	public long getToCustomerId() {
		return toCustomerId;
	}
	public void setToCustomerId(long toCustomerId) {
		this.toCustomerId = toCustomerId;
	}
	
	
}
