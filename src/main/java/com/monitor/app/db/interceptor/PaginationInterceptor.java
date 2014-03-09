package com.monitor.app.db.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.monitor.app.dao.common.Pagination;
import com.monitor.app.db.interceptor.dialect.Dialect;
import com.monitor.app.db.interceptor.dialect.MySqlDialect;

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PaginationInterceptor implements Interceptor{

	    private final static Log log = LogFactory.getLog(PaginationInterceptor.class);
	    private static final String PAGE_SQL_MATCH_ID = "pageSqlId";  
	    
	    @Override
	    public Object intercept(Invocation invocation) throws Throwable {
	
	       StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
	       BoundSql boundSql = statementHandler.getBoundSql();
	       MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);
	       RowBounds rowBounds = (RowBounds)metaStatementHandler.getValue("delegate.rowBounds");
	       Configuration configuration = (Configuration)metaStatementHandler.getValue("delegate.configuration");
	       MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");  
	       
	       //1.过滤需要分页的sql
	       String pageSqlId = configuration.getVariables().getProperty(PAGE_SQL_MATCH_ID);  
	       if(!mappedStatement.getId().matches(pageSqlId)){  
	    	   return invocation.proceed();
	       }
	       
	       //2.获取分页数据
	       Object pagination = boundSql.getParameterObject();
	       if(!(pagination instanceof Pagination)){
	    	 //无分页。。
		       if(rowBounds ==null|| rowBounds == RowBounds.DEFAULT){
		           return invocation.proceed();
		       }
	       }else{
	    	   Pagination pageObj = (Pagination)pagination;
	    	   rowBounds = new RowBounds((pageObj.getCurrentPage()-1)*10, 10);
	       }
	       
	
	       Dialect.Type databaseType  =null;
	       try{
	           databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
	       } catch(Exception e){
	    	   log.error("pagination_dialect_exception",e);
	       }
	
	       if(databaseType ==null){
	           throw new RuntimeException("the value of the dialect property in configuration.xml is not defined : "+ configuration.getVariables().getProperty("dialect"));
	       }
	
	       Dialect dialect =null;
	       switch(databaseType){
	           case MYSQL:
	              dialect = MySqlDialect.getInstance();
	              break;
	           default:
	        	  dialect= MySqlDialect.getInstance();
	       }
	
	       String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
	       metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, rowBounds.getOffset(), rowBounds.getLimit()) );
	       //metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET );
	       //metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT );
	
	       if(log.isDebugEnabled()){
	           log.debug("生成分页SQL : "+ boundSql.getSql());
	       }
	
	       return invocation.proceed();
	    }
	
	    @Override
	    public Object plugin(Object target) {
	
	       return Plugin.wrap(target, this);
	    }
	 
	    @Override
	    public void setProperties(Properties properties) {
	
	    }
}

