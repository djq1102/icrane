package com.monitor.app.exception;

public class ManagerException extends Exception{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8990506601970288217L;

	public ManagerException() {
		super();
	}

	public ManagerException(String msg) {
		super(msg);
	}

	public ManagerException(Throwable cause) {
		super(cause);
	}

	public ManagerException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
