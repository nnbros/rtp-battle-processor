package com.github.nnbros.rtp.pve.action.battle;

import com.github.nnbros.rtp.common.action.ActionPipeline;
import com.github.nnbros.rtp.common.action.ActionRegistrar;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.github.nnbros.rtp.common.action.ActionPipelines.*;
import static com.github.nnbros.rtp.pve.action.battle.BattleAction.BATTLE_TURN;
import static com.github.nnbros.rtp.pve.action.battle.BattleAction.START_BATTLE;

@Getter
@Component
public class BattleActionRegistrar implements ActionRegistrar {
	private final Map<String, ActionPipeline> actionPipelines;

	public BattleActionRegistrar(BattleService battleService, BattleTelegramClient battleTelegramClient) {
		actionPipelines = initPipelines(battleService, battleTelegramClient);
	}

	private Map<String, ActionPipeline> initPipelines(BattleService battleService, BattleTelegramClient battleTelegramClient) {
		return Map.of(
				START_BATTLE.getActionName(), create(battleService::initiateBattle, battleTelegramClient::sendInitialBattleMessage),
				BATTLE_TURN.getActionName(), create(battleService::processBattleTurn, battleTelegramClient::sendResultMessage, battleTelegramClient::sendTurnOptionsMenu)
		);
	}
}
