package org.peie.avicultura.helper;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

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
	public static final int PDF_ERROR = 2056;
	
	private Logger LOG = Logger.getLogger(getClass());
	private int errorCode;
	private String msg;
	private String errorMsg;

	public AviculturaException(int errorCode, String msg, Throwable e) {
		super(msg, e);
		this.errorCode = errorCode;
		this.msg = msg;
		errorMsg = e.getMessage();
		LOG.error(msg, e);

	}

	public int getErrorCode() {
		return errorCode;
	}

	public void viewError(Component frame) {
		JOptionPane.showMessageDialog(frame, msg + " [" + errorMsg + "]",
				"Fehler im Program", JOptionPane.ERROR_MESSAGE);
	}

}
