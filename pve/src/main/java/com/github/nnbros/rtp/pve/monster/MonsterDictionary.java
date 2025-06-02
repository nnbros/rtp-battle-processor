package com.github.nnbros.rtp.pve.monster;

import com.github.nnbros.rtp.battleprocessor.core.CombatantImpl;

public record MonsterDictionary(Integer id,
								String name,
								Archetype type,
								Integer baseHp,
								Integer baseAtk,
								Integer baseDef,
								String armyName,
								Archetype armyType,
								Integer armyBaseQuantity,
								Integer armyBaseHp,
								Integer armyBaseAtk,
								Integer armyBaseDef,
								Integer armyTier) {

	public CombatantImpl toCombatant() {
		return CombatantImpl.builder()
				.entityName(name)
				.archetype(type.name())
				.isCharacter(false)
				.maxHp(baseHp)
				.hp(baseHp)
				.atk(baseAtk)
				.def(baseDef)
				.armyName(armyName)
				.armyArchetype(armyType.name())
				.armyMaxHp(armyBaseHp)
				.armyHp(armyBaseHp)
				.armyAtk(armyBaseAtk)
				.armyDef(armyBaseDef)
				.armyQuantity(armyBaseQuantity)
				.aliveSoldiers(armyBaseQuantity)
				.build();
	}
}
