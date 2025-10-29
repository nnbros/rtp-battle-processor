import unittest
from mock import patch, Mock
from scripts.battle_processor import calculate_duel_turn, CombatantPy, DuelParticipantsPy 
from scripts.damage_calculator import Changes


class TestBattleProcessor(unittest.TestCase):    
    # Patch all functions from other modules    
    @patch('scripts.battle_processor.choose_monster_skill')
    @patch('scripts.battle_processor.calculate_damage')
    @patch('scripts.battle_processor.apply_changes')
    def test_calculate_duel_turn_check_function_calls(self, mock_apply_changes, mock_calculate_damage, mock_choose_monster_skill):
        # Create Stubs
        dummy1 = CombatantPy("bandit_leader", "A", 5000, 3000, 500, 100, "B", "C", 4500, 4500, 500, 100, 3, False, "reckless_attack", True, True, 0, 0, 3, 0)
        dummy2 = CombatantPy("slime", "A", 5000, 3000, 500, 100, "B", "C", 4500, 4500, 500, 100, 3, True, "assault", True, True, 0, 0, 3, 0)
        dueldummies = DuelParticipantsPy(dummy1, dummy2)
        changes = Changes(1000, 1000, 1000, 1000, 2, -1)

        # Configure Mocks
        mock_choose_monster_skill.return_value = "assault"
        mock_calculate_damage.return_value = changes 

        # Start test
        calculate_duel_turn(dueldummies)

        # Check results
        mock_choose_monster_skill.assert_called_once_with(dummy1)           # AI called only for NPC
        self.assertEqual(mock_calculate_damage.call_count, 2)               # Methods called times equal to number of combatants
        self.assertEqual(mock_apply_changes.call_count, 2)
        
        
