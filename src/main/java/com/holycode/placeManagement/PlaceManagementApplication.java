package com.holycode.placeManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlaceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaceManagementApplication.class, args);
	}

}
