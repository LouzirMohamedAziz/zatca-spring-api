package com.zatca.invoice.zatcaspringapi.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.zatca.invoice.zatcaspringapi.Configurations.QrGenerationServiceConfig;
import com.zatca.invoice.zatcaspringapi.Models.Qr;
import com.zatca.invoice.zatcaspringapi.Repositories.QrRepository;
import com.zatca.sdk.dto.ApplicationPropertyDto;
import com.zatca.sdk.service.GeneratorTemplate;
import com.zatca.sdk.service.QrGenerationService;

@Service
public class QrService {

    private final QrRepository qrRepository;
    private final QrGenerationServiceConfig qrGenerationServiceConfig;
    private final ResourceLoader resourceLoader;

    @Autowired
    public QrService(QrRepository qrRepository, QrGenerationServiceConfig qrGenerationServiceConfig, ResourceLoader resourceLoader) {
        this.qrRepository = qrRepository;
        this.qrGenerationServiceConfig = qrGenerationServiceConfig;
        this.resourceLoader = resourceLoader;
    }

    public Qr generateQRCODE(Qr qr) {
        GeneratorTemplate generateService = new QrGenerationService();
        ApplicationPropertyDto configuration = new ApplicationPropertyDto();
        configuration.setGenerateQr(true);
        configuration.setInvoiceFileName(qr.getSignedInvoiceFilePath());
        configuration.setOutputInvoiceFileName("/Users/louzir/Pictures/zatca-spring-api/lib/zatca-einvoicing-sdk-234-R3.2.0/qrSignedInvoice.xml");
        generateService.generate(configuration);
        return qrRepository.save(qr);
        
    // }}
    //     Resource resourcePath = resourceLoader.getResource("file:" + qr.getSignedInvoiceFilePath());
    //     try {
    //         byte[] fileBytes = StreamUtils.copyToByteArray(resourcePath.getInputStream());
    //         String signedInvoiceString = new String(fileBytes, StandardCharsets.UTF_8);
    //         qrGenerationServiceConfig.qrGenerationService().generateQrCode(signedInvoiceString);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return qrRepository.save(qr);
    // }
}
}
