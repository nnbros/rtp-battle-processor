package com.github.nnbros.rtp.pve.action.battle;

import com.github.guronas.telegram.bot.elements.TelegramElementRegistry;
import com.github.nnbros.rtp.pve.action.ActionResult;
import com.github.nnbros.rtp.pve.telegram.AbstractTelegramClient;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class BattleTelegramClient extends AbstractTelegramClient {

	public BattleTelegramClient(TelegramClient telegramClient, TelegramElementRegistry elementRegistry) {
		super(telegramClient, elementRegistry);
	}

	public void sendTurnOptionsMenu(ActionResult<Battle> actionResult) {

	}

	public void sendResultMessage(ActionResult<Battle> actionResult) {

	}
}
