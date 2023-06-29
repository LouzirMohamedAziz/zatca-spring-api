package com.zatca.invoice.zatcaspringapi.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zatca.invoice.zatcaspringapi.Models.Qr;
import com.zatca.invoice.zatcaspringapi.Repositories.QrRepository;
import com.zatca.sdk.dto.ApplicationPropertyDto;
import com.zatca.sdk.service.GeneratorTemplate;
import com.zatca.sdk.service.QrGenerationService;

@Service
public class QrService {

    private final QrRepository qrRepository;

    @Autowired
    public QrService(QrRepository qrRepository) {
        this.qrRepository = qrRepository;
    }

    public Qr generateQRCODE(Qr qr) {
        String baseDir = System.getProperty("user.dir");
        String invoiceFilePath = baseDir + "/lib/zatca-einvoicing-sdk-234-R3.2.0/signedInvoice.xml";
        qrRepository.save(qr);
        GeneratorTemplate generateService = new QrGenerationService();
        ApplicationPropertyDto configuration = new ApplicationPropertyDto();
        configuration.setGenerateQr(true);
        configuration.setInvoiceFileName(qr.getSignedInvoiceFilePath());
        configuration.setOutputInvoiceFileName(invoiceFilePath);
        generateService.generate(configuration);
        return qrRepository.save(qr);

}
}
