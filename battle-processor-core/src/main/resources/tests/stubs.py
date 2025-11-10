from scripts.battle_processor import CombatantPy, DuelParticipantsPy
def getDummyPlayer():
    return CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, True, "assault", 0.2, 0.2, 0, 0, 3, 0)
def getDummyNpc():
    return CombatantPy("slime", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", True, True, 0, 0, 3, 0)
def getDummyParticipants(dummy1 = getDummyPlayer(), dummy2 = getDummyNpc()):
    return DuelParticipantsPy(dummy1, dummy2)