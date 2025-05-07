class Skill:
    def __init__(self, action_hero_atk, action_hero_def, action_army_atk, action_army_def, bonus_against, skill_type,
                 hero_damage_distribution, army_damage_distribution):
        self.actionHeroAtk = action_hero_atk
        self.actionHeroDef = action_hero_def
        self.actionArmyAtk = action_army_atk
        self.actionArmyDef = action_army_def
        self.bonus_against = bonus_against                  
        self.skill_type = skill_type
        self.hero_damage_distribution = hero_damage_distribution
        self.army_damage_distribution = army_damage_distribution


skillDictionary = {
    # Basic
    "reckless_attack": Skill(3, 0, 0, 0, "", "BASIC", 0.5, 0.5),
    "assault": Skill(2, 1, 0, 0, "", "BASIC", 0.5, 0.5),
    "defense": Skill(1, 2, 0, 0, "", "BASIC", 0.5, 0.5),
    "full_defense": Skill(0, 3, 0, 0, "", "BASIC", 0.5, 0.5),
    "lead": Skill(1, 1, 1, 0, "", "BASIC", 0.5, 0.5),
    "battle_readiness": Skill(1, 1, 1, 0, "", "BASIC", 0.5, 0.5),
    # Class
    # Warrior
    "duel": Skill(1, 2, 0, 0, "SPEARMAN", "CLASS", 0.75, 0.5),
    "cleave": Skill(2, 1, 0, 0, "SPEARMAN", "CLASS", 0.25, 0.5),
    # Rogue
    "elimination": Skill(2, 1, 0, 0, "", "CLASS", 1, 0.5),
    "fire_bomb": Skill(2, 1, 0, 0, "", "CLASS", 0.25, 0.5),
    # Army
    # Cavalry
    "onslaught ": Skill(1, 0, 2, 0, "SWORDSMAN", "ARMY", 0.5, 0.5),
    "raid": Skill(1, 0, 2, 0, "SWORDSMAN", "ARMY", 0.5, 0.5),
    # Swordsman
    "iron_tide": Skill(1, 0, 1, 1, "SPEARMAN", "ARMY", 0.5, 0.5),
    "unearth": Skill(1, 1, 1, 0, "SPEARMAN", "ARMY", 0.5, 0.5),
    # Spearman
    "hold_the_line": Skill(0, 1, 0, 2, "CAVALRY", "ARMY", 0.5, 0.5),
    "make_the_way": Skill(2, 0, 0, 1, "CAVALRY", "ARMY", 0.5, 0.5),
    # Monsters
    # Slime
    "slime_jab": Skill(1, 1, 0, 0, "", "CLASS", 0.5, 0.5),
    "splashing1": Skill(1, 0, 0, 0, "", "CLASS", 0.5, 0.5),
    "splashing2": Skill(3, 0, 0, 0, "", "CLASS", 0.25, 0.5),
    "tentacle1": Skill(0, 1, 0, 0, "", "CLASS", 0.5, 0.5),
    "tentacle2": Skill(3, 0, 0, 0, "", "CLASS", 0.75, 0.5),
}
