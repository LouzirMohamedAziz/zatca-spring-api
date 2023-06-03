package com.zatca.invoice.zatcaspringapi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zatca.invoice.zatcaspringapi.Services.CSRequestService;
import com.zatca.sdk.service.CsrGenerationService;

@RequestMapping("/invoice")
@RestController
public class CsrGenerationController {

    CsrGenerationService csrGenerationService;
    CSRequestService csrRequestService;

    @PostMapping()
    public Boolean load() {
        return csrGenerationService.loadInput();
    }

    @PostMapping()
    public Boolean validate() {
        return csrGenerationService.validateInput();
    }

    @PostMapping()
    public Boolean process() {
        return csrGenerationService.process();
    }

    @PostMapping()
    public void generateCSR(String privateKeyFile) {
        csrRequestService.generateCSR(privateKeyFile);
    }

    @GetMapping()
    public Boolean generate() {
        return csrGenerationService.generateOutput();
    }
}
