package com.github.nnbros.rtp.pve.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents localized values dictionary.
 */
//TODO validator?
@Setter
@Getter
@ConfigurationProperties(prefix = "localization")
public class Localization {
	@NotNull
	private Map<String, MonsterSkill> monsterSkills = new HashMap<>();

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MonsterSkill {
		@NotBlank
		private String name;
	}
}
