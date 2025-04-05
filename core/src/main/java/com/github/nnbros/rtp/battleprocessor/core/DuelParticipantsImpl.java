package com.github.nnbros.rtp.battleprocessor.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.python.core.PyObject;
import org.python.core.PyType;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class DuelParticipantsImpl extends PyObject implements DuelParticipants {
	private final Combatant firstCombatant;
	private final Combatant secondCombatant;

	public DuelParticipantsImpl(Combatant firstCombatant, Combatant secondCombatant) {
		super(PyType.fromClass(DuelParticipantsImpl.class));
		this.firstCombatant = firstCombatant;
		this.secondCombatant = secondCombatant;
	}
}
