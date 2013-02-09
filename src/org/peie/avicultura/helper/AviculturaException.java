package org.peie.avicultura.helper;

public class AviculturaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4932171752790986426L;
	public static final int EXIT_SUCCESS = 0;
	public static final int DB_CONNECT_FAILED = 2050;
	public static final int CLASS_NOTFOUND = 2051;
	public static final int APPLICATION_ERROR = 2052;
	public static final int IO_ERROR = 2053;
	public static final int SQL_EXECUTION_FAILED = 2054;
	public static final int SQL_PREPARATION_FAILED = 2055;

	private int errorCode;

	public AviculturaException(int errorCode, String msg, Throwable e) {
		super(msg, e);
		this.errorCode = errorCode;

	}

	public int getErrorCode() {
		return errorCode;
	}

}
