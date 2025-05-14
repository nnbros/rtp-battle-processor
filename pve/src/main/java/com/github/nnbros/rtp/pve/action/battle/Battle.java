package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.battleprocessor.core.DuelParticipants;
import com.github.nnbros.rtp.battleprocessor.core.DuelParticipantsImpl;
import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
public class Battle {
	private final long battleId;
	private final DuelParticipantsImpl duelParticipants;
	private final List<String> characterSkills;
	private final int maxActiveSkillsCount;
	@Builder.Default
	private final Deque<String> playerDeck = new ArrayDeque<>();
	@Builder.Default
	private final AtomicInteger turnCounter = new AtomicInteger();
	@Builder.Default
	private final Set<String> activeSkills = new HashSet<>();

	public List<String> drawNextTurnSkills() {
		if (playerDeck.size() < maxActiveSkillsCount) {
			List<String> shufflingList = new ArrayList<>(characterSkills);
			Collections.shuffle(shufflingList, ThreadLocalRandom.current());
			playerDeck.addAll(shufflingList);
		}

		List<String> nextTurnSkills = new ArrayList<>(maxActiveSkillsCount);
		for (int i = 0; i < maxActiveSkillsCount; i++) {
			nextTurnSkills.add(playerDeck.removeFirst());
		}
		turnCounter.incrementAndGet();
		activeSkills.clear();
		activeSkills.addAll(nextTurnSkills);
		return nextTurnSkills;
	}

	public boolean activeSkillsContain(String skill) {
		return activeSkills.contains(skill);
	}
}
