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
	@NotNull
	private Map<String, Monster> monsters = new HashMap<>();
	@NotNull
	private Map<String, Clazz> classes = new HashMap<>();
	@NotNull
	private Map<String, Army> armies = new HashMap<>();
	@NotNull
	private Map<String, Skill> skills = new HashMap<>();
	@NotNull
	private Map<String, String> battle = new HashMap<>();

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MonsterSkill {
		@NotBlank
		private String name;
	}

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Monster {
		@NotBlank
		private String name;
	}

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Clazz {
		@NotBlank
		private String name;
		@NotBlank
		private String description;
	}

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Army {
		@NotBlank
		private String name;
		@NotBlank
		private String description;
		@NotBlank
		private String type;
	}

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Skill {
		@NotBlank
		private String name;
		private String description;
	}
}
