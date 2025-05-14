package com.github.nnbros.rtp.battleprocessor.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@Setter
@Getter
@ConfigurationProperties(prefix = "battle-processor")
public class BattleProcessorProperties {
	@NotNull
	private Path scriptsPath = Path.of("/scripts");
	@NotNull
	private String mainScriptName = "battle_processor.py";
}
