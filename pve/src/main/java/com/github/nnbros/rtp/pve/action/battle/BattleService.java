package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.battleprocessor.core.*;
import com.github.nnbros.rtp.common.action.ActionContext;
import com.github.nnbros.rtp.common.action.ActionResult;
import com.github.nnbros.rtp.common.api.dto.character.ActiveCharacterSkill;
import com.github.nnbros.rtp.common.api.dto.character.DetailedCharacterWithSkillsView;
import com.github.nnbros.rtp.common.exception.CharacterNotFoundException;
import com.github.nnbros.rtp.pve.configuration.PveProperties;
import com.github.nnbros.rtp.pve.exception.BattleNotFoundException;
import com.github.nnbros.rtp.pve.exception.PveRuntimeException;
import com.github.nnbros.rtp.pve.monster.MonsterDictionary;
import com.github.nnbros.rtp.pve.monster.MonsterService;
import com.github.nnbros.rtp.pve.storyteller.StoryTellerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class BattleService {
	private final AtomicLong battleId = new AtomicLong();
	private final PveProperties properties;
	private final MonsterService monsterService;
	private final StoryTellerClient storyTellerClient;
	private final BattleProcessor battleProcessor;
	private final CharacterMapper characterMapper;
	private final MonsterMapper monsterMapper;
	private final ConcurrentHashMap<Long, Battle> battleCache = new ConcurrentHashMap<>();

	public ActionResult<Battle> initiateBattle(ActionContext actionContext) {
		Long userId = actionContext.userId();
		log.debug("Initiation battle request has been received for the user [{}]", userId);
		DetailedCharacterWithSkillsView character = Optional.ofNullable(storyTellerClient.getCharacter(userId))
				.orElseThrow(() -> new CharacterNotFoundException(userId));
		log.trace("Character actionData has been fetched from Storyteller:\n{}", character);

		MonsterDictionary monster = monsterService.getRandomMonster();
		List<String> skills = character.getActiveSkills()
				.stream()
				.map(ActiveCharacterSkill::name)
				.toList();
		DuelParticipantsImpl duelParticipants = new DuelParticipantsImpl(characterMapper.toCombatant(character), monsterMapper.toCombatant(monster));

		Battle battle = Battle.builder()
				.battleId(battleId.incrementAndGet())
				.duelParticipants(duelParticipants)
				.characterClass(character.getActiveClass().name())
				.characterSkills(skills)
				.maxActiveSkillsCount(properties.getBattle().getMaxActiveSkills())
				.build();
		Battle existingBattle = battleCache.putIfAbsent(userId, battle);
		if (Objects.nonNull(existingBattle)) {
			throw new PveRuntimeException("Unable to create a new battle for the the user [{}], the battle already exists: [{}]", userId, existingBattle);
		}
		battle.drawNextTurnSkills();
		log.debug("The battle [{}] has been started successfully", battle.getBattleId());
		log.trace("The battle:\n{}", battle);
		return new ActionResult<>(actionContext, battle);
	}

	public ActionResult<Battle> processBattleTurn(ActionContext actionContext) {
		Long userId = actionContext.userId();
		log.debug("Battle turn processing request has been received for the user [{}]", userId);
		String selectedSkill = actionContext.actionData();
		Battle battle = getBattle(userId);
		if (!battle.activeSkillsContain(selectedSkill)) {
			throw new PveRuntimeException("Active skills don't contain selected skill");
		}

		DuelParticipantsImpl duelParticipants = battle.getDuelParticipants();
		Combatant character = duelParticipants.getFirstCombatant().isCharacter() ? duelParticipants.getFirstCombatant() : duelParticipants.getSecondCombatant();
		character.setActiveSkill(selectedSkill);

		int battleTurn = battle.incrementTurnAndGet();
		log.debug("Calculating battle turn [{}] for battle [{}]", battleTurn, battle.getBattleId());
		battleProcessor.calculateDuelTurn(duelParticipants);
		boolean battleFinished = isBattleFinished(battle);
		if (battleFinished) {
			battleCache.remove(userId);
		} else {
			battle.drawNextTurnSkills();
		}
		log.debug("Is battle finished: {}", battleFinished);
		log.trace("Current battle state:\n{}", battle);
		return new ActionResult<>(actionContext, battleFinished, battle);
	}

	private Battle getBattle(Long userId) {
		return Optional.ofNullable(battleCache.get(userId))
				.orElseThrow(() -> new BattleNotFoundException(userId));
	}

	private boolean isBattleFinished(Battle battle) {
		DuelParticipants participants = battle.getDuelParticipants();
		Combatant first = participants.getFirstCombatant();
		Combatant second = participants.getSecondCombatant();
		Combatant character = first.isCharacter() ? first : second;
		Combatant monster = first.isCharacter() ? second : first;

		int characterHp = character.getHp();
		int monsterHp = monster.getHp();
		if (characterHp > 0 && monsterHp > 0) {
			return false;
		}

		if (characterHp <= 0 && monsterHp <= 0) {
			battle.setBattleOutcome(BattleOutcome.battleOutcomeDraw);
		} else if (characterHp <= 0) {
			battle.setBattleOutcome(BattleOutcome.battleOutcomeLose);
		} else {
			battle.setBattleOutcome(BattleOutcome.battleOutcomeWin);
		}
		return true;
	}

	//TODO scheduler for removing expired battles
}
