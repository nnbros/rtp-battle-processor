package com.github.nnbros.rtp.battleprocessor.core;

public interface Combatant {

	String getEntityName();

	String getArchetype();

	int getMaxHp();

	int getHp();

	int getAtk();

	int getDef();

	float getAdvantageBonus();

	String getArmyName();

	String getArmyArchetype();

	int getArmyMaxHp();

	int getArmyHp();

	int getArmyAtk();

	int getArmyDef();

	int getArmyQuantity();

	float getArmyAdvantageBonus();

	boolean isCharacter();

	String getActiveSkill();

	int getAliveSoldiers();

	int getReceivedDmg();

	int getReceivedArmyDmg();

	int getSoldiersDelta();

	void setEntityName(String entityName);

	void setArchetype(String archetype);

	void setMaxHp(int maxHp);

	void setHp(int hp);

	void setAtk(int atk);

	void setDef(int def);

	void setAdvantageBonus(float advantageBonus);

	void setArmyName(String armyName);

	void setArmyArchetype(String armyArchetype);

	void setArmyMaxHp(int armyMaxHp);

	void setArmyHp(int armyHp);

	void setArmyAtk(int armyAtk);

	void setArmyDef(int armyDef);

	void setArmyQuantity(int armyQuantity);

	void setArmyAdvantageBonus(float armyAdvantageBonus);

	void setCharacter(boolean character);

	void setActiveSkill(String activeSkill);

	void setReceivedDmg(int receivedDmg);

	void setReceivedArmyDmg(int receivedArmyDmg);

	void setAliveSoldiers(int aliveSoldiers);

	void setSoldiersDelta(int soldiersDelta);
}
