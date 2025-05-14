package com.github.nnbros.rtp.pve.exception;

public class BattleNotFoundException extends PveRuntimeException {
	public BattleNotFoundException() {
	}

	public BattleNotFoundException(long userId) {
		super("Battle for the user [%s] was not found", userId);
	}
}
