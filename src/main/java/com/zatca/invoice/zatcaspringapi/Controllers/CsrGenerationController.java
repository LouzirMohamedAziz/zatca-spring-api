package com.zatca.invoice.zatcaspringapi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zatca.invoice.zatcaspringapi.Services.CSRequestService;
import com.zatca.sdk.service.CsrGenerationService;

@RequestMapping("/invoice/CSR")
@RestController
public class CsrGenerationController {

    CsrGenerationService csrGenerationService;
    CSRequestService csrRequestService;

    @PostMapping("/load")
    public Boolean load() {
        return csrGenerationService.loadInput();
    }

    @PostMapping("/validate")
    public Boolean validate() {
        return csrGenerationService.validateInput();
    }

    @PostMapping("/process")
    public Boolean process() {
        return csrGenerationService.process();
    }

    @PostMapping("generate")
    public void generateCSR(String privateKeyFile) {
        csrRequestService.generateCSR(privateKeyFile);
    }

    @GetMapping("/generate")
    public Boolean generate() {
        return csrGenerationService.generateOutput();
    }
}
