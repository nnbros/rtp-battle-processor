package com.github.nnbros.rtp.pve.exception;

import com.github.nnbros.rtp.common.exception.RtpRuntimeException;

public class PveRuntimeException extends RtpRuntimeException {
	public PveRuntimeException() {
	}

	public PveRuntimeException(String message, Object... params) {
		super(message, params);
	}

	public PveRuntimeException(String message, Throwable cause, Object... params) {
		super(message, cause, params);
	}

	public PveRuntimeException(Throwable cause) {
		super(cause);
	}
}
