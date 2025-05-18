package com.github.nnbros.rtp.pve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.github.nnbros.rtp")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}