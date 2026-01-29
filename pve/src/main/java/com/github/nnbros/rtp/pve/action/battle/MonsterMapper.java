package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.battleprocessor.core.CombatantImpl;
import com.github.nnbros.rtp.pve.monster.MonsterDictionary;
import org.springframework.stereotype.Component;

@Component
public class MonsterMapper implements CombatantMapper<MonsterDictionary> {

	@Override
	public CombatantImpl toCombatant(MonsterDictionary monsterDictionary) {
		Integer armyBaseQuantity = monsterDictionary.armyBaseQuantity();
		int armyMaxHp = monsterDictionary.armyBaseHp() * armyBaseQuantity;
		return CombatantImpl.builder()
				.entityName(monsterDictionary.name())
				.archetype(monsterDictionary.type().name())
				.isCharacter(false)
				.maxHp(monsterDictionary.baseHp())
				.hp(monsterDictionary.baseHp())
				.atk(monsterDictionary.baseAtk())
				.def(monsterDictionary.baseDef())
				.armyName(monsterDictionary.armyName())
				.armyArchetype(monsterDictionary.armyType().name())
				.armyMaxHp(armyMaxHp)
				.armyHp(armyMaxHp)
				.armyAtk(monsterDictionary.armyBaseAtk())
				.armyDef(monsterDictionary.armyBaseDef())
				.armyQuantity(armyBaseQuantity)
				.aliveSoldiers(armyBaseQuantity)
				.build();
	}
}
