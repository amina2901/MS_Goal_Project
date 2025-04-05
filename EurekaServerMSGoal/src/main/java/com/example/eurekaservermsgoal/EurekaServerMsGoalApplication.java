package com.example.eurekaservermsgoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerMsGoalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerMsGoalApplication.class, args);
	}

}
