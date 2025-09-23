package com.github.nnbros.rtp.pve.action.battle;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.nnbros.rtp.pve.BotTestUtils.TEST_SKILL_1;
import static com.github.nnbros.rtp.pve.BotTestUtils.TEST_SKILL_2;
import static com.github.nnbros.rtp.pve.BotTestUtils.TEST_SKILL_3;
import static com.github.nnbros.rtp.pve.BotTestUtils.testBattle;
import static org.junit.jupiter.api.Assertions.*;

class BattleTest {
	public static final String FIRST_ACTIVE_SKILL = "first_active";
	public static final String SECOND_ACTIVE_SKILL = "second_active";
	public static final String THIRD_ACTIVE_SKILL = "third_active";
	public static final String OUTDATED_SKILL = "outdated_skill";

	@Test
        void drawNextTurnSkillsRefillsDeck() {
                List<String> characterSkills = List.of(TEST_SKILL_1, TEST_SKILL_2, TEST_SKILL_3);
                int maxActiveSkillsCount = 2;
                Battle battle = testBattle(characterSkills, maxActiveSkillsCount);
                battle.getPlayerDeck().add(OUTDATED_SKILL);

                battle.drawNextTurnSkills();

                assertEquals(maxActiveSkillsCount, battle.getActiveSkills().size());
                assertTrue(characterSkills.containsAll(battle.getActiveSkills()));
                assertEquals(characterSkills.size() - maxActiveSkillsCount, battle.getPlayerDeck().size());
                assertTrue(characterSkills.containsAll(battle.getPlayerDeck()));
                assertFalse(battle.getActiveSkills().contains(OUTDATED_SKILL));
                assertFalse(battle.getPlayerDeck().contains(OUTDATED_SKILL));
                assertEquals(characterSkills.size(), battle.getActiveSkills().size() + battle.getPlayerDeck().size());
        }

        @Test
        void drawNextTurnSkillsUsesExistingDeck() {
                List<String> characterSkills = List.of(TEST_SKILL_1, TEST_SKILL_2, TEST_SKILL_3);
                int maxActiveSkillsCount = 2;
                Battle battle = testBattle(characterSkills, maxActiveSkillsCount);
                List<String> existingDeck = List.of(FIRST_ACTIVE_SKILL, SECOND_ACTIVE_SKILL, THIRD_ACTIVE_SKILL);
                battle.getPlayerDeck().clear();
                battle.getPlayerDeck().addAll(existingDeck);

                battle.drawNextTurnSkills();

                assertEquals(existingDeck.subList(0, maxActiveSkillsCount), new ArrayList<>(battle.getActiveSkills()));
                assertEquals(existingDeck.get(maxActiveSkillsCount), battle.getPlayerDeck().peekFirst());
                assertEquals(existingDeck.size() - maxActiveSkillsCount, battle.getPlayerDeck().size());
        }

        @Test
        void activeSkillsContainChecksPresence() {
                Battle battle = testBattle(List.of(TEST_SKILL_1, TEST_SKILL_2, TEST_SKILL_3), 2);
                List<String> existingDeck = List.of(FIRST_ACTIVE_SKILL, SECOND_ACTIVE_SKILL, THIRD_ACTIVE_SKILL);
                battle.getPlayerDeck().clear();
                battle.getPlayerDeck().addAll(existingDeck);

                battle.drawNextTurnSkills();

                assertTrue(battle.activeSkillsContain(existingDeck.get(0)));
                assertTrue(battle.activeSkillsContain(existingDeck.get(1)));
                assertFalse(battle.activeSkillsContain(existingDeck.get(2)));
        }

        @Test
        void incrementTurnAndGetAdvancesCounter() {
                Battle battle = testBattle(List.of(TEST_SKILL_1, TEST_SKILL_2), 1);

                assertEquals(0, battle.getCurrentTurn());
                assertEquals(1, battle.incrementTurnAndGet());
                assertEquals(1, battle.getCurrentTurn());
                assertEquals(2, battle.incrementTurnAndGet());
        }
}
