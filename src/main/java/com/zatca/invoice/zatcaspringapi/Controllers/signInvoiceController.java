package com.zatca.invoice.zatcaspringapi.Controllers;


import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;
import com.zatca.invoice.zatcaspringapi.Services.InvoiceSignService;

@RequestMapping("/invoice")
@RestController
public class signInvoiceController {

    private final InvoiceSignService invoiceSignService;
    Logger log;

    @Autowired
    public signInvoiceController(InvoiceSignService invoiceSignService) {
        this.invoiceSignService = invoiceSignService;
    }


    @PostMapping("/sign")
    public String signInvoice(@RequestBody InvoiceRequest invoiceRequest){
        try {
            invoiceSignService.generateSignedInvoice(invoiceRequest);
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
            log.error("An unexpected error occurred during signInvoice", e);
        }
        return "Invoice signed successfully!";
    }



}
