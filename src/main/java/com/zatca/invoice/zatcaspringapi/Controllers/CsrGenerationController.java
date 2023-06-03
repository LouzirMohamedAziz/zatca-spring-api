package com.zatca.invoice.zatcaspringapi.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zatca.sdk.service.CsrGenerationService;

@RequestMapping("/invoice")
@RestController
public class CsrGenerationController {

    CsrGenerationService csrGenerationService;
    
    @PostMapping()
    public String generateCSR(String csrConfigFilePath, String csrConfigFileName) {
        
        String line;

		try {

			Runtime rt = Runtime.getRuntime();
			String command = "bash -c 'cd " + csrConfigFilePath + " && fatoora -csr -csrConfig " + csrConfigFileName + "'";
            Process pr = rt.exec(command);
            InputStream inputStream = pr.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
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

        return "CsrGenerationService - csr and private key have been generated successfully. ";

        }
    }
