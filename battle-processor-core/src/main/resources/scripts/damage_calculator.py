from skill_dictionary import skill_dictionary
import random
import math


class Changes():
    def __init__(self, hp, armyHp, receivedDmg, receivedArmyDmg, aliveSoldiers, soldiersDelta):
        self.newHp = hp
        self.newArmyHp = armyHp
        self.newReceivedDmg = receivedDmg
        self.newReceivedArmyDmg = receivedArmyDmg
        self.newAliveSoldiers = aliveSoldiers
        self.newSoldiersDelta = soldiersDelta
    # Method for == have uses in tests
    def __eq__(self, other):
        if isinstance(other, Changes):
            return (self.newHp == other.newHp and self.newArmyHp == other.newArmyHp and 
                    self.newReceivedDmg == other.newReceivedDmg and self.newReceivedArmyDmg == other.newReceivedArmyDmg and
                    self.newAliveSoldiers == other.newAliveSoldiers and self.newSoldiersDelta == other.newSoldiersDelta)
        return False


def calculate_damage(attacker, defender):
    changes = Changes(0, 0, 0, 0, 0, 0)

    # Get attacker values
    attacker_skill = skill_dictionary[attacker.getActiveSkill()]
    attacker_hero_adv_bonus = attacker.getAdvantageBonus()
    attacker_army_adv_bonus = attacker.getArmyAdvantageBonus()
    attacker_alive_soldiers = attacker.getAliveSoldiers()
    
    # Get defender values
    defender_skill = skill_dictionary[defender.getActiveSkill()]
    defender_hero_archetype = defender.getArchetype()
    defender_army_archetype = defender.getArmyArchetype()
    defender_alive_soldiers = defender.getAliveSoldiers()
    
    # Calculate totals
    attacker_hero_total_attack = attacker.getAtk() * (2 + attacker_skill.action_hero_atk)
    attacker_army_total_attack = (attacker.getArmyAtk() * (1 + attacker_skill.action_army_atk) * attacker_alive_soldiers)
    defender_hero_total_defense = defender.getDef() * (1 + defender_skill.action_hero_def)
    defender_army_total_defense = (defender.getArmyDef() * (1 + defender_skill.action_army_def) * defender_alive_soldiers)
    
    # Battle logic
    if attacker_alive_soldiers == 0:
        if defender_alive_soldiers == 0:
            hero_vs_hero = calc_attack(attacker_hero_total_attack, attacker_hero_adv_bonus, attacker_skill.skill_type,
                                   "CLASS", defender_hero_archetype, attacker_skill.bonus_against, 1)
            defender_hero_damage_taken = max(0, hero_vs_hero - defender_hero_total_defense)
            defender_army_damage_taken = 0
        else:
            hero_vs_hero = calc_attack(attacker_hero_total_attack, attacker_hero_adv_bonus, attacker_skill.skill_type,
                                   "CLASS", defender_hero_archetype, attacker_skill.bonus_against,
                                   attacker_skill.hero_damage_distribution)
            hero_vs_army = calc_attack(attacker_hero_total_attack, attacker_hero_adv_bonus, attacker_skill.skill_type,
                                   "CLASS", defender_army_archetype, attacker_skill.bonus_against,
                                   1 - attacker_skill.hero_damage_distribution)
            defender_hero_damage_taken = max(0, hero_vs_hero - defender_hero_total_defense)
            defender_army_damage_taken = max(0, hero_vs_army - defender_army_total_defense)
    else:
        if defender_alive_soldiers == 0:
            hero_vs_hero = calc_attack(attacker_hero_total_attack, attacker_hero_adv_bonus, attacker_skill.skill_type,
                                   "CLASS", defender_hero_archetype, attacker_skill.bonus_against, 1)
            army_vs_hero = calc_attack(attacker_army_total_attack, attacker_army_adv_bonus, attacker_skill.skill_type,
                                   "ARMY", defender_hero_archetype, attacker_skill.bonus_against, 1)
            defender_hero_damage_taken = max(0, hero_vs_hero + army_vs_hero - defender_hero_total_defense)
            defender_army_damage_taken = 0
        else:
            hero_vs_hero = calc_attack(attacker_hero_total_attack, attacker_hero_adv_bonus, attacker_skill.skill_type,
                                   "CLASS", defender_hero_archetype, attacker_skill.bonus_against,
                                   attacker_skill.hero_damage_distribution)
            hero_vs_army = calc_attack(attacker_hero_total_attack, attacker_hero_adv_bonus, attacker_skill.skill_type,
                                   "CLASS", defender_army_archetype, attacker_skill.bonus_against,
                                   1 - attacker_skill.hero_damage_distribution)
            army_vs_hero = calc_attack(attacker_army_total_attack, attacker_army_adv_bonus, attacker_skill.skill_type,
                                   "ARMY", defender_hero_archetype, attacker_skill.bonus_against,
                                   attacker_skill.army_damage_distribution)
            army_vs_army = calc_attack(attacker_army_total_attack, attacker_army_adv_bonus, attacker_skill.skill_type,
                                   "ARMY", defender_army_archetype, attacker_skill.bonus_against,
                                   1 - attacker_skill.army_damage_distribution)
            defender_hero_damage_taken = max(0, hero_vs_hero + army_vs_hero - defender_hero_total_defense)
            defender_army_damage_taken = max(0, hero_vs_army + army_vs_army - defender_army_total_defense)

    # Set changes
    changes.newHp = int(max(0, defender.getHp() - defender_hero_damage_taken))
    changes.newArmyHp = int(max(0, defender.getArmyHp() - defender_army_damage_taken))
    changes.newReceivedDmg = defender_hero_damage_taken
    changes.newReceivedArmyDmg = defender_army_damage_taken
    changes.newAliveSoldiers = int(math.ceil(float(defender.getArmyQuantity() * changes.newArmyHp) / defender.getArmyMaxHp()) if defender.getArmyMaxHp() else 0)
    changes.newSoldiersDelta = int(changes.newAliveSoldiers - defender_alive_soldiers)

    return changes


# damage = total_attack * adv_bonus * damage_distribution - total_defense
def calc_attack(atk_total, adv_bonus, skill_type, damage_type, def_archetype, bonus_against, damage_distribution, spread = 0.2):
    attack = atk_total * (1 + adv_bonus) if skill_type == damage_type and def_archetype == bonus_against else atk_total
    attack_distributed = attack * damage_distribution
    attack_randomized = int(attack_distributed * random.uniform(1-spread, 1+spread))
    return attack_randomized


def apply_changes(changes, combatant):
    combatant.setHp(changes.newHp)
    combatant.setArmyHp(changes.newArmyHp)
    combatant.setReceivedDmg(changes.newReceivedDmg)
    combatant.setReceivedArmyDmg(changes.newReceivedArmyDmg)
    combatant.setAliveSoldiers(changes.newAliveSoldiers)
    combatant.setSoldiersDelta(changes.newSoldiersDelta)
