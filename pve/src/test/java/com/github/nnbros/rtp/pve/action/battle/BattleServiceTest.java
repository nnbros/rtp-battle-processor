package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.battleprocessor.core.BattleProcessor;
import com.github.nnbros.rtp.battleprocessor.core.DuelParticipantsImpl;
import com.github.nnbros.rtp.pve.PveTest;
import com.github.nnbros.rtp.pve.action.ActionContext;
import com.github.nnbros.rtp.pve.action.ActionResult;
import com.github.nnbros.rtp.pve.api.view.DetailedCharacterWithSkillsView;
import com.github.nnbros.rtp.pve.configuration.PveProperties;
import com.github.nnbros.rtp.pve.exception.BattleNotFoundException;
import com.github.nnbros.rtp.pve.exception.CharacterNotFoundException;
import com.github.nnbros.rtp.pve.exception.PveRuntimeException;
import com.github.nnbros.rtp.pve.monster.MonsterService;
import com.github.nnbros.rtp.pve.storyteller.StoryTellerClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static com.github.nnbros.rtp.pve.BotTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BattleServiceTest extends PveTest {

	public static final int TEST_MAX_ACTIVE_SKILLS = 3;
	@Mock
	PveProperties properties;
	@Mock
	PveProperties.Battle battleProps;
	@Mock
	MonsterService monsterService;
	@Mock
	StoryTellerClient storyTellerClient;
	@Mock
	BattleProcessor battleProcessor;

	@InjectMocks
	BattleService battleService;

	@BeforeEach
	void setUp() {
		when(properties.getBattle()).thenReturn(battleProps);
		when(battleProps.getMaxActiveSkills()).thenReturn(TEST_MAX_ACTIVE_SKILLS);
	}

	@Test
	void initiateBattle() {
		DetailedCharacterWithSkillsView testCharacterView = testCharacterView();
		when(storyTellerClient.getCharacter(TEST_USER_ID)).thenReturn(testCharacterView);
		when(monsterService.getRandomMonster()).thenReturn(testMonster());
		ActionContext ctx = createTestActionContext();

		ActionResult<Battle> result = battleService.initiateBattle(ctx);

		assertNotNull(result);
		assertTrue(result.isSuccessful());
		Battle battle = result.getValue();
		assertNotNull(battle);
		assertEquals(testDuelParticipants(), battle.getDuelParticipants());
		assertTrue(battle.getCharacterSkills().containsAll(List.of(TEST_SKILL_1, TEST_SKILL_2)));
		assertEquals(TEST_MAX_ACTIVE_SKILLS, battle.getMaxActiveSkillsCount());
		verify(storyTellerClient, times(1)).getCharacter(TEST_USER_ID);
		verify(monsterService, times(1)).getRandomMonster();
	}

	@Test
	void initiateBattleWhenBattleAlreadyExists() {
		DetailedCharacterWithSkillsView testCharacterView = testCharacterView();
		when(storyTellerClient.getCharacter(TEST_USER_ID)).thenReturn(testCharacterView);
		when(monsterService.getRandomMonster()).thenReturn(testMonster());
		ActionContext ctx = createTestActionContext();

		battleService.initiateBattle(ctx);
		assertThrows(PveRuntimeException.class,
				() -> battleService.initiateBattle(ctx));
	}

	@Test
	void initiateBattleCharacterNotFound() {
		when(storyTellerClient.getCharacter(TEST_USER_ID)).thenReturn(null);
		ActionContext ctx = createTestActionContext();

		assertThrows(CharacterNotFoundException.class,
				() -> battleService.initiateBattle(ctx));

		verify(monsterService, never()).getRandomMonster();
	}

	@Test
	void processBattleTurnBattleIsNotFinished() {
		when(storyTellerClient.getCharacter(TEST_USER_ID)).thenReturn(testCharacterView());
		when(monsterService.getRandomMonster()).thenReturn(testMonster());

		ActionContext initialContext = createTestActionContext();
		ActionResult<Battle> battleActionResult = battleService.initiateBattle(initialContext);

		assertNotNull(battleActionResult);
		Battle battle = battleActionResult.getValue();
		assertNotNull(battle);
		battle.getActiveSkills().add(TEST_SKILL_1);

		ActionContext battleTurnContext = createTestActionContext(TEST_SKILL_1);
		ActionResult<Battle> turnResult = battleService.processBattleTurn(battleTurnContext);

		assertNotNull(turnResult);
		assertFalse(turnResult.isSuccessful());
		battle = turnResult.getValue();
		assertNotNull(battle);
		DuelParticipantsImpl duelParticipants = battle.getDuelParticipants();
		DuelParticipantsImpl expectedDuelParticipants = testDuelParticipants();
		expectedDuelParticipants.getFirstCombatant().setActiveSkill(TEST_SKILL_1);
		assertEquals(expectedDuelParticipants, duelParticipants);
		verify(battleProcessor).calculateDuelTurn(duelParticipants);
	}

	@Test
	void processBattleTurnSkillNotActive() {
		when(storyTellerClient.getCharacter(TEST_USER_ID)).thenReturn(testCharacterView());
		when(monsterService.getRandomMonster()).thenReturn(testMonster());

		ActionContext initialContext = createTestActionContext();
		ActionResult<Battle> battleActionResult = battleService.initiateBattle(initialContext);

		assertNotNull(battleActionResult);
		Battle battle = battleActionResult.getValue();
		assertNotNull(battle);
		battle.getActiveSkills().add(TEST_SKILL_1);

		ActionContext battleTurnContext = createTestActionContext(TEST_SKILL_2);
		assertThrows(PveRuntimeException.class,
				() -> battleService.processBattleTurn(battleTurnContext));
	}

	@Test
	void processBattleTurnBattleNotFound() {
		ActionContext battleTurnContext = createTestActionContext(TEST_SKILL_2);

		assertThrows(BattleNotFoundException.class,
				() -> battleService.processBattleTurn(battleTurnContext));
	}
}
