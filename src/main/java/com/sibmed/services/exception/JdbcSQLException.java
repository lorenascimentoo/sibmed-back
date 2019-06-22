package com.sibmed.services.exception;

public class JdbcSQLException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public JdbcSQLException(String msg) {
		super(msg);
	}
	
	public JdbcSQLException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
