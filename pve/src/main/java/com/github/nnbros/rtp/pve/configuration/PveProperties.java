package com.github.nnbros.rtp.pve.configuration;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Setter
@Getter
@ConfigurationProperties(prefix = "pve")
public class PveProperties {
	@NotNull
	private Api api = new Api();
	@NotNull
	private ThreadPool actionProcessorThreadPool = new ThreadPool();
	@NotNull
	private Battle battle = new Battle();

	@Setter
	@Getter
	public static class ThreadPool {
		@Positive
		private int min = 10;
		@Positive
		private int max = 30;
		@Positive
		private int queue = 10_000;
		private Duration awaitTerminationTimeout = Duration.ofMinutes(5);
		@NotBlank
		private String threadNamePrefix = "ActionProcessor-";
	}

	@Setter
	@Getter
	public static class Api {
		@URL
		private String baseUrl = "/api/v1";
		@URL
		private String actionsEndpointPrefix = "/actions";
	}

	@Setter
	@Getter
	public static class Battle {
		@Positive
		private int maxActiveSkills = 3;
	}
}
