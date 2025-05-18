package com.github.nnbros.rtp.pve.api.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nnbros.rtp.pve.monster.Archetype;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ActiveCharacterSkill(
		@JsonProperty("name") String name,
		@JsonProperty("skillType") SkillType skillType,
		@JsonProperty("effectiveAgainst") Archetype effectiveAgainst) {
}
