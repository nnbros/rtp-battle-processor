package com.github.nnbros.rtp.battleprocessor.core;

import com.github.nnbros.rtp.battleprocessor.jython.JythonService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {
	private final JythonService jythonService;

	public DuelParticipants calculateDuelTurn(@NonNull DuelParticipantsImpl duelParticipants) {
		return jythonService.call(Function.calculateDuelTurn.getFunctionName(), duelParticipants, DuelParticipants.class);
	}
}
