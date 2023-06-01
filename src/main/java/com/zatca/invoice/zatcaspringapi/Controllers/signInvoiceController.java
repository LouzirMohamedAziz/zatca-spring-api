package com.zatca.invoice.zatcaspringapi.Controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;
import com.zatca.invoice.zatcaspringapi.Models.InvoiceResponse;
import com.zatca.invoice.zatcaspringapi.Services.ComplianceInvoiceService;
import com.zatca.sdk.dto.ApplicationPropertyDto;
import com.zatca.sdk.service.InvoiceSigningService;

@RequestMapping("/invoice/")
@RestController
public class signInvoiceController {

    ApplicationPropertyDto applicationPropertyDto;
    InvoiceSigningService invoiceSigningService;
    ComplianceInvoiceService complianceInvoiceService;
    
    @PostMapping("/sign")
    public boolean signInvoice() {
        return invoiceSigningService.signInvoice();
    }


    @PostMapping("/generate")
    public boolean generateSignedInvoice(ApplicationPropertyDto apd){
        return invoiceSigningService.generateOutput();
    }

    @PostMapping("/v1/signInvoice")
    public InvoiceResponse retrieveSignedInvoice(@RequestBody InvoiceRequest invoiceRequest) throws Exception {
        InvoiceResponse response = complianceInvoiceService.process(invoiceRequest.getOtp(), invoiceRequest.getEncodedInvoice());
        return response;
    }

}
