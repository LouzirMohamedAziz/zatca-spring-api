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

import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;
import com.zatca.invoice.zatcaspringapi.Services.InvoiceSignService;
import com.zatca.sdk.service.validation.Result;


@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceSignService invoiceSignService;
    Logger log;

    @Autowired
    public InvoiceController(InvoiceSignService invoiceSignService) {
        this.invoiceSignService = invoiceSignService;
    }

    @PostMapping
    public ResponseEntity<Object> signInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        
        try {
            Result result = invoiceSignService.generateSignedInvoice(invoiceRequest);
            if(result!=null) {
                return ResponseEntity.ok(result);
            }
            else {
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
            return ResponseEntity.status(statusCode).body(responseBody);
        } catch (Exception e) {
            log.error("An unexpected error occurred during invoice signing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during invoice signing");
        }
    }
}