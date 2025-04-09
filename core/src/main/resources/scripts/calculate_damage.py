def calculate_damage(duel_participants, skill1, skill2):
    comb1 = duel_participants.getFirstCombatant
    comb2 = duel_participants.getSecondCombatant
    comb1_hero_type = comb1.getType()
    comb1_army_type = comb1.getArmyType()
    comb2_hero_type = comb2.getType()
    comb2_army_type = comb2.getArmyType()
    comb1_hero_adv_bonus = comb1.getAdvantageBonus()
    comb1_army_adv_bonus = comb1.getAdvantageArmyBonus()
    comb2_hero_adv_bonus = comb2.getAdvantageBonus()
    comb2_army_adv_bonus = comb2.getAdvantageArmyBonus()
    
    hero1_total_attack = comb1.getAtk() * (2 + skill1.actionHeroAtk)
    army1_total_attack = (comb1.getArmyAtk() * (1 + skill1.actionArmyAtk) * comb1.getAliveSoldiers())
    hero1_total_defense = comb1.getDef() * (1 + skill1.actionHeroDef)
    army1_total_defense = (comb1.getArmyDef() * (1 + skill1.actionArmyDef) * comb1.getAliveSoldiers())
    hero2_total_attack = comb2.getAtk() * (2 + skill2.actionHeroAtk)
    army2_total_attack = (comb2.getArmyAtk() * (1 + skill2.actionArmyAtk) * comb2.getAliveSoldiers())
    hero2_total_defense = comb2.getDef() * (1 + skill2.actionHeroDef)
    army2_total_defense = (comb2.getArmyDef() * (1 + skill2.actionArmyDef) * comb2.getAliveSoldiers())

# damage = total_attack * adv_bonus * damage_distribution - total_defense

    if comb2.getArmyHp() == 0:
        hero2_damage_taken = max(0, int(
            hero1_total_attack * (1 + comb1_hero_adv_bonus) if skill1.type == "CLASS" and comb2_hero_type == skill1.bonus_against else hero1_total_attack +
            army1_total_attack * (1 + comb1_army_adv_bonus) if skill1.type == "ARMY" and comb2_hero_type == skill1.bonus_against else army1_total_attack
        ) - hero2_total_defense)
        army2_damage_taken = 0
    else:
        army2_damage_taken = max(0, int(
            (hero1_total_attack * (1 + comb1_hero_adv_bonus) if skill1.type == "CLASS" and comb2_army_type == skill1.bonus_against else hero1_total_attack)
            * (1 - skill1.hero_damage_distribution) +
            (army1_total_attack * (1 + comb1_army_adv_bonus) if skill1.type == "ARMY" and comb2_army_type == skill1.bonus_against else army1_total_attack)
            * (1 - skill1.army_damage_distribution)
        ) - army2_total_defense)
        hero2_damage_taken = max(0, int(
            (hero1_total_attack * (1 + comb1_hero_adv_bonus) if skill1.type == "CLASS" and comb2_army_type == skill1.bonus_against else hero1_total_attack)
            * (1 - skill1.hero_damage_distribution) +
            (army1_total_attack * (1 + comb1_army_adv_bonus) if skill1.type == "ARMY" and comb2_army_type == skill1.bonus_against else army1_total_attack)
            * (1 - skill1.army_damage_distribution)
        ) - hero2_total_defense)

    if comb1.getArmyHp() == 0:
        hero1_damage_taken = max(0, int(
            hero2_total_attack * (1 + comb2_hero_adv_bonus) if skill2.type == "CLASS" and comb1_hero_type == skill2.bonus_against else hero2_total_attack +
            army2_total_attack * (1 + comb2_army_adv_bonus) if skill2.type == "ARMY" and comb1_hero_type == skill2.bonus_against else army2_total_attack
        ) - hero1_total_defense)
        army1_damage_taken = 0
    else:
        army1_damage_taken = max(0, int(
            (hero2_total_attack * (1 + comb2_hero_adv_bonus) if skill2.type == "CLASS" and comb1_army_type == skill2.bonus_against else hero2_total_attack)
            * (1 - skill2.hero_damage_distribution) +
            (army2_total_attack * (1 + comb2_army_adv_bonus) if skill2.type == "ARMY" and comb1_army_type == skill2.bonus_against else army2_total_attack)
            * (1 - skill2.army_damage_distribution)
        ) - army1_total_defense)
        hero1_damage_taken = max(0, int(
            (hero2_total_attack * (1 + comb2_hero_adv_bonus) if skill2.type == "CLASS" and comb1_army_type == skill2.bonus_against else hero2_total_attack)
            * (1 - skill2.hero_damage_distribution) +
            (army2_total_attack * (1 + comb2_army_adv_bonus) if skill2.type == "ARMY" and comb1_army_type == skill2.bonus_against else army2_total_attack)
            * (1 - skill2.army_damage_distribution)
        ) - hero1_total_defense)

    comb1.setHp(comb1.getHp() - hero1_damage_taken)
    comb1.setArmyHp(comb1.getArmyHp() - army1_damage_taken)
    comb1.setReceivedDmg(hero1_damage_taken)
    comb1.setReceivedArmyDmg(army1_damage_taken)
    comb2.setHp(comb2.getHp() - hero2_damage_taken)
    comb2.setArmyHp(comb2.getArmyHp() - army2_damage_taken)
    comb2.setReceivedDmg(hero2_damage_taken)
    comb2.setReceivedArmyDmg(army2_damage_taken)
    return duel_participants


