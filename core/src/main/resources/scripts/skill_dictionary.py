class skill:
    def __init__(self, actionHeroAtk, actionHeroDef, actionArmyAtk,actionArmyDef, bonus_against, type, hero_damage_distribution, army_damage_distribution):
        self.actionHeroAtk = actionHeroAtk
        self.actionHeroDef = actionHeroDef
        self.actionArmyAtk = actionArmyAtk
        self.actionArmyDef = actionArmyDef
        self.bonus_against = bonus_against                  
        self.type = type
        self.hero_damage_distribution = hero_damage_distribution
        self.army_damage_distribution = army_damage_distribution


skillDictionary = {
    # Basic
    "reckless_attack": skill(3, 0, 0, 0, "", "BASIC", 0.5, 0.5),
    "assault": skill(2, 1, 0, 0, "", "BASIC", 0.5, 0.5),
    "defense": skill(1, 2, 0, 0, "", "BASIC", 0.5, 0.5),
    "full_defense": skill(0, 3, 0, 0, "", "BASIC", 0.5, 0.5),
    "lead": skill(1, 1, 1, 0, "", "BASIC", 0.5, 0.5),
    "battle_readiness": skill(1, 1, 1, 0, "", "BASIC", 0.5, 0.5),
    # Class
    # Warrior
    "duel": skill(1, 2, 0, 0, "SPEARMAN", "CLASS", 0.75, 0.5),
    "cleave": skill(2, 1, 0, 0, "SPEARMAN", "CLASS", 0.25, 0.5),
    # Rogue
    "elimination": skill(2, 1, 0, 0, "", "CLASS", 1, 0.5),
    "fire_bomb": skill(2, 1, 0, 0, "", "CLASS", 0.25, 0.5),
    # Army
    # Cavalry
    "onslaught ": skill(1, 0, 2, 0, "SWORDSMAN", "ARMY", 0.5, 0.5),
    "raid": skill(1, 0, 2, 0, "SWORDSMAN", "ARMY", 0.5, 0.5),
    # Swordsman
    "iron_tide": skill(1, 0, 1, 1, "SPEARMAN", "ARMY", 0.5, 0.5),
    "unearth": skill(1, 1, 1, 0, "SPEARMAN", "ARMY", 0.5, 0.5),
    # Spearman
    "hold_the_line": skill(0, 1, 0, 2, "CAVALRY", "ARMY", 0.5, 0.5),
    "make_the_way": skill(2, 0, 0, 1, "CAVALRY", "ARMY", 0.5, 0.5),
    # Monsters
    # Slime
    "slime_jab": skill(1, 1, 0, 0, "", "CLASS", 0.5, 0.5),
    "splashing1": skill(1, 0, 0, 0, "", "CLASS", 0.5, 0.5),
    "splashing2": skill(3, 0, 0, 0, "", "CLASS", 0.25, 0.5),
    "tentacle1": skill(0, 1, 0, 0, "", "CLASS", 0.5, 0.5),
    "tentacle2": skill(3, 0, 0, 0, "", "CLASS", 0.75, 0.5),
}