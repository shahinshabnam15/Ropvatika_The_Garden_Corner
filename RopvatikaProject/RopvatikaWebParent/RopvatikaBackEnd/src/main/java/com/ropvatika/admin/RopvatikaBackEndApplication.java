package com.ropvatika.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.ropvatika.common.entity","com.ropvatika.admin.user"})
public class RopvatikaBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(RopvatikaBackEndApplication.class, args);
	}

}
