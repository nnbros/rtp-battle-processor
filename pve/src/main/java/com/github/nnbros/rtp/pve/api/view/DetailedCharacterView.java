package com.github.nnbros.rtp.pve.api.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailedCharacterView extends Character {

	@JsonProperty("activeClass")
	protected ActiveCharacterClass activeClass;

	@JsonProperty("activeArmy")
	protected ActiveCharacterArmy activeArmy;
}
