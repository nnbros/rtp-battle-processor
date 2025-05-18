package com.github.nnbros.rtp.battleprocessor.core;

import lombok.*;
import org.python.core.PyObject;
import org.python.core.PyType;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class CombatantImpl extends PyObject implements Combatant {

	public CombatantImpl(
			String entityName,
			String archetype,
			int maxHp,
			int hp,
			int atk,
			int def,
			float advantageBonus,
			String armyName,
			String armyArchetype,
			int armyMaxHp,
			int armyHp,
			int armyAtk,
			int armyDef,
			int armyQuantity,
			float armyAdvantageBonus,
			boolean isCharacter,
			String activeSkill,
			int receivedDmg,
			int receivedArmyDmg,
			int aliveSoldiers,
			int soldiersDelta) {
		super(PyType.fromClass(CombatantImpl.class));
		this.entityName = entityName;
		this.archetype = archetype;
		this.maxHp = maxHp;
		this.hp = hp;
		this.atk = atk;
		this.def = def;
		this.advantageBonus = advantageBonus;
		this.armyName = armyName;
		this.armyArchetype = armyArchetype;
		this.armyMaxHp = armyMaxHp;
		this.armyHp = armyHp;
		this.armyAtk = armyAtk;
		this.armyDef = armyDef;
		this.armyQuantity = armyQuantity;
		this.armyAdvantageBonus = armyAdvantageBonus;
		this.isCharacter = isCharacter;
		this.activeSkill = activeSkill;
		this.receivedDmg = receivedDmg;
		this.receivedArmyDmg = receivedArmyDmg;
		this.aliveSoldiers = aliveSoldiers;
		this.soldiersDelta = soldiersDelta;
	}

	private String entityName;
	private String archetype;
	private int maxHp;
	private int hp;
	private int atk;
	private int def;
	private float advantageBonus;
	private String armyName;
	private String armyArchetype;
	private int armyMaxHp;
	private int armyHp;
	private int armyAtk;
	private int armyDef;
	private int armyQuantity;
	private float armyAdvantageBonus;
	private boolean isCharacter;
	private String activeSkill;
	private int receivedDmg;
	private int receivedArmyDmg;
	private int aliveSoldiers;
	private int soldiersDelta;
}
