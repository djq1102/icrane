/**
 * 
 */
package com.monitor.app.dao.common;

import java.io.Serializable;

/**
 * @author ibm
 *
 */
public class Pagination implements Serializable{

	private static final long serialVersionUID = -1864161800532602570L;
	/**limit开始行号，0代表首行*/
	private int  begin;
	/**limit结尾行号*/
	private int end;
	
	private int currentPage;
	//default
	private int pageSize=10;
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
