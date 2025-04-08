from core.src.main.resources.scripts.skill_dictionary import skillDictionary


def monster_ai(duel_participants):

    match duel_participants.getSecondCombatant.getEntityName():
        case "Bandit Leader":
            match duel_participants.getSecondCombatant.getActiveSkill():
                case "Reckless Attack":
                    skill2 = skillDictionary["Assault"]
                case "Assault":
                    skill2 = skillDictionary["Full Defense"]
                case _:
                    skill2 = skillDictionary["Reckless Attack"]
        case _:
            skill2 = skillDictionary["Assault"]

    duel_participants.getSecondCombatant.setActiveSkill = skill2.__name__

    return duel_participants
