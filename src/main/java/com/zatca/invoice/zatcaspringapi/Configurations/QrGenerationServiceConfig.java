package com.zatca.invoice.zatcaspringapi.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zatca.sdk.service.QrGenerationService;

@Configuration
public class QrGenerationServiceConfig {

    @Bean
    public QrGenerationService qrGenerationService()  {
        return new QrGenerationService();
    }
}
