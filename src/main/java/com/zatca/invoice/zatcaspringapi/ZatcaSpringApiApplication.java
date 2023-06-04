package com.zatca.invoice.zatcaspringapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.core.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Author : Mohamed Aziz LOUZIR
* **/
@ComponentScan()
@SpringBootApplication
public class ZatcaSpringApiApplication implements CommandLineRunner{

	private static Logger log;
	public static void main(String[] args) {

		SpringApplication.run(ZatcaSpringApiApplication.class, args);

		
        }

@Override
public void run(String... args) throws Exception {
	String line;
	try {
		Runtime rt = Runtime.getRuntime();
		String command = "bash -c 'cd /Users/louzir/Pictures/zatca-spring-api/lib/zatca-einvoicing-sdk-234-R3.2.0/Data/Input && fatoora -csr -csrConfig csr-config-example-EN.properties'";
		Process pr = rt.exec(command);
		InputStream inputStream = pr.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			log.info("The following command has been executed: "+command);
		}
		try {
			int exitCode = pr.waitFor();
			System.out.println("Exit Code:" + exitCode);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	}

