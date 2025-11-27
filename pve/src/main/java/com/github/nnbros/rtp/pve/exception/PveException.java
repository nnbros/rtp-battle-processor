package com.github.nnbros.rtp.pve.exception;

import com.github.nnbros.rtp.common.exception.RtpException;
import org.springframework.http.HttpStatus;

public class PveException extends RtpException {

	public PveException() {
	}

	public PveException(String message, Object... params) {
		super(message, params);
	}

	public PveException(String message, Throwable cause, Object... params) {
		super(message, cause, params);
	}

	public PveException(Throwable cause) {
		super(cause);
	}

	public HttpStatus getStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
