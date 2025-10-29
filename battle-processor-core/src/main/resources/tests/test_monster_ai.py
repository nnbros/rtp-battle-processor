import unittest
from scripts.monster_ai import choose_monster_skill, monster_db
from scripts.skill_dictionary import skill_dictionary, Skill
from scripts.battle_processor import CombatantPy

dummy1 = CombatantPy("", "A", 5000, 3000, 500, 100, "B", "C", 4500, 4500, 500, 100, 3, False, "", True, True, 0, 0, 3, 0)
# Put all monster names in this list
monsters = ["bandit_leader", "slime"]
# Put all skill names in this list
skills = ["reckless_attack", "assault", "defense", "full_defense", "lead", "battle_readiness",
            "duel", "cleave", "elimination", "fire_bomb","onslaught", "raid", "iron_tide","unearth",
            "hold_the_line", "make_the_way", "slime_jab", "splashing1", "splashing2", "tentacle1", "tentacle2"]
class TestMonsterAi(unittest.TestCase):
    # Checks all monsters and their ai functions to be in monster_db, also checks all ai functions to return right values for all possible skills.
    def test_monster_ai_functions(self):        
        for monster in monsters:
            dummy1.setEntityName(monster)
            for skill in skills:
                dummy1.setActiveSkill(skill)
                try:
                    skill_dictionary[choose_monster_skill(dummy1)]
                except TypeError:
                    raise TypeError(monster, " Not in the monster_db")
                except KeyError:
                    raise KeyError(monster, " Monster return wrong skill after:", skill)
    # All skill names must be in skill_dictionary
    def test_skill_dictionary(self):
        for skill in skills:
            try:
                skill_dictionary[skill]
            except KeyError:
                raise KeyError(skill, " Not found in skill_dictionary")



            