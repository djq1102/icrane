/**
 * 
 */
package com.monitor.app.dataobject;

import java.util.Date;

/**
 * @author ibm
 * 
 */
public class Notice {

	private long noticeId;
	private long userId;
	private String toCustomerId;//多个客户id用逗号分隔

	private String title;
	private String content;

	private String noticeStart;
	private String noticeEnd;

	private Date gmtCreate;
	private Date gmtModify;

	public long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToCustomerId() {
		return toCustomerId;
	}

	public void setToCustomerId(String toCustomerId) {
		this.toCustomerId = toCustomerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNoticeStart() {
		return noticeStart;
	}

	public void setNoticeStart(String noticeStart) {
		this.noticeStart = noticeStart;
	}

	public String getNoticeEnd() {
		return noticeEnd;
	}

	public void setNoticeEnd(String noticeEnd) {
		this.noticeEnd = noticeEnd;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

}
