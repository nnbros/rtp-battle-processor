package com.github.nnbros.rtp.pve.exception;

public class PveRuntimeException extends RuntimeException {
	public PveRuntimeException() {
	}

	public PveRuntimeException(String message, Object... params) {
		super(message.formatted(params));
	}

	public PveRuntimeException(String message, Throwable cause, Object... params) {
		super(message.formatted(params), cause);
	}

	public PveRuntimeException(Throwable cause) {
		super(cause);
	}
}
