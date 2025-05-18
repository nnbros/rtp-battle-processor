package com.github.nnbros.rtp.battleprocessor.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.python.core.PyObject;
import org.python.core.PyType;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DuelParticipantsImpl extends PyObject implements DuelParticipants {
	private final CombatantImpl firstCombatant;
	private final CombatantImpl secondCombatant;

	public DuelParticipantsImpl(CombatantImpl firstCombatant, CombatantImpl secondCombatant) {
		super(PyType.fromClass(DuelParticipantsImpl.class));
		this.firstCombatant = firstCombatant;
		this.secondCombatant = secondCombatant;
	}
}
