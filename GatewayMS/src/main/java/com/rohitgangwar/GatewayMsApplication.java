package com.rohitgangwar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMsApplication {

	public static void main(String[] args) {
		System.out.println("hello");
		SpringApplication.run(GatewayMsApplication.class, args);
	}

}
