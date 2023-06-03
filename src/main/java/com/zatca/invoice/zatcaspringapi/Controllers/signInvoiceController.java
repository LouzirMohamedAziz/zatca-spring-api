package com.zatca.invoice.zatcaspringapi.Controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zatca.sdk.dto.ApplicationPropertyDto;
import com.zatca.sdk.service.InvoiceSigningService;

@RequestMapping("/invoice/")
@RestController
public class signInvoiceController {

    ApplicationPropertyDto applicationPropertyDto;
    InvoiceSigningService invoiceSigningService;
    
    @PostMapping("/sign")
    public boolean signInvoice() {
        return invoiceSigningService.signInvoice();
    }


    @PostMapping("/generate")
    public boolean generateSignedInvoice(ApplicationPropertyDto apd){
        return invoiceSigningService.generateOutput();
    }


}
