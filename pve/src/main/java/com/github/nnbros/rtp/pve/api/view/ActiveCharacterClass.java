package com.github.nnbros.rtp.pve.api.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nnbros.rtp.pve.monster.Archetype;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ActiveCharacterClass(
		@JsonProperty("type") Archetype type,
		@JsonProperty("name") String name,
		@JsonProperty("baseHp") Integer baseHp,
		@JsonProperty("baseAtk") Integer baseAtk,
		@JsonProperty("baseDef") Integer baseDef,
		@JsonProperty("advantageBonus") Float advantageBonus,
		@JsonProperty("experience") Long experience) {
}
