package com.github.nnbros.rtp.battleprocessor.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Function {
	calculateDuelTurn("calculate_duel_turn");

	private final String functionName;
}
