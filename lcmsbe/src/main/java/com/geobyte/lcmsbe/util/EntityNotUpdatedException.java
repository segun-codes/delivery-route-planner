package com.geobyte.lcmsbe.util;

public class EntityNotUpdatedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntityNotUpdatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotUpdatedException(String message) {
		super(message);
	}

	public EntityNotUpdatedException(Throwable cause) {
		super(cause);
	}
}

