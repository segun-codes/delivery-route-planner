package com.geobyte.lcmsbe.util;

public class ErrorStatusMessage extends StatusMessage {
	private int statusCode;
	
	public ErrorStatusMessage() {}
	
	public ErrorStatusMessage(String message, int statusCode, StatusInWord statusInWord) {
		super(message, statusInWord);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
