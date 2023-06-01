package com.zatca.invoice.zatcaspringapi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zatca.sdk.service.InvoiceValidationService;

@RequestMapping("/invoice/validation")
@RestController
public class InvoiceValidationController {

    InvoiceValidationService invoiceValidationService;

    @GetMapping("/load")
    public boolean load(){
        return invoiceValidationService.loadInput();
    }


    @GetMapping("/validate")
    public boolean validate(){
        return  invoiceValidationService.process();
    }

    @GetMapping("/generate")
    public boolean generate(){
        return invoiceValidationService.generateOutput();
    }

}
