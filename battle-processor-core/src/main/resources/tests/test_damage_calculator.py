import unittest
from mock import patch
from scripts.damage_calculator import calculate_damage, calc_attack
from scripts.battle_processor import CombatantPy


class TestDamageCalculator(unittest.TestCase):
    # With equal combatants and equal attack, should return equal results. 
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_damage_equal_results(self, mock_calc_attack):
        mock_calc_attack.return_value = 2000
        dummy1 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 3, 0)
        dummy2 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 3, 0)
        changes1 = calculate_damage(dummy2, dummy1)
        changes2 = calculate_damage(dummy1, dummy2)
        try:
            self.assertEqual(changes1, changes2)
        except AssertionError:
            raise AssertionError("Changes returned by calculate_damage are not equal.")

    # Test logic with all armies dead       
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_damage_no_army(self, mock_calc_attack):
        dummy1 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 0, 0)
        dummy2 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 0, 0)
        mock_calc_attack.return_value = 2000
        calculate_damage(dummy1, dummy2)
        mock_calc_attack.assert_called_once_with(2000, 0.2, 'BASIC', 'CLASS', 'archetype', '', 1)

    # Test logic with attacker army alive
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_damage_attacker_army_alive(self, mock_calc_attack):
        dummy1 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 3, 0)
        dummy2 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 0, 0)
        mock_calc_attack.return_value = 2000
        calculate_damage(dummy1, dummy2)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'archetype', '', 1)
        mock_calc_attack.assert_any_call(1500, 0.2, 'BASIC', 'ARMY', 'archetype', '', 1)
        self.assertEqual(mock_calc_attack.call_count, 2)

    # Test logic with defender army alive
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_damage_defender_army_alive(self, mock_calc_attack):
        dummy1 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 0, 0)
        dummy2 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 3, 0)
        mock_calc_attack.return_value = 2000
        calculate_damage(dummy1, dummy2)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'archetype', '', 0.5)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'armyArchetype', '', 0.5)
        self.assertEqual(mock_calc_attack.call_count, 2)

    # Test logic with both armies alive
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_damage_all_alive(self, mock_calc_attack):
        dummy1 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 3, 0)
        dummy2 = CombatantPy("entityName", "archetype", 5000, 3000, 500, 100, "armyName", "armyArchetype", 4500, 4500, 500, 100, 3, False, "assault", 0.2, 0.2, 0, 0, 3, 0)
        mock_calc_attack.return_value = 2000
        calculate_damage(dummy1, dummy2)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'archetype', '', 0.5)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'armyArchetype', '', 0.5)
        mock_calc_attack.assert_any_call(1500, 0.2, 'BASIC', 'ARMY', 'archetype', '', 0.5)
        mock_calc_attack.assert_any_call(1500, 0.2, 'BASIC', 'ARMY', 'armyArchetype', '', 0.5)
        self.assertEqual(mock_calc_attack.call_count, 4)

    # calc_attack must return assumed value
    def test_calc_attack(self):
        self.assertEqual(calc_attack(2000, 0.2, 'CLASS', 'CLASS', 'SWORDSMAN', 'SWORDSMAN', 1 , 0), 2400)
