package com.zatca.invoice.zatcaspringapi.Controllers;


import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.zatca.invoice.zatcaspringapi.Models.Validation;
import com.zatca.invoice.zatcaspringapi.Services.ValidationService;
import com.zatca.sdk.service.validation.Result;

@RequestMapping("/validation")
@RestController
public class InvoiceValidationController {

    private final ValidationService validationService;
    Logger log;

    @Autowired
    public InvoiceValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping()
    public ResponseEntity<Result> valdiateInvoiceImpl(@RequestBody Validation validation) {
        try {
            Result result = validationService.validityCheck(validation);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
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
            return null;
        } catch (Exception e) {
            log.error("An unexpected error occurred during signInvoice", e);
            // Add a return statement here to provide a response to the client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    



}
