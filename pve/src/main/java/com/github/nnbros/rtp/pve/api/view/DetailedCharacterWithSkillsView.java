package com.github.nnbros.rtp.pve.api.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nnbros.rtp.battleprocessor.core.Combatant;
import com.github.nnbros.rtp.battleprocessor.core.CombatantImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailedCharacterWithSkillsView extends DetailedCharacterView {

	@JsonProperty("activeSkills")
	private List<ActiveCharacterSkill> activeSkills;

	public CombatantImpl toCombatant() {
		int armyMaxHp = activeArmy.baseHp() * activeArmy.baseQuantity();
		return CombatantImpl.builder()
				.entityName(name)
				.archetype(activeClass.type().name())
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
