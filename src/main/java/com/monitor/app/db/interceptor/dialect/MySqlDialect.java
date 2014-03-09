package com.monitor.app.db.interceptor.dialect;

public class MySqlDialect extends Dialect {

	private final static MySqlDialect instance = new MySqlDialect();
	
	public static Dialect getInstance(){
		return instance;
	}
	
	public boolean supportsLimitOffset() {
		return true;
	}

	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		if (offset > 0) {
			return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
		} else {
			return sql + " limit " + limitPlaceholder;
		}
	}

}