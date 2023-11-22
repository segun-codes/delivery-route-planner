package com.geobyte.lcmsbe.util;

public class RoutesTableSetupException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RoutesTableSetupException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoutesTableSetupException(String message) {
		super(message);
	}

	public RoutesTableSetupException(Throwable cause) {
		super(cause);
	}
}

