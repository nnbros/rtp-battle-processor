package com.github.nnbros.rtp.battleprocessor.core;

import lombok.*;
import org.python.core.PyObject;
import org.python.core.PyType;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
public class CombatantImpl extends PyObject implements Combatant {

	public CombatantImpl(
			String entityName,
			String combatantType,
			int hp,
			int atk,
			int def,
			String armyName,
			String armyType,
			int armyHp,
			int armyAtk,
			int armyDef,
			int armyQuantity,
			boolean isCharacter,
			String activeSkill,
			int receivedDmg,
			int receivedArmyDmg) {
		super(PyType.fromClass(CombatantImpl.class));
		this.entityName = entityName;
		this.combatantType = combatantType;
		this.hp = hp;
		this.atk = atk;
		this.def = def;
		this.armyName = armyName;
		this.armyType = armyType;
		this.armyHp = armyHp;
		this.armyAtk = armyAtk;
		this.armyDef = armyDef;
		this.armyQuantity = armyQuantity;
		this.isCharacter = isCharacter;
		this.activeSkill = activeSkill;
		this.receivedDmg = receivedDmg;
		this.receivedArmyDmg = receivedArmyDmg;
	}

	private String entityName;
	private String combatantType;
	private int hp;
	private int atk;
	private int def;
	private String armyName;
	private String armyType;
	private int armyHp;
	private int armyAtk;
	private int armyDef;
	private int armyQuantity;
	private boolean isCharacter;
	private String activeSkill;
	private int receivedDmg;
	private int receivedArmyDmg;
}
