from monster_ai import *


def choose_monster_skill(monster):
    last_skill = monster.getActiveSkill()
    handler = monster_bd.get(monster.getEntityName(), ai_default)
    new_skill = handler(last_skill)
    return new_skill


monster_bd = {
    "bandit_leader": ai_bandit_leader,
    "slime": ai_slime
}
