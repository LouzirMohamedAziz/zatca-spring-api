package com.zatca.invoice.zatcaspringapi.Controllers;

import java.security.cert.X509Certificate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zatca.sdk.service.QrGenerationService;

@RequestMapping("/invoice/qr")
@RestController
public class QrGenerationController {

    QrGenerationService qrGenerationService;

    @PostMapping()
    public String createQRCode(@RequestBody String xmlContent) {
        return qrGenerationService.generateQrCode(xmlContent);
    }


    @PostMapping("/hash")
    public String invoiceHash(@RequestBody String xmlContent) {
        return qrGenerationService.getInvoiceHash(xmlContent);
    }

    @PostMapping("/certificate")
    public X509Certificate getCertificate(@RequestBody String certificate){
        return (X509Certificate) qrGenerationService.extractQrData(certificate);

    }
}
