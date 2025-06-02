package com.github.nnbros.rtp.pve.action.battle;

import com.github.guronas.telegram.bot.elements.TelegramElementRegistry;
import com.github.guronas.telegram.bot.elements.parameter.DynamicParameters;
import com.github.guronas.telegram.bot.elements.parameter.InlineKeyboardButtonParameters;
import com.github.guronas.telegram.bot.elements.parameter.Parameter;
import com.github.guronas.telegram.bot.elements.parameter.Parameters;
import com.github.nnbros.rtp.battleprocessor.core.Combatant;
import com.github.nnbros.rtp.battleprocessor.core.DuelParticipants;
import com.github.nnbros.rtp.battleprocessor.core.DuelParticipantsImpl;
import com.github.nnbros.rtp.pve.action.ActionContext;
import com.github.nnbros.rtp.pve.action.ActionResult;
import com.github.nnbros.rtp.pve.configuration.Localization;
import com.github.nnbros.rtp.pve.exception.BattleNotFoundException;
import com.github.nnbros.rtp.pve.monster.Archetype;
import com.github.nnbros.rtp.pve.telegram.AbstractTelegramClient;
import com.github.nnbros.rtp.pve.telegram.ui.DefaultParameter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.*;

import static com.github.nnbros.rtp.pve.action.battle.BattleAction.BATTLE_TURN;
import static com.github.nnbros.rtp.pve.action.battle.BattleParameter.*;

@Service
public class BattleTelegramClient extends AbstractTelegramClient {
	public static final String DEAD_SOLDIER_EMOJI = "☠️";
	public static final String EMPTY_STRING = "";

	private final Localization localization;

	public BattleTelegramClient(TelegramClient telegramClient, TelegramElementRegistry elementRegistry, Localization localization) {
		super(telegramClient, elementRegistry);
		this.localization = localization;
	}

	public void sendInitialBattleMessage(ActionResult<Battle> actionResult) {
		ActionContext actionContext = actionResult.getActionContext();
		Long userId = actionContext.userId();
		Battle battle = Optional.ofNullable(actionResult.getValue())
				.orElseThrow(() -> new BattleNotFoundException(userId));
		DuelParticipantsImpl duelParticipants = battle.getDuelParticipants();

		Map<String, String> params = buildBattleParameters(duelParticipants, userId, battle.getCharacterClass());
		Map<String, DynamicParameters> dynamicParams = buildSkillButtons(battle.getActiveSkills());
		BotApiMethod<?> message = buildBotApiMethod(BattleElement.BATTLE_GROUP_NAME, BattleElement.battleStart.name(), params, dynamicParams);
		execute(message);
	}

	public void sendTurnOptionsMenu(ActionResult<Battle> actionResult) {
		ActionContext actionContext = actionResult.getActionContext();
		Long userId = actionContext.userId();
		Battle battle = Optional.ofNullable(actionResult.getValue())
				.orElseThrow(() -> new BattleNotFoundException(userId));
		sendTurnResults(actionContext, battle, true);
	}

	public void sendResultMessage(ActionResult<Battle> actionResult) {
		ActionContext actionContext = actionResult.getActionContext();
		Long userId = actionContext.userId();
		Battle battle = Optional.ofNullable(actionResult.getValue())
				.orElseThrow(() -> new BattleNotFoundException(userId));
		sendTurnResults(actionContext, battle, false);

		DuelParticipantsImpl duelParticipants = battle.getDuelParticipants();
		Map<String, String> params = buildBattleResultParameters(duelParticipants, userId, battle.getCharacterClass(), battle.getBattleOutcome());
		BotApiMethod<?> message = buildBotApiMethod(BattleElement.BATTLE_GROUP_NAME, BattleElement.battleResult.name(), params);
		execute(message);
	}

	private void sendTurnResults(ActionContext actionContext, Battle battle, boolean shouldSentTurnOptions) {
		Long userId = actionContext.userId();
		DuelParticipantsImpl duelParticipants = battle.getDuelParticipants();

		Map<String, String> params = buildBattleTurnParameters(duelParticipants, battle.getCurrentTurn(), userId, battle.getCharacterClass());
		Map<String, DynamicParameters> dynamicParams;
		if (shouldSentTurnOptions) {
			dynamicParams = buildSkillButtons(battle.getActiveSkills());
		} else {
			params.put(YOUR_TURN_TEXT.getKey(), EMPTY_STRING);
			dynamicParams = Collections.emptyMap();
		}
		BotApiMethod<?> message = buildBotApiMethod(BattleElement.BATTLE_GROUP_NAME, BattleElement.battleTurn.name(), params, dynamicParams);
		sendRemoveInlineKeyboardMessage(userId, actionContext.messageId());
		execute(message);
	}

	private Map<String, DynamicParameters> buildSkillButtons(Collection<String> skills) {
		InlineKeyboardButtonParameters inlineKeyboardButtonParameters = new InlineKeyboardButtonParameters();
		Map<String, Localization.Skill> localizedSkills = localization.getSkills();
		skills.forEach(skill -> inlineKeyboardButtonParameters.add(localizedSkills.get(skill).getName(), CALLBACK_DATA_TEMPLATE.formatted(BATTLE_TURN.getActionName(), skill)));

		return Map.of(BattleElement.charActiveSkills.name(), inlineKeyboardButtonParameters);
	}

	private String buildArmyStrengthEmojis(int armyQuantity, int aliveSoldiers, int soldiersDelta, String armyArchetype) {
		aliveSoldiers += soldiersDelta;
		int aliveSoldiersEmojis = 0;
		if (armyQuantity > 0) {
			double ratio = (double) aliveSoldiers / armyQuantity;
			aliveSoldiersEmojis = (int) Math.ceil(ratio * armyQuantity);
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < armyQuantity; i++) {
			String emoji = armyQuantity - i > aliveSoldiersEmojis ? DEAD_SOLDIER_EMOJI : Archetype.valueOf(armyArchetype).getEmoji();
			stringBuilder.append(emoji);
		}
		return stringBuilder.toString();
	}

	private Map<String, String> buildBattleParameters(DuelParticipants duelParticipants, Long userId, String characterClass) {
		Combatant firstCombatant = duelParticipants.getFirstCombatant();
		Combatant secondCombatant = duelParticipants.getSecondCombatant();
		Map<String, Localization.Monster> monsters = localization.getMonsters();
		String firstCombatantEntityName = firstCombatant.getEntityName();
		String secondCombatantEntityName = secondCombatant.getEntityName();
		Map<String, Localization.Army> armies = localization.getArmies();
		String firstArmyStrengthEmojis = buildArmyStrengthEmojis(firstCombatant.getArmyQuantity(), firstCombatant.getAliveSoldiers(), 0, firstCombatant.getArmyArchetype());
		String secondArmyStrengthEmojis = buildArmyStrengthEmojis(secondCombatant.getArmyQuantity(), secondCombatant.getAliveSoldiers(), 0, secondCombatant.getArmyArchetype());
		Map<String, Localization.Clazz> classes = localization.getClasses();
		String firstArmyStrengthEmojisAfter = buildArmyStrengthEmojis(firstCombatant.getArmyQuantity(), firstCombatant.getAliveSoldiers(), firstCombatant.getSoldiersDelta(), firstCombatant.getArmyArchetype());
		String secondArmyStrengthEmojisAfter = buildArmyStrengthEmojis(secondCombatant.getArmyQuantity(), secondCombatant.getAliveSoldiers(), secondCombatant.getSoldiersDelta(), secondCombatant.getArmyArchetype());

		//TODO refactor when all parameters are available
		return Parameters.buildParameters(
				Parameter.of(DefaultParameter.CHAT_ID, userId),
				Parameter.of(FIRST_COMBATANT_NAME, firstCombatant.isCharacter() ? firstCombatantEntityName : monsters.get(firstCombatantEntityName).getName()),
				Parameter.of(FIRST_COMBATANT_LEVEL, "0"),
				Parameter.of(FIRST_COMBATANT_CLASS_LEVEL, "0"),
				Parameter.of(FIRST_COMBATANT_TYPE_EMOJI, Archetype.valueOf(firstCombatant.getArchetype()).getEmoji()),
				Parameter.of(FIRST_COMBATANT_CLASS, firstCombatant.isCharacter() ? classes.get(characterClass).getName() : EMPTY_STRING),
				Parameter.of(FIRST_COMBATANT_HP, firstCombatant.getHp()),
				Parameter.of(FIRST_COMBATANT_MAX_HP, firstCombatant.getMaxHp()),
				Parameter.of(FIRST_COMBATANT_ATK, firstCombatant.getAtk()),
				Parameter.of(FIRST_COMBATANT_DEF, firstCombatant.getDef()),
				Parameter.of(FIRST_COMBATANT_ARMY_TYPE_EMOJI, firstArmyStrengthEmojis),
				Parameter.of(FIRST_COMBATANT_ARMY_NAME, armies.get(firstCombatant.getArmyName()).getName()),
				Parameter.of(FIRST_COMBATANT_ARMY_HP, firstCombatant.getArmyHp()),
				Parameter.of(FIRST_COMBATANT_ARMY_MAX_HP, firstCombatant.getArmyMaxHp()),
				Parameter.of(FIRST_COMBATANT_ARMY_ATK, firstCombatant.getArmyAtk()),
				Parameter.of(FIRST_COMBATANT_ARMY_DEF, firstCombatant.getArmyDef()),
				Parameter.of(FIRST_COMBATANT_ARMY_TIER, "0"),

				Parameter.of(SECOND_COMBATANT_NAME, secondCombatant.isCharacter() ? secondCombatantEntityName : monsters.get(secondCombatantEntityName).getName()),
				Parameter.of(SECOND_COMBATANT_TYPE_EMOJI, Archetype.valueOf(secondCombatant.getArchetype()).getEmoji()),
				Parameter.of(SECOND_COMBATANT_LEVEL, "0"),
				Parameter.of(SECOND_COMBATANT_CLASS_LEVEL, "0"),
				Parameter.of(SECOND_COMBATANT_CLASS, secondCombatant.isCharacter() ? classes.get(characterClass).getName() : EMPTY_STRING),
				Parameter.of(SECOND_COMBATANT_HP, secondCombatant.getHp()),
				Parameter.of(SECOND_COMBATANT_MAX_HP, secondCombatant.getMaxHp()),
				Parameter.of(SECOND_COMBATANT_ATK, secondCombatant.getAtk()),
				Parameter.of(SECOND_COMBATANT_DEF, secondCombatant.getDef()),
				Parameter.of(SECOND_COMBATANT_ARMY_TYPE_EMOJI, secondArmyStrengthEmojis),
				Parameter.of(SECOND_COMBATANT_ARMY_NAME, armies.get(secondCombatant.getArmyName()).getName()),
				Parameter.of(SECOND_COMBATANT_ARMY_HP, secondCombatant.getArmyHp()),
				Parameter.of(SECOND_COMBATANT_ARMY_MAX_HP, secondCombatant.getArmyMaxHp()),
				Parameter.of(SECOND_COMBATANT_ARMY_ATK, secondCombatant.getArmyAtk()),
				Parameter.of(SECOND_COMBATANT_ARMY_DEF, secondCombatant.getArmyDef()),
				Parameter.of(SECOND_COMBATANT_ARMY_TIER, "0"),

				Parameter.of(FIRST_COMBATANT_ARMY_TYPE_EMOJI_AFTER, firstArmyStrengthEmojisAfter),
				Parameter.of(SECOND_COMBATANT_ARMY_TYPE_EMOJI_AFTER, secondArmyStrengthEmojisAfter)
		);
	}

	private Map<String, String> buildBattleTurnParameters(DuelParticipants duelParticipants, int battleTurn, Long userId, String characterClass) {
		Map<String, String> battleParams = buildBattleParameters(duelParticipants, userId, characterClass);
		Combatant firstCombatant = duelParticipants.getFirstCombatant();
		Combatant secondCombatant = duelParticipants.getSecondCombatant();
		Map<String, Localization.Skill> skills = localization.getSkills();
		Map<String, Localization.MonsterSkill> monsterSkills = localization.getMonsterSkills();
		String firstCombatantActiveSkill = firstCombatant.getActiveSkill();
		String firstCombatantSkill = firstCombatant.isCharacter() ? skills.get(firstCombatantActiveSkill).getName() : monsterSkills.get(firstCombatantActiveSkill).getName();
		String secondCombatantActiveSkill = secondCombatant.getActiveSkill();
		String secondCombatantSkill = secondCombatant.isCharacter() ? skills.get(secondCombatantActiveSkill).getName() : monsterSkills.get(secondCombatantActiveSkill).getName();

		Map<String, String> battleTurnParams = Parameters.buildParameters(
				Parameter.of(FIRST_COMBATANT_LAST_SKILL, firstCombatantSkill),
				Parameter.of(SECOND_COMBATANT_LAST_SKILL, secondCombatantSkill),
				Parameter.of(FIRST_COMBATANT_HERO_DAMAGE_TAKEN, firstCombatant.getReceivedDmg()),
				Parameter.of(SECOND_COMBATANT_HERO_DAMAGE_TAKEN, secondCombatant.getReceivedDmg()),
				Parameter.of(FIRST_COMBATANT_ARMY_DAMAGE_TAKEN, firstCombatant.getReceivedArmyDmg()),
				Parameter.of(SECOND_COMBATANT_ARMY_DAMAGE_TAKEN, secondCombatant.getReceivedArmyDmg()),
				Parameter.of(TURN, battleTurn)
		);

		Map<String, String> params = new HashMap<>();
		params.putAll(battleParams);
		params.putAll(battleTurnParams);
		return params;
	}

	private Map<String, String> buildBattleResultParameters(DuelParticipants duelParticipants, Long userId, String characterClass, BattleOutcome battleOutcome) {
		Map<String, String> battleParams = buildBattleParameters(duelParticipants, userId, characterClass);
		Map<String, String> params = new HashMap<>(battleParams);
		params.put(BATTLE_OUTCOME.getKey(), localization.getBattle().get(battleOutcome.name()));
		return params;
	}
}
