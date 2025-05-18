package com.github.nnbros.rtp.pve.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
@EnableConfigurationProperties({PveProperties.class, Localization.class})
public class PveConfiguration {

	@Bean(name = "actionPipelineExecutor")
	public ThreadPoolTaskExecutor actionPipelineExecutor(PveProperties pveProperties) {
		PveProperties.ThreadPool actionProcessor = pveProperties.getActionProcessorThreadPool();
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutorBuilder().corePoolSize(actionProcessor.getMin())
				.maxPoolSize(actionProcessor.getMax())
				.threadNamePrefix(actionProcessor.getThreadNamePrefix())
				.awaitTermination(true)
				.awaitTerminationPeriod(actionProcessor.getAwaitTerminationTimeout())
				.build();
		executor.initialize();
		return executor;
	}
}
