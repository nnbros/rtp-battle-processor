package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.battleprocessor.core.BattleProcessor;
import com.github.nnbros.rtp.battleprocessor.core.Combatant;
import com.github.nnbros.rtp.battleprocessor.core.DuelParticipants;
import com.github.nnbros.rtp.battleprocessor.core.DuelParticipantsImpl;
import com.github.nnbros.rtp.pve.action.ActionContext;
import com.github.nnbros.rtp.pve.action.ActionResult;
import com.github.nnbros.rtp.pve.api.view.ActiveCharacterSkill;
import com.github.nnbros.rtp.pve.api.view.DetailedCharacterWithSkillsView;
import com.github.nnbros.rtp.pve.configuration.PveProperties;
import com.github.nnbros.rtp.pve.exception.BattleNotFoundException;
import com.github.nnbros.rtp.pve.exception.CharacterNotFoundException;
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
		DuelParticipantsImpl duelParticipants = new DuelParticipantsImpl(character.toCombatant(), monster.toCombatant());

		Battle battle = Battle.builder()
				.battleId(battleId.incrementAndGet())
				.duelParticipants(duelParticipants)
				.characterSkills(skills)
				.maxActiveSkillsCount(properties.getBattle().getMaxActiveSkills())
				.build();
		Battle existingBattle = battleCache.putIfAbsent(userId, battle);
		if (Objects.nonNull(existingBattle)) {
			throw new PveRuntimeException("Unable to create a new battle for the the user [{}], the battle already exists: [{}]", userId, existingBattle);
		}
		log.debug("The battle has been started successfully");
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

		battleProcessor.calculateDuelTurn(duelParticipants);
		boolean battleFinished = isBattleFinished(duelParticipants);
		log.debug("Is battle finished: {}", battleFinished);
		log.trace("Current battle state:\n{}", battle);
		return new ActionResult<>(actionContext, battleFinished, battle);
	}

	private Battle getBattle(Long userId) {
		return Optional.ofNullable(battleCache.get(userId))
				.orElseThrow(() -> new BattleNotFoundException(userId));
	}

	private boolean isBattleFinished(DuelParticipants duelParticipants) {
		Combatant firstCombatant = duelParticipants.getFirstCombatant();
		Combatant secondCombatant = duelParticipants.getSecondCombatant();
		return firstCombatant.getHp() - firstCombatant.getReceivedDmg() <= 0 || secondCombatant.getHp() - secondCombatant.getReceivedDmg() <= 0;
	}

	//TODO scheduler for removing expired battles
}
