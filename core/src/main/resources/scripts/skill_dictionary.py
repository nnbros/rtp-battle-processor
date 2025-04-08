class skill:
    def __init__(self, actionHeroAtk, actionHeroDef, actionArmyAtk,actionArmyDef, type, typeOwner, typeBonus, heroDamageDistribution, armyDamageDistribution):
        self.actionHeroAtk = actionHeroAtk
        self.actionHeroDef = actionHeroDef
        self.actionArmyAtk = actionArmyAtk
        self.actionArmyDef = actionArmyDef
        self.type = type                    # по какому типу врага эффективен
        self.typeOwner = typeOwner          # Чей скилл: 0 - базовый, 1 - Hero, 2 - Army
        self.typeBonus = typeBonus          # Какой дополнительный урон
        self.heroDamageDistribution = heroDamageDistribution
        self.armyDamageDistribution = armyDamageDistribution

#def __init__(self, actionHeroAtk, actionHeroDef, actionArmyAtk,actionArmyDef, type, typeOwner, typeBonus, heroDamageDistribution, armyDamageDistribution):
skillDictionary = {
    #Воин
    "Reckless Attack": skill("Reckless Attack", 3, 0, 0, 0, 0, 0, 0, 0.5, 0.5),
    "Assault": skill("Assault", 2, 1, 0, 0, 0, 0, 0, 0.5, 0.5),
    "Full Defense": skill("Full Defense", 0, 3, 0, 0, 0, 0, 0, 0.5, 0.5),

    #монстры
}