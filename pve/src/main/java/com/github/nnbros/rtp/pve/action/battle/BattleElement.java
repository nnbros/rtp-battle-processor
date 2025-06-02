package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.pve.telegram.ui.Element;

public enum BattleElement implements Element {
	battleStart,
	battleTurn,
	battleResult,
	charActiveSkills;

	public static final String BATTLE_GROUP_NAME = "battle";

	@Override
	public String getGroupName() {
		return BATTLE_GROUP_NAME;
	}
}
