package com.isban.javaapps.reporting.exception;

public class ExcelFileUtilException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Exception thrownException;

	// Constructors
	public ExcelFileUtilException() {

	}

	public ExcelFileUtilException(String message) {
		super(message);
	}

	public ExcelFileUtilException(String message, Exception thrownException) {
		super(message);
		this.setThrownException(thrownException);
	}

	// Accessors
	public Exception getThrownException() {
		return thrownException;
	}

	public void setThrownException(Exception thrownException) {
		this.thrownException = thrownException;
	}

	// Logic
	public String parsePassedMessage(String message, Exception thrownException) {
		String parsedPassedMessage = String.format("Exception thrown: '%s'. Message: '%s'",
			thrownException != null ? thrownException.getClass().getName() : "None",
			message
		);
		return parsedPassedMessage;
	}

}
