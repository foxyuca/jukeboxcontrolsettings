package com.jukebox.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JukeboxControlSettingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JukeboxControlSettingsApplication.class, args);
	}

}
