package com.github.nnbros.rtp.battleprocessor.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@Setter
@Getter
@ConfigurationProperties(prefix = "battle-processor")
public class BattleProcessorProperties {
	private Path mainScriptPath;
}
