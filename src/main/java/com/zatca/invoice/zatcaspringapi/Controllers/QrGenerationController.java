package com.zatca.invoice.zatcaspringapi.Controllers;

import org.apache.logging.log4j.core.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.zatca.invoice.zatcaspringapi.Models.Qr;
import com.zatca.invoice.zatcaspringapi.Services.QrService;


@RequestMapping("/qr")
@RestController
public class QrGenerationController {

    private final QrService qrService;
    Logger log;
    
    public QrGenerationController(QrService qrService) {
        this.qrService = qrService;
    }

    @PostMapping()
    public String createQRCode(@RequestBody Qr qr) {
        try {
            qrService.generateQRCODE(qr);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();
            if (statusCode.is4xxClientError()) {
                log.error("Client error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            } else if (statusCode.is5xxServerError()) {
                log.error("Server error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            } else {
                log.error("HTTP error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            }
        } catch (Exception e) {
            log.error("An unexpected error occurred during QR generation", e);
        }
        return "QR code generated successsfuly!";
    }

}
