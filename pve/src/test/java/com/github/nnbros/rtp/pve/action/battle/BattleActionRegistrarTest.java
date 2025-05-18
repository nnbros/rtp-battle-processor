package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.pve.PveTest;
import com.github.nnbros.rtp.pve.action.ActionContext;
import com.github.nnbros.rtp.pve.action.ActionPipeline;
import com.github.nnbros.rtp.pve.action.ActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;

import static com.github.nnbros.rtp.pve.BotTestUtils.createTestActionContext;
import static org.mockito.Mockito.*;

public class BattleActionRegistrarTest extends PveTest {
    @Mock
    private BattleService battleService;

    @Mock
    private BattleTelegramClient telegramClient;

    @InjectMocks
    private BattleActionRegistrar registrar;

    private final HashMap<String, ActionPipeline> actionPipelines = new HashMap<>();

    @BeforeEach
    public void initPipelines() {
        actionPipelines.clear();
        registrar.register(actionPipelines);
    }

    @Test
    public void executeInitiateBattlePipeline() {
        ActionPipeline actionPipeline = actionPipelines.get(BattleAction.START_BATTLE.getActionName());
        ActionContext testActionContext = createTestActionContext();
        ActionResult<Battle> testActionResult = new ActionResult<>(testActionContext, mock(Battle.class));
        when(battleService.initiateBattle(testActionContext)).thenReturn(testActionResult);

        actionPipeline.execute(testActionContext);

        verify(battleService, times(1)).initiateBattle(testActionContext);
        verify(telegramClient, times(1)).sendTurnOptionsMenu(testActionResult);
    }

    @Test
    public void executeProcessBattlePipelineIfBattleIsNotFinished() {
        ActionPipeline actionPipeline = actionPipelines.get(BattleAction.BATTLE_TURN.getActionName());
        ActionContext testActionContext = createTestActionContext();
        ActionResult<Battle> testActionResult = new ActionResult<>(testActionContext, false, mock(Battle.class));
        when(battleService.processBattleTurn(testActionContext)).thenReturn(testActionResult);

        actionPipeline.execute(testActionContext);

        verify(battleService, times(1)).processBattleTurn(testActionContext);
        verify(telegramClient, times(1)).sendTurnOptionsMenu(testActionResult);
    }

    @Test
    public void executeProcessBattlePipelineIfBattleIsFinished() {
        ActionPipeline actionPipeline = actionPipelines.get(BattleAction.BATTLE_TURN.getActionName());
        ActionContext testActionContext = createTestActionContext();
        ActionResult<Battle> testActionResult = new ActionResult<>(testActionContext, true, mock(Battle.class));
        when(battleService.processBattleTurn(testActionContext)).thenReturn(testActionResult);

        actionPipeline.execute(testActionContext);

        verify(battleService, times(1)).processBattleTurn(testActionContext);
        verify(telegramClient, times(1)).sendResultMessage(testActionResult);
    }
}
