from core.src.main.resources.scripts.skill_dictionary import skillDictionary


def calculate_damage(attacker, defender):
    attacker_skill = skillDictionary[attacker.getActiveSkill()]
    defender_skill = skillDictionary[defender.getActiveSkill()]
    attacker_hero_adv_bonus = attacker.getAdvantageBonus()
    attacker_army_adv_bonus = attacker.getAdvantageArmyBonus()
    defender_hero_archetype = defender.getArchetype()
    defender_army_archetype = defender.getArmyType()

    attacker_hero_total_attack = attacker.getAtk() * (2 + attacker_skill.action_hero_atk)
    attacker_army_total_attack = (attacker.getArmyAtk() * (1 + attacker_skill.action_army_atk) * attacker.getAliveSoldiers())
    defender_hero_total_defense = defender.getDef() * (1 + defender_skill.actionHeroDef)
    defender_army_total_defense = (defender.getArmyDef() * (1 + defender_skill.actionArmyDef) * defender.getAliveSoldiers())

# damage = total_attack * adv_bonus * damage_distribution - total_defense

    if defender.getAliveSoldiers() == 0:
        defender_hero_damage_taken = max(0, int(
            attacker_hero_total_attack * (1 + attacker_hero_adv_bonus) if attacker_skill.skill_type == "CLASS" and defender_hero_archetype == attacker_skill.bonus_against else attacker_hero_total_attack +
            attacker_army_total_attack * (1 + attacker_army_adv_bonus) if attacker_skill.skill_type == "ARMY" and defender_hero_archetype == attacker_skill.bonus_against else attacker_army_total_attack
        ) - defender_hero_total_defense)
        defender_army_damage_taken = 0
    else:
        defender_army_damage_taken = max(0, int(
            (attacker_hero_total_attack * (1 + attacker_hero_adv_bonus) if attacker_skill.skill_type == "CLASS" and defender_army_archetype == attacker_skill.bonus_against else attacker_hero_total_attack)
            * (1 - attacker_skill.hero_damage_distribution) +
            (attacker_army_total_attack * (1 + attacker_army_adv_bonus) if attacker_skill.skill_type == "ARMY" and defender_army_archetype == attacker_skill.bonus_against else attacker_army_total_attack)
            * (1 - attacker_skill.army_damage_distribution)
        ) - defender_army_total_defense)
        defender_hero_damage_taken = max(0, int(
            (attacker_hero_total_attack * (1 + attacker_hero_adv_bonus) if attacker_skill.skill_type == "CLASS" and defender_hero_archetype == attacker_skill.bonus_against else attacker_hero_total_attack)
            * attacker_skill.hero_damage_distribution +
            (attacker_army_total_attack * (1 + attacker_army_adv_bonus) if attacker_skill.skill_type == "ARMY" and defender_hero_archetype == attacker_skill.bonus_against else attacker_army_total_attack)
            * attacker_skill.army_damage_distribution
        ) - defender_hero_total_defense)

    defender.setReceivedDmg(defender_hero_damage_taken)
    defender.setReceivedArmyDmg(defender_army_damage_taken)
    return defender
