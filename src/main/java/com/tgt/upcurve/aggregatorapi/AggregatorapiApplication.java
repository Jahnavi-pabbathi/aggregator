package com.tgt.upcurve.aggregatorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AggregatorapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AggregatorapiApplication.class, args);
	}

}
