package com.github.nnbros.rtp.pve.exception;

import org.springframework.http.HttpStatus;

public class ActionNotFoundException extends PveException {

	public ActionNotFoundException(String action) {
		super("Action with id [%s] was not found", action);
	}


	@Override
	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
