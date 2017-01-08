package com.games.exception;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 4144954378000909280L;

	private Integer errorCode;

	private Object[] args;

	public ApplicationException(Integer errorCode) {
		this(errorCode, (Object[]) null);
	}

	public ApplicationException(Integer errorCode, Object[] objects) {
		this(errorCode, (Exception) null);
		this.args = objects;
	}

	public ApplicationException(Integer errorCode, String message) {
		this(errorCode, message, (Object[]) null);
	}

	public ApplicationException(Integer errorCode, String message, Object[] objects) {
		this(errorCode, message, (Exception) null);
		this.args = objects;
	}

	public ApplicationException(Integer errorCode, Throwable t) {
		this(errorCode, t, null);
	}

	public ApplicationException(Integer errorCode, Throwable e, Object[] objects) {
		this(errorCode, null, e);
		this.args = objects;
	}

	public ApplicationException(Integer errorCode, String message, Throwable t) {
		super(message, t);
		this.errorCode = errorCode;
	}

	public ApplicationException(Integer errorCode, String message, Object[] objects, Throwable t) {
		super(message, t);
		this.errorCode = errorCode;
		this.args = objects;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public Object[] getArgs() {
		return this.args;
	}

}
