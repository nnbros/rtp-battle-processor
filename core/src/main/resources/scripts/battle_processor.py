# coding=utf-8
import math


from com.github.nnbros.rtp.battleprocessor.core import DuelParticipants, Combatant
from core.src.main.resources.scripts.calculate_damage import calculate_damage
from core.src.main.resources.scripts.monsterAI import monster_ai
from core.src.main.resources.scripts.skill_dictionary import skillDictionary


class CombatantPy(Combatant):
    def __init__(self, entityName, archetype, maxHp, hp, atk, def_, armyName, armyArchetype, armyMaxHp, armyHp, armyAtk, armyDef,
                 armyQuantity, isCharacter, activeSkill, advantageBonus, advantageArmyBonus, receivedDmg, receivedArmyDmg):
        self._entityName = entityName
        self._archetype = archetype  # Нужно поменять в Джаве из type, так как мы решили поменять везде type на archetype
        self._maxHp = maxHp  # Нужно добавить в Джаву
        self._hp = hp
        self._atk = atk
        self._def = def_  # Avoid using "def" since it's a reserved keyword
        self._armyName = armyName
        self._armyArchetype = armyArchetype # Нужно поменять в Джаве из type, так как мы решили поменять везде type на archetype
        self._armyMaxHp = armyMaxHp  # Нужно добавить в Джаву
        self._armyHp = armyHp
        self._armyAtk = armyAtk
        self._armyDef = armyDef
        self._armyQuantity = armyQuantity
        self._isCharacter = isCharacter
        self.__activeSkill = activeSkill
        self._advantageBonus = advantageBonus  # Нужно добавить в Джаву
        self._advantageArmyBonus = advantageArmyBonus  # Нужно добавить в Джаву
        self._receivedDmg = receivedDmg
        self._receivedArmyDmg = receivedArmyDmg

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
        return skillDictionary[self.__activeSkill]

    def getAdvantageBonus(self):
        return self._advantageBonus

    def getAdvantageArmyBonus(self):
        return self._advantageArmyBonus

    def getReceivedDmg(self):
        return self._receivedDmg

    def getReceivedArmyDmg(self):
        return self._receivedArmyDmg

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

    def setAdvantageArmyBonus(self, value):
        self._advantageArmyBonus = value

    def setReceivedDmg(self, value):
        self._receivedDmg = value

    def setReceivedArmyDmg(self, value):
        self._receivedArmyDmg = value

    def getAliveSoldiers(self):
        return math.ceil(self._armyQuantity * self._armyHp / self._armyMaxHp) if self._armyMaxHp else 0


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
    if not duel_participants.getSecondCombatant().isCharacter():
        duel_participants.getSecondCombatant().setActiveSkill(monster_ai(duel_participants.getSecondCombatant()))

    duel_participants.setSecondCombatant(calculate_damage(duel_participants.getFirstCombatant(), duel_participants.getSecondCombatant()))
    duel_participants.setFirstCombatant(calculate_damage(duel_participants.getSecondCombatant(), duel_participants.getFirstCombatant()))
