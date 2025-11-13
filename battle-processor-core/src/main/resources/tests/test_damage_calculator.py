import unittest
from mock import patch
from tests.stubs import getDummyPlayer
from scripts.damage_calculator import TurnSnapshot, calculate_turn_snapshot, calc_attack, apply_turn_snapshot


class TestDamageCalculator(unittest.TestCase):
    # With equal combatants and equal attack, should return equal results. 
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_snapshot_equal_results(self, mock_calc_attack):
        mock_calc_attack.return_value = 2000
        dummy1 = getDummyPlayer()
        dummy2 = getDummyPlayer()
        snapshot1 = calculate_turn_snapshot(dummy2, dummy1)
        snapshot2 = calculate_turn_snapshot(dummy1, dummy2)
        try:
            self.assertEqual(snapshot1, snapshot2)
        except AssertionError:
            raise AssertionError("Snapshots returned by calculate_snapshot are not equal.")

    # Test logic with all armies dead       
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_snapshot_no_army(self, mock_calc_attack):
        dummy1 = getDummyPlayer()
        dummy1.setAliveSoldiers(0)
        dummy2 = getDummyPlayer()        
        dummy2.setAliveSoldiers(0)
        mock_calc_attack.return_value = 2000
        calculate_turn_snapshot(dummy1, dummy2)
        mock_calc_attack.assert_called_once_with(2000, 0.2, 'BASIC', 'CLASS', 'archetype', '', 1)

    # Test logic with attacker army alive
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_snapshot_attacker_army_alive(self, mock_calc_attack):
        dummy1 = getDummyPlayer()
        dummy2 = getDummyPlayer()
        dummy2.setAliveSoldiers(0)
        mock_calc_attack.return_value = 2000
        calculate_turn_snapshot(dummy1, dummy2)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'archetype', '', 1)
        mock_calc_attack.assert_any_call(1500, 0.2, 'BASIC', 'ARMY', 'archetype', '', 1)
        self.assertEqual(mock_calc_attack.call_count, 2)

    # Test logic with defender army alive
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_snapshot_defender_army_alive(self, mock_calc_attack):
        dummy1 = getDummyPlayer()
        dummy1.setAliveSoldiers(0)
        dummy2 = getDummyPlayer()
        mock_calc_attack.return_value = 2000
        calculate_turn_snapshot(dummy1, dummy2)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'archetype', '', 0.5)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'armyArchetype', '', 0.5)
        self.assertEqual(mock_calc_attack.call_count, 2)

    # Test logic with both armies alive
    @patch('scripts.damage_calculator.calc_attack')
    def test_calculate_snapshot_all_alive(self, mock_calc_attack):
        dummy1 = getDummyPlayer()
        dummy2 = getDummyPlayer()
        mock_calc_attack.return_value = 2000
        calculate_turn_snapshot(dummy1, dummy2)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'archetype', '', 0.5)
        mock_calc_attack.assert_any_call(2000, 0.2, 'BASIC', 'CLASS', 'armyArchetype', '', 0.5)
        mock_calc_attack.assert_any_call(1500, 0.2, 'BASIC', 'ARMY', 'archetype', '', 0.5)
        mock_calc_attack.assert_any_call(1500, 0.2, 'BASIC', 'ARMY', 'armyArchetype', '', 0.5)
        self.assertEqual(mock_calc_attack.call_count, 4)

    # calc_attack must return assumed value
    def test_calc_attack(self):
        self.assertEqual(calc_attack(2000, 0.2, 'CLASS', 'CLASS', 'SWORDSMAN', 'SWORDSMAN', 1 , 0), 2400)

    # Check apply_turn_snapshot
    def test_apply_turn_snapshot(self):
        dummy1 = getDummyPlayer()
        snapshot = TurnSnapshot(1000,2000)
        apply_turn_snapshot(snapshot, dummy1)
        self.assertEqual(dummy1.getHp(), 2000)
        self.assertEqual(dummy1.getArmyHp(), 2500)
        self.assertEqual(dummy1.getReceivedDmg(), 1000)
        self.assertEqual(dummy1.getReceivedArmyDmg(), 2000)
        self.assertEqual(dummy1.getAliveSoldiers(), 2)
        self.assertEqual(dummy1.getSoldiersDelta(), -1)
