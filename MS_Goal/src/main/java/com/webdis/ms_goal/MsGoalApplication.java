package com.webdis.ms_goal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;


@EnableDiscoveryClient
@SpringBootApplication
public class MsGoalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGoalApplication.class, args);
	}
	@Autowired
	private GoalRepository goalRepository;
	@Bean
	ApplicationRunner init(){
		return (args)->{
			goalRepository.save(new Goal("Fitness", LocalDateTime.of(2025, 6, 1, 10, 0)));
			goalRepository.save(new Goal("Learn Java", LocalDateTime.of(2025, 12, 31, 23, 59)));
			goalRepository.save(new Goal("Travel to Spain", LocalDateTime.of(2026, 8, 15, 12, 0)));
			goalRepository.save(new Goal("Read 10 Books", LocalDateTime.of(2025, 9, 1, 18, 0)));
			goalRepository.save(new Goal("Start a Blog", LocalDateTime.of(2025, 5, 20, 9, 0)));
			goalRepository.save(new Goal("Complete Internship", LocalDateTime.of(2025, 7, 15, 17, 30)));
			goalRepository.save(new Goal("Volunteer Abroad", LocalDateTime.of(2026, 3, 1, 8, 0)));
			goalRepository.save(new Goal("Run a Half-Marathon", LocalDateTime.of(2025, 10, 10, 7, 0)));
			goalRepository.save(new Goal("Save Money for Laptop", LocalDateTime.of(2025, 11, 25, 20, 0)));
			goalRepository.save(new Goal("Take Online Course in AI", LocalDateTime.of(2025, 12, 1, 14, 0)));


		};
}}
