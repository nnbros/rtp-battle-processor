# coding=utf-8
import math


from com.github.nnbros.rtp.battleprocessor.core import DuelParticipants, Combatant
from damage_calculator import calculate_damage, apply_changes
from monster_ai import choose_monster_skill


class CombatantPy(Combatant):
    def __init__(self, entityName, archetype, maxHp, hp, atk, def_, armyName, armyArchetype, armyMaxHp, armyHp, armyAtk, armyDef,
                 armyQuantity, isCharacter, activeSkill, advantageBonus, armyAdvantageBonus, receivedDmg, receivedArmyDmg, aliveSoldiers, soldiersDelta):
        self._entityName = entityName
        self._archetype = archetype
        self._maxHp = maxHp
        self._hp = hp
        self._atk = atk
        self._def = def_  # Avoid using "def" since it's a reserved keyword
        self._armyName = armyName
        self._armyArchetype = armyArchetype
        self._armyMaxHp = armyMaxHp
        self._armyHp = armyHp
        self._armyAtk = armyAtk
        self._armyDef = armyDef
        self._armyQuantity = armyQuantity
        self._isCharacter = isCharacter
        self.__activeSkill = activeSkill
        self._advantageBonus = advantageBonus
        self._armyAdvantageBonus = armyAdvantageBonus
        self._receivedDmg = receivedDmg
        self._receivedArmyDmg = receivedArmyDmg
        self._aliveSoldiers = aliveSoldiers
        self._soldiersDelta = soldiersDelta

    def getEntityName(self):
        return self._entityName

    def getArchetype(self):
        return self._archetype

    def getMaxHp(self):
        return self._maxHp

    def getHp(self):
        return self._hp

    def getAtk(self):
        return self._atk

    def getDef(self):
        return self._def

    def getArmyName(self):
        return self._armyName

    def getArmyArchetype(self):
        return self._armyArchetype

    def getArmyMaxHp(self):
        return self._armyMaxHp

    def getArmyHp(self):
        return self._armyHp

    def getArmyAtk(self):
        return self._armyAtk

    def getArmyDef(self):
        return self._armyDef

    def getArmyQuantity(self):
        return self._armyQuantity

    def isCharacter(self):
        return self._isCharacter

    def getActiveSkill(self):
        return self.__activeSkill

    def getAdvantageBonus(self):
        return self._advantageBonus

    def getArmyAdvantageBonus(self):
        return self._armyAdvantageBonus

    def getReceivedDmg(self):
        return self._receivedDmg

    def getReceivedArmyDmg(self):
        return self._receivedArmyDmg

    def getAliveSoldiers(self):
        return self._aliveSoldiers

    def getSoldiersDelta(self):
        return self._soldiersDelta

    def setEntityName(self, value):
        self._entityName = value

    def setArchetype(self, value):
        self._archetype = value

    def setHp(self, value):
        self._hp = value

    def setAtk(self, value):
        self._atk = value

    def setDef(self, value):
        self._def = value

    def setArmyName(self, value):
        self._armyName = value

    def setArmyArchetype(self, value):
        self._armyArchetype = value

    def setArmyHp(self, value):
        self._armyHp = value

    def setArmyAtk(self, value):
        self._armyAtk = value

    def setArmyDef(self, value):
        self._armyDef = value

    def setArmyQuantity(self, value):
        self._armyQuantity = value

    def setIsCharacter(self, value):
        self._isCharacter = value

    def setActiveSkill(self, value):
        self.__activeSkill = value

    def setAdvantageBonus(self, value):
        self._advantageBonus = value

    def setArmyAdvantageBonus(self, value):
        self._armyAdvantageBonus = value

    def setReceivedDmg(self, value):
        self._receivedDmg = value

    def setReceivedArmyDmg(self, value):
        self._receivedArmyDmg = value

    def setAliveSoldiers(self, value):
        self._aliveSoldiers = value

    def setSoldiersDelta(self, value):
        self._soldiersDelta = value


class DuelParticipantsPy(DuelParticipants):
    def __init__(self, firstCombatant, secondCombatant):
        self._firstCombatant = firstCombatant
        self._secondCombatant = secondCombatant

    def getFirstCombatant(self):
        return self._firstCombatant

    def getSecondCombatant(self):
        return self._secondCombatant

    def setFirstCombatant(self, value):
        self._firstCombatant = value

    def setSecondCombatant(self, value):
        self._secondCombatant = value


def calculate_duel_turn(duel_participants):
    # Parse duel_participants
    combatant1 = duel_participants.getFirstCombatant()
    combatant2 = duel_participants.getSecondCombatant()
        
    # Apply AI to NPCs 
    for combatant in [combatant1, combatant2]:
        if not combatant.isCharacter():
            combatant.setActiveSkill(choose_monster_skill(combatant))
            
    # Calculate whats happened
    changes_combat1 = calculate_damage(combatant2, combatant1)
    changes_combat2 = calculate_damage(combatant1, combatant2)
        
    # Apply results
    apply_changes(changes_combat1, combatant1)
    apply_changes(changes_combat2, combatant2)
    
