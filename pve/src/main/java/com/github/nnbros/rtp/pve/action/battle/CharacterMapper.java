package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.battleprocessor.core.CombatantImpl;
import com.github.nnbros.rtp.common.api.dto.character.ActiveCharacterArmy;
import com.github.nnbros.rtp.common.api.dto.character.ActiveCharacterClass;
import com.github.nnbros.rtp.common.api.dto.character.DetailedCharacterWithSkillsView;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper implements CombatantMapper<DetailedCharacterWithSkillsView> {

	@Override
	public CombatantImpl toCombatant(DetailedCharacterWithSkillsView characterView) {
		ActiveCharacterArmy activeArmy = characterView.getActiveArmy();
		int armyMaxHp = activeArmy.baseHp() * activeArmy.baseQuantity();
		ActiveCharacterClass activeClass = characterView.getActiveClass();
		String activeClassName = activeClass.type().name();
		return CombatantImpl.builder()
				.entityName(characterView.getName())
				.archetype(activeClassName)
				.isCharacter(true)
				.maxHp(activeClass.baseHp())
				.hp(activeClass.baseHp())
				.atk(activeClass.baseAtk())
				.def(activeClass.baseDef())
				.advantageBonus(activeClass.advantageBonus())
				.armyName(activeArmy.name())
				.armyArchetype(activeArmy.type().name())
				.armyMaxHp(armyMaxHp)
				.armyHp(armyMaxHp)
				.armyAtk(activeArmy.baseAtk())
				.armyDef(activeArmy.baseDef())
				.armyQuantity(activeArmy.baseQuantity())
				.armyAdvantageBonus(activeArmy.advantageBonus())
				.aliveSoldiers(activeArmy.baseQuantity())
				.build();
	}
}
