package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.battleprocessor.core.DuelParticipantsImpl;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
public class Battle {
	private final long battleId;
	@NonNull
	private final DuelParticipantsImpl duelParticipants;
	private final String characterClass;
	private final List<String> characterSkills;
	private final int maxActiveSkillsCount;
	@Builder.Default
	private final Deque<String> playerDeck = new ArrayDeque<>();
	@Builder.Default
	private final AtomicInteger turnCounter = new AtomicInteger(0);
	@Builder.Default
	private final Set<String> activeSkills = new LinkedHashSet<>();
	private BattleOutcome battleOutcome;

	public void drawNextTurnSkills() {
		if (playerDeck.size() < maxActiveSkillsCount) {
			List<String> shufflingList = new ArrayList<>(characterSkills);
			Collections.shuffle(shufflingList, ThreadLocalRandom.current());
			playerDeck.clear();
			playerDeck.addAll(shufflingList);
		}

		activeSkills.clear();
		for (int i = 0; i < maxActiveSkillsCount; i++) {
			activeSkills.add(playerDeck.removeFirst());
		}
	}

	public boolean activeSkillsContain(String skill) {
		return activeSkills.contains(skill);
	}

	public int getCurrentTurn() {
		return turnCounter.get();
	}

	public int incrementTurnAndGet() {
		return turnCounter.incrementAndGet();
	}
}
