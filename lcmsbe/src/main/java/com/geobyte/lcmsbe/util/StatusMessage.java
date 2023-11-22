package com.geobyte.lcmsbe.util;

public class StatusMessage {	
	private String message;
	private StatusInWord statusInWord;
	
	public StatusMessage() {}
	
	public StatusMessage(String message, StatusInWord statusInWord) {
		this.message = message;
		this.statusInWord = statusInWord;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public StatusInWord getStatusInWord() {
		return statusInWord;
	}

	public void setStatusInWord(StatusInWord statusInWord) {
		this.statusInWord = statusInWord;
	}	
}
