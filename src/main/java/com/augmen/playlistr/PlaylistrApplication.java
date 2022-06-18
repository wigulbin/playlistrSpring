package com.augmen.playlistr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.augmen.playlistr"})
@EnableJpaRepositories(basePackages = {"com.augmen.playlistr"})
public class PlaylistrApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaylistrApplication.class, args);
	}

}
