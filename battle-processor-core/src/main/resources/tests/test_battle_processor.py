import unittest
from mock import patch
from tests.stubs import getDummyParticipants
from scripts.battle_processor import calculate_duel_turn
from scripts.damage_calculator import TurnSnapshot


class TestBattleProcessor(unittest.TestCase):    
    # Patch all functions from other modules    
    @patch('scripts.battle_processor.choose_monster_skill')
    @patch('scripts.battle_processor.calculate_turn_snapshot')
    @patch('scripts.battle_processor.apply_turn_snapshot')
    def test_calculate_duel_turn_check_function_calls(self, mock_apply_turn_snapshot, mock_calculate_turn_snapshot, mock_choose_monster_skill):
        # Create Stubs
        dueldummies = getDummyParticipants()
        changes = TurnSnapshot(1000, 1000)

        # Configure Mocks
        mock_choose_monster_skill.return_value = "assault"
        mock_calculate_turn_snapshot.return_value = changes 

        # Start test
        calculate_duel_turn(dueldummies)

        # Check results
        mock_choose_monster_skill.assert_called_once_with(dueldummies.getSecondCombatant())           # AI called only for NPC
        self.assertEqual(mock_calculate_turn_snapshot.call_count, 2)               # Methods called times equal to number of combatants
        self.assertEqual(mock_apply_turn_snapshot.call_count, 2)
        
        
