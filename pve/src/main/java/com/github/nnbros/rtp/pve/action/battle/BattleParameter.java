package com.github.nnbros.rtp.pve.action.battle;

import com.github.guronas.telegram.bot.elements.parameter.ParameterKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BattleParameter implements ParameterKey {
	FIRST_COMBATANT_NAME("firstCombatantName"),
	FIRST_COMBATANT_LEVEL("firstCombatantLevel"),
	FIRST_COMBATANT_TYPE_EMOJI("firstCombatantTypeEmoji"),
	FIRST_COMBATANT_CLASS("firstCombatantClass"),
	FIRST_COMBATANT_CLASS_LEVEL("firstCombatantClassLevel"),
	FIRST_COMBATANT_HP("firstCombatantHP"),
	FIRST_COMBATANT_MAX_HP("firstCombatantMaxHP"),
	FIRST_COMBATANT_ATK("firstCombatantAtk"),
	FIRST_COMBATANT_DEF("firstCombatantDef"),
	FIRST_COMBATANT_ARMY_TYPE_EMOJI("firstCombatantArmyTypeEmoji"),
	FIRST_COMBATANT_ARMY_NAME("firstCombatantArmyName"),
	FIRST_COMBATANT_ARMY_TIER("firstCombatantArmyTier"),
	FIRST_COMBATANT_ARMY_HP("firstCombatantArmyHP"),
	FIRST_COMBATANT_ARMY_MAX_HP("firstCombatantArmyMaxHP"),
	FIRST_COMBATANT_ARMY_ATK("firstCombatantArmyAtk"),
	FIRST_COMBATANT_ARMY_DEF("firstCombatantArmyDef"),

	SECOND_COMBATANT_NAME("secondCombatantName"),
	SECOND_COMBATANT_LEVEL("secondCombatantLevel"),
	SECOND_COMBATANT_TYPE_EMOJI("secondCombatantTypeEmoji"),
	SECOND_COMBATANT_CLASS("secondCombatantClass"),
	SECOND_COMBATANT_CLASS_LEVEL("secondCombatantClassLevel"),
	SECOND_COMBATANT_HP("secondCombatantHP"),
	SECOND_COMBATANT_MAX_HP("secondCombatantMaxHP"),
	SECOND_COMBATANT_ATK("secondCombatantAtk"),
	SECOND_COMBATANT_DEF("secondCombatantDef"),
	SECOND_COMBATANT_ARMY_TYPE_EMOJI("secondCombatantArmyTypeEmoji"),
	SECOND_COMBATANT_ARMY_NAME("secondCombatantArmyName"),
	SECOND_COMBATANT_ARMY_TIER("secondCombatantArmyTier"),
	SECOND_COMBATANT_ARMY_HP("secondCombatantArmyHP"),
	SECOND_COMBATANT_ARMY_MAX_HP("secondCombatantArmyMaxHP"),
	SECOND_COMBATANT_ARMY_ATK("secondCombatantArmyAtk"),
	SECOND_COMBATANT_ARMY_DEF("secondCombatantArmyDef"),

	FIRST_COMBATANT_LAST_SKILL("firstCombatantLastSkill"),
	SECOND_COMBATANT_LAST_SKILL("secondCombatantLastSkill"),

	FIRST_COMBATANT_HERO_DAMAGE_TAKEN("firstCombatantHeroDamageTaken"),
	SECOND_COMBATANT_HERO_DAMAGE_TAKEN("secondCombatantHeroDamageTaken"),

	FIRST_COMBATANT_ARMY_TYPE_EMOJI_AFTER("firstCombatantArmyTypeEmojiAfter"),
	SECOND_COMBATANT_ARMY_TYPE_EMOJI_AFTER("secondCombatantArmyTypeEmojiAfter"),

	FIRST_COMBATANT_ARMY_DAMAGE_TAKEN("firstCombatantArmyDamageTaken"),
	SECOND_COMBATANT_ARMY_DAMAGE_TAKEN("secondCombatantArmyDamageTaken"),

	TURN("turn"),
	BATTLE_OUTCOME("battleOutcome"),
	LOOT_GAINED("lootGained"),
	YOUR_TURN_TEXT("yourTurnText");

	private final String key;
}
