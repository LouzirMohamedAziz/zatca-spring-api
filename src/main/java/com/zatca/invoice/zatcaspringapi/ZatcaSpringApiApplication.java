package com.zatca.invoice.zatcaspringapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class ZatcaSpringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZatcaSpringApiApplication.class, args);
	}

}
