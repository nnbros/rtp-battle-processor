package com.github.nnbros.rtp.pve.exception;

import org.springframework.http.HttpStatus;

public class PveException extends Exception {

	public PveException() {
	}

	public PveException(String message, Object... params) {
		super(message.formatted(params));
	}

	public PveException(String message, Throwable cause, Object... params) {
		super(message.formatted(params), cause);
	}

	public PveException(Throwable cause) {
		super(cause);
	}

	public HttpStatus getStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
