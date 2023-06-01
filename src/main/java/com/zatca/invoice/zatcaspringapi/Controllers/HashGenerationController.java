package com.zatca.invoice.zatcaspringapi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zatca.sdk.service.HashGenerationService;

@RequestMapping("/invoice/hash")
@RestController
public class HashGenerationController {
    
    HashGenerationService hashGenerationService;

    @GetMapping("/load")
    public boolean load() {
        return hashGenerationService.loadInput();
    }

    @GetMapping("/validate")
    public boolean validate() {
        return hashGenerationService.validateInput();
    }

    @GetMapping("/process")
    public boolean proce() {
        return hashGenerationService.process();
    }

    @GetMapping("/hash")
    public boolean generateHash() {
        return hashGenerationService.generateOutput();
    }

}
