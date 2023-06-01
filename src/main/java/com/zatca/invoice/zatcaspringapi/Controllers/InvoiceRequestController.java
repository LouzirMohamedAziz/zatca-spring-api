package com.zatca.invoice.zatcaspringapi.Controllers;

import java.util.Properties;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zatca.sdk.service.InvoiceRequestGenerationService;

@RequestMapping("/invoice")
@RestController
public class InvoiceRequestController {

    Properties properties;
    InvoiceRequestGenerationService invoiceRequestGenerationService;

    @PostMapping("data")
    public Properties invoiceData(String invoice) {
        return invoiceRequestGenerationService.getInvoiceData(invoice);
    }

    @PostMapping("/body-string")
    public String prepRequestBodyString(Properties properties){
        return invoiceRequestGenerationService.prepareRequestBodyString(properties);
    }

    @PostMapping("body")
    public boolean prepRequestBody(Properties properties) {
        return invoiceRequestGenerationService.prepareRequestBody(properties);
    }



}
