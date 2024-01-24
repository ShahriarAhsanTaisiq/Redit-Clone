package com.dev.springreditclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class SpringreditcloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringreditcloneApplication.class, args);
	}

}
