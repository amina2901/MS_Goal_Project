package com.example.apigatewaymsgoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayMsGoalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayMsGoalApplication.class, args);
	}
@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route("Goal",r->r.path("/goals/**")
						.uri("http://localhost:8051"))
				.build();

}
}
