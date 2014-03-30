/**
 * 
 */
package com.monitor.app.dao.user;

import java.util.List;

import com.monitor.app.dataobject.Notice;
import com.monitor.app.exception.DAOException;
import com.monitor.app.query.NoticeQuery;

/**
 * @author ibm
 *
 */
public interface NoticeDao {

	public int addNotice(Notice notice) throws DAOException;
	
	public int updateNotice(Notice notice) throws DAOException;
	
	public int delNotice(long noticeId) throws DAOException;
	
	public Notice queryNoticeById(long noticeId) throws DAOException;
	
	/**
	 * 分页时用。查询所有的
	 * needPagination=false
	 * 
	 * @param query
	 * @return
	 * @throws DAOException
	 */
	public List<Notice> queryNotices(NoticeQuery query) throws DAOException;
	
	/**
	 * 分页时用。查询所有的
	 * needPagination=false
	 * 
	 * @param query
	 * @return
	 */
	public int totalCount(NoticeQuery query)  throws DAOException;
}
