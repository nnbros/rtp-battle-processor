class skill:
    def __init__(self, actionHeroAtk, actionHeroDef, actionArmyAtk,actionArmyDef, type, typeOwner, heroDamageDistribution, armyDamageDistribution):
        self.actionHeroAtk = actionHeroAtk
        self.actionHeroDef = actionHeroDef
        self.actionArmyAtk = actionArmyAtk
        self.actionArmyDef = actionArmyDef
        self.type = type                    # по какому типу врага эффективен 1 - кавалерия, 2 - мечник, 3 - копейщик
        self.typeOwner = typeOwner          # Чей скилл: 0 - базовый, 1 - Hero, 2 - Army
        self.heroDamageDistribution = heroDamageDistribution
        self.armyDamageDistribution = armyDamageDistribution


skillDictionary = {
    # Базовые
    "Reckless Attack": skill(3, 0, 0, 0, 0, 0, 0.5, 0.5),
    "Assault": skill(2, 1, 0, 0, 0, 0, 0.5, 0.5),
    "Defense": skill(1, 2, 0, 0, 0, 0, 0.5, 0.5),
    "Full Defense": skill(0, 3, 0, 0, 0, 0, 0.5, 0.5),
    "Lead": skill(1, 1, 1, 0, 0, 0, 0.5, 0.5),
    "Battle Readiness": skill(1, 1, 1, 0, 0, 0, 0.5, 0.5),
    # Воин
    "Duel": skill(1, 2, 0, 0, 3, 1, 0.75, 0.5),
    "Cleave": skill(2, 1, 0, 0, 3, 1, 0.25, 0.5),
    # Плут
    "Elimination": skill(2, 1, 0, 0, 0, 1, 1, 0.5),
    "Fire Bomb": skill(2, 1, 0, 0, 0, 1, 0.25, 0.5),
    # монстры
}