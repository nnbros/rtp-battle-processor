package com.github.nnbros.rtp.battleprocessor.core;

public interface Combatant {

	String getEntityName();

	String getCombatantType();

	int getHp();

	int getAtk();

	int getDef();

	String getArmyName();

	String getArmyType();

	int getArmyHp();

	int getArmyAtk();

	int getArmyDef();

	int getArmyQuantity();

	boolean isCharacter();

	String getActiveSkill();

	int getReceivedDmg();

	int getReceivedArmyDmg();

	void setEntityName(String entityName);

	void setCombatantType(String combatantType);

	void setHp(int hp);

	void setAtk(int atk);

	void setDef(int def);

	void setArmyName(String armyName);

	void setArmyType(String armyType);

	void setArmyHp(int armyHp);

	void setArmyAtk(int armyAtk);

	void setArmyDef(int armyDef);

	void setArmyQuantity(int armyQuantity);

	void setCharacter(boolean character);

	void setActiveSkill(String activeSkill);

	void setReceivedDmg(int receivedDmg);

	void setReceivedArmyDmg(int receivedArmyDmg);
}
