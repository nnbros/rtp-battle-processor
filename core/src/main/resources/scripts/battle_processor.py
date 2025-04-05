from com.github.nnbros.rtp.battleprocessor.core import DuelParticipants, Combatant


class CombatantPy(Combatant):
    def __init__(self, entityName, type, hp, atk, def_, armyName, armyType, armyHp, armyAtk, armyDef, armyQuantity,
                 isCharacter, activeSkill, receivedDmg, receivedArmyDmg):
        self._entityName = entityName
        self._type = type
        self._hp = hp
        self._atk = atk
        self._def = def_  # Avoid using "def" since it's a reserved keyword
        self._armyName = armyName
        self._armyType = armyType
        self._armyHp = armyHp
        self._armyAtk = armyAtk
        self._armyDef = armyDef
        self._armyQuantity = armyQuantity
        self._isCharacter = isCharacter
        self._activeSkill = activeSkill
        self._receivedDmg = receivedDmg
        self._receivedArmyDmg = receivedArmyDmg

    def getEntityName(self):
        return self._entityName

    def getType(self):
        return self._type

    def getHp(self):
        return self._hp

    def getAtk(self):
        return self._atk

    def getDef(self):
        return self._def

    def getArmyName(self):
        return self._armyName

    def getArmyType(self):
        return self._armyType

    def getArmyHp(self):
        return self._armyHp

    def getArmyAtk(self):
        return self._armyAtk

    def getArmyDef(self):
        return self._armyDef

    def getArmyQuantity(self):
        return self._armyQuantity

    def getIsCharacter(self):
        return self._isCharacter

    def getActiveSkill(self):
        return self._activeSkill

    def getReceivedDmg(self):
        return self._receivedDmg

    def getReceivedArmyDmg(self):
        return self._receivedArmyDmg

    def setEntityName(self, value):
        self._entityName = value

    def setType(self, value):
        self._type = value

    def setHp(self, value):
        self._hp = value

    def setAtk(self, value):
        self._atk = value

    def setDef(self, value):
        self._def = value

    def setArmyName(self, value):
        self._armyName = value

    def setArmyType(self, value):
        self._armyType = value

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
        self._activeSkill = value

    def setReceivedDmg(self, value):
        self._receivedDmg = value

    def setReceivedArmyDmg(self, value):
        self._receivedArmyDmg = value


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
    # Put your code here
    # e.g.:
    # def current_hp = duel_participants.getFirstCombatant().getHp() - duel_participants.getSecondCombatant().getAtk()
    # duel_participants.getFirstCombatant().setHp(current_hp)
    duel_participants.getFirstCombatant().setHp(0)
    duel_participants.getSecondCombatant().setHp(2000)

    return duel_participants
