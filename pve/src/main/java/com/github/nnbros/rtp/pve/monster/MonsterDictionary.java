package com.github.nnbros.rtp.pve.monster;

import com.github.nnbros.rtp.common.api.dto.character.Archetype;

public record MonsterDictionary(Integer id,
								String name,
								Archetype type,
								Integer baseHp,
								Integer baseAtk,
								Integer baseDef,
								String armyName,
								Archetype armyType,
								Integer armyBaseQuantity,
								Integer armyBaseHp,
								Integer armyBaseAtk,
								Integer armyBaseDef,
								Integer armyTier) {
}
