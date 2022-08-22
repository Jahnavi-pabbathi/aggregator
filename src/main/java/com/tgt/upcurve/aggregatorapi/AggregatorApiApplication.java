package com.tgt.upcurve.aggregatorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AggregatorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AggregatorApiApplication.class, args);
	}

}
