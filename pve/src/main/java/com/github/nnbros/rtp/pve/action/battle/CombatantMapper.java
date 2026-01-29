package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.battleprocessor.core.CombatantImpl;

public interface CombatantMapper<T> {

	CombatantImpl toCombatant(T entity);
}
