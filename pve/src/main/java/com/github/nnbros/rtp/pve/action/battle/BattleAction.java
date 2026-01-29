package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.common.action.Action;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BattleAction implements Action {
	START_BATTLE("pve_main_menu_monster_hunt"),
	BATTLE_TURN("pve_battle_turn");

	private final String actionName;
}
