package com.github.nnbros.rtp.pve.monster;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Archetype {
	NEUTRAL("🥋"),
	SWORDSMAN("🗡"),
	CAVALRY("🏇"),
	SPEARMAN("🔱");

	private final String emoji;
}
