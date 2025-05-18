package com.github.nnbros.rtp.pve;

import com.github.nnbros.rtp.battleprocessor.core.CombatantImpl;
import com.github.nnbros.rtp.battleprocessor.core.DuelParticipantsImpl;
import com.github.nnbros.rtp.pve.action.ActionContext;
import com.github.nnbros.rtp.pve.api.view.*;
import com.github.nnbros.rtp.pve.monster.Archetype;
import com.github.nnbros.rtp.pve.monster.MonsterDictionary;
import com.github.nnbros.rtp.pve.telegram.UpdateType;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

public class BotTestUtils {
	public static final String TEST_ACTION_NAME = "test_action";
	public static final String TEST_ACTION_DATA = "test_action_data";
	public static final int TEST_UPDATE_ID = 456;
	public static final String TEST_TEXT = "util";
	public static final long TEST_USER_ID = 123L;
	public static final int TEST_MESSAGE_ID = 12345;
	public static final String CHAT_PRIVATE_TYPE = "private";
	public static final String COMMAND_MESSAGE_TYPE = "bot_command";
	public static final String TEST_CALLBACK_DATA = "test_data";
	public static final String TEST_CHARACTER_NAME = "hero";
	public static final String TEST_MONSTER_NAME = "goblin";
	public static final String TEST_SKILL_1 = "assault";
	public static final String TEST_SKILL_2 = "defence";
	public static final String TEST_CLASS_NAME = "warrior";
	public static final String TEST_ARMY_NAME = "peasants";

	public static User createTestUser() {
		return User.builder()
				.id(TEST_USER_ID)
				.firstName("TestUser")
				.isBot(false)
				.build();
	}

	public static Message createTestMessage() {
		return createTestMessage(TEST_TEXT);
	}

	public static Message createTestMessage(String text) {
		Chat chat = Chat.builder()
				.type(CHAT_PRIVATE_TYPE)
				.id(TEST_USER_ID)
				.build();
		User from = createTestUser();
		return Message.builder()
				.text(text)
				.chat(chat)
				.from(from)
				.messageId(TEST_MESSAGE_ID)
				.build();
	}

	public static Message createTestCommandMessage(String text) {
		MessageEntity messageEntity = MessageEntity.builder()
				.length(1)
				.offset(0)
				.type(COMMAND_MESSAGE_TYPE)
				.build();
		Message message = createTestMessage(text);
		message.setEntities(List.of(messageEntity));
		return message;
	}

	public static CallbackQuery createTestCallbackQuery(String data) {
		CallbackQuery callbackQuery = new CallbackQuery();
		callbackQuery.setFrom(createTestUser());
		callbackQuery.setData(data);
		callbackQuery.setMessage(createTestMessage());
		return callbackQuery;
	}

	public static Update createTestEmptyUpdate() {
		Update update = new Update();
		update.setUpdateId(TEST_UPDATE_ID);
		return update;
	}

	public static Update createTestMessageUpdate() {
		Update update = new Update();
		update.setMessage(createTestMessage());
		update.setUpdateId(TEST_UPDATE_ID);
		return update;
	}

	public static Update createTestMessageUpdate(Message message) {
		Update update = createTestMessageUpdate();
		update.setMessage(message);
		return update;
	}

	public static Update createTestEditedMessageUpdate() {
		return createTestEditedMessageUpdate(createTestMessage());
	}

	public static Update createTestEditedMessageUpdate(Message message) {
		Update update = new Update();
		update.setEditedMessage(message);
		update.setUpdateId(TEST_UPDATE_ID);
		return update;
	}

	public static Update createTestCallbackQueryUpdate() {
		return createTestCallbackQueryUpdate(TEST_CALLBACK_DATA);
	}

	public static Update createTestCallbackQueryUpdate(String data) {
		Update update = new Update();
		update.setCallbackQuery(createTestCallbackQuery(data));
		update.setUpdateId(TEST_UPDATE_ID);
		return update;
	}

	public static ActionContext createTestActionContext() {
		return createTestActionContext(TEST_ACTION_DATA);
	}

	public static ActionContext createTestActionContext(String data) {
		return new ActionContext(TEST_ACTION_NAME, TEST_USER_ID, UpdateType.CALLBACK_QUERY, createTestEmptyUpdate(), TEST_MESSAGE_ID, data);
	}

	public static DuelParticipantsImpl testDuelParticipants() {
		return new DuelParticipantsImpl(testCharacterCombatant(), testMonsterCombatant());
	}

	public static CombatantImpl testCharacterCombatant() {
		return testCombatantBuilder()
				.entityName(TEST_CHARACTER_NAME)
				.archetype(Archetype.CAVALRY.toString())
				.armyName(TEST_ARMY_NAME)
				.armyArchetype(Archetype.CAVALRY.toString())
				.armyMaxHp(50)
				.armyHp(50)
				.armyAtk(5)
				.armyDef(2)
				.armyQuantity(10)
				.aliveSoldiers(10)
				.isCharacter(true)
				.build();
	}

	public static CombatantImpl testMonsterCombatant() {
		return testCombatantBuilder()
				.entityName(TEST_MONSTER_NAME)
				.archetype(Archetype.NEUTRAL.toString())
				.isCharacter(false)
				.atk(15)
				.def(5)
				.armyName(TEST_MONSTER_NAME)
				.armyArchetype(Archetype.NEUTRAL.toString())
				.advantageBonus(0.0f)
				.build();
	}

	private static CombatantImpl.CombatantImplBuilder testCombatantBuilder() {
		return CombatantImpl.builder()
				.entityName("Unnamed")
				.archetype("UNKNOWN")
				.maxHp(100)
				.hp(100)
				.atk(20)
				.def(10)
				.advantageBonus(1.0f)
				.armyName(null)
				.armyArchetype(null)
				.armyMaxHp(0)
				.armyHp(0)
				.armyAtk(0)
				.armyDef(0)
				.armyQuantity(0)
				.armyAdvantageBonus(0.0f)
				.isCharacter(true)
				.activeSkill(null)
				.receivedDmg(0)
				.receivedArmyDmg(0)
				.aliveSoldiers(0)
				.soldiersDelta(0);
	}

	public static DetailedCharacterWithSkillsView testCharacterView() {
		return baseView();
	}

	private static DetailedCharacterWithSkillsView baseView() {
		DetailedCharacterWithSkillsView view = new DetailedCharacterWithSkillsView();
		view.setName(TEST_CHARACTER_NAME);

		ActiveCharacterClass activeClass = new ActiveCharacterClass(
				Archetype.CAVALRY,
				TEST_CLASS_NAME,
				100,     // baseHp
				20,             // baseAtk
				10,             // baseDef
				1.0f,           // advantageBonus
				0L              // experience
		);
		view.setActiveClass(activeClass);

		ActiveCharacterArmy activeArmy = new ActiveCharacterArmy(
				TEST_ARMY_NAME,
				Archetype.CAVALRY,
				10,  // baseQuantity
				50,             // baseHp
				5,              // baseAtk
				2,              // baseDef
				0.0f,           // advantageBonus
				1,              // level
				1               // tier
		);
		view.setActiveArmy(activeArmy);

		ActiveCharacterSkill testSkill1 = new ActiveCharacterSkill(TEST_SKILL_1, SkillType.CAVALRY, null);
		ActiveCharacterSkill testSkill2 = new ActiveCharacterSkill(TEST_SKILL_2, SkillType.SWORDSMAN, null);
		view.setActiveSkills(List.of(testSkill1, testSkill2));
		return view;
	}

	public static MonsterDictionary testMonster() {
		return new MonsterDictionary(
				1,
				TEST_MONSTER_NAME,
				Archetype.NEUTRAL,
				100,
				15,
				5,
				TEST_MONSTER_NAME,
				Archetype.NEUTRAL,
				0,
				0,
				0,
				0,
				0
		);
	}
}
