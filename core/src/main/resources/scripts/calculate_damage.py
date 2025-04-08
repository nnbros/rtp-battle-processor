def calculate_damage(duel_participants, skill1, skill2):
    h1_atk = duel_participants.getFirstCombatant.getAtk() * (2 + skill1.actionHeroAtk)
    a1_atk = (duel_participants.getFirstCombatant.getArmyAtk() * (1 + skill1.actionArmyAtk)
              * duel_participants.getFirstCombatant.getAliveSoldiers())
    h1_def = duel_participants.getFirstCombatant.getDef() * (1 + skill1.actionHeroDef)
    a1_def = (duel_participants.getFirstCombatant.getArmyDef() * (1 + skill1.actionArmyDef)
              * duel_participants.getFirstCombatant.getAliveSoldiers())
    h2_atk = duel_participants.getSecondCombatant.getAtk() * (2 + skill2.actionHeroAtk)
    a2_atk = (duel_participants.getSecondCombatant.getArmyAtk() * (1 + skill2.actionArmyAtk)
              * duel_participants.getSecondCombatant.getAliveSoldiers())
    h2_def = duel_participants.getSecondCombatant.getDef() * (1 + skill2.actionHeroDef)
    a2_def = (duel_participants.getSecondCombatant.getArmyDef() * (1 + skill2.actionArmyDef)
              * duel_participants.getSecondCombatant.getAliveSoldiers())

    if duel_participants.getSecondCombatant.getArmyHp() == 0:
        h2_dmg = max(0, int(
            h1_atk * (1 + skill1.typeBonus) if skill1.typeOwner == 1 and duel_participants.getSecondCombatant.getType == skill1.type else h1_atk +
            a1_atk * (1 + skill1.typeBonus) if skill1.typeOwner == 2 and duel_participants.getSecondCombatant.getType == skill1.type else a1_atk
        ) - h2_def)
        a2_dmg = 0
    else:
        a2_dmg = max(0, int(
            (h1_atk * (1 + skill1.typeBonus) if skill1.typeOwner == 1 and duel_participants.getSecondCombatant.getArmyType == skill1.type else h1_atk)
            * (1 - skill1.heroDamageDistribution) +
            (a1_atk * (1 + skill1.typeBonus) if skill1.typeOwner == 2 and duel_participants.getSecondCombatant.getArmyType == skill1.type else a1_atk)
            * (1 - skill1.armyDamageDistribution)
        ) - a2_def)
        h2_dmg = max(0, int(
            (h1_atk * (1 + skill1.typeBonus) if skill1.typeOwner == 1 and duel_participants.getSecondCombatant.getArmyType == skill1.type else h1_atk)
            * (1 - skill1.heroDamageDistribution) +
            (a1_atk * (1 + skill1.typeBonus) if skill1.typeOwner == 2 and duel_participants.getSecondCombatant.getArmyType == skill1.type else a1_atk)
            * (1 - skill1.armyDamageDistribution)
        ) - h2_def)

    if duel_participants.getFirstCombatant.getArmyHp() == 0:
        h1_dmg = max(0, int(
            h2_atk * (1 + skill2.typeBonus) if skill2.typeOwner == 1 and duel_participants.getFirstCombatant.getType == skill2.type else h2_atk +
            a2_atk * (1 + skill2.typeBonus) if skill2.typeOwner == 2 and duel_participants.getFirstCombatant.getType == skill2.type else a2_atk
        ) - h1_def)
        a1_dmg = 0
    else:
        a1_dmg = max(0, int(
            (h2_atk * (1 + skill2.typeBonus) if skill2.typeOwner == 1 and duel_participants.getFirstCombatant.getArmyType == skill2.type else h2_atk)
            * (1 - skill2.heroDamageDistribution) +
            (a2_atk * (1 + skill2.typeBonus) if skill2.typeOwner == 2 and duel_participants.getFirstCombatant.getArmyType == skill2.type else a2_atk)
            * (1 - skill2.armyDamageDistribution)
        ) - a1_def)
        h1_dmg = max(0, int(
            (h2_atk * (1 + skill2.typeBonus) if skill2.typeOwner == 1 and duel_participants.getSecondCombatant.getArmyType == skill2.type else h2_atk)
            * (1 - skill2.heroDamageDistribution) +
            (a2_atk * (1 + skill2.typeBonus) if skill2.typeOwner == 2 and duel_participants.getSecondCombatant.getArmyType == skill2.type else a2_atk)
            * (1 - skill2.armyDamageDistribution)
        ) - h1_def)

    duel_participants.getFirstCombatant.setHp = duel_participants.getFirstCombatant.getHp - h1_dmg
    duel_participants.getFirstCombatant.setArmyHp = duel_participants.getFirstCombatant.getArmyHp - a1_dmg
    duel_participants.getFirstCombatant.setReceivedDmg = h1_dmg
    duel_participants.getFirstCombatant.setReceivedArmyDmg = a1_dmg
    duel_participants.getSecondCombatant.setHp = duel_participants.getSecondCombatant.getHp - h1_dmg
    duel_participants.getSecondCombatant.setArmyHp = duel_participants.getSecondCombatant.getArmyHp - a1_dmg
    duel_participants.getSecondCombatant.setReceivedDmg = h1_dmg
    duel_participants.getSecondCombatant.setReceivedArmyDmg = a1_dmg
    return duel_participants


