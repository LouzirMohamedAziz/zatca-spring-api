package com.zatca.invoice.zatcaspringapi.Services;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.zatca.invoice.zatcaspringapi.Configurations.FileLoader;
import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;
import com.zatca.invoice.zatcaspringapi.Repositories.InvoiceRequestRepository;
import com.zatca.sdk.dto.ApplicationPropertyDto;
import com.zatca.sdk.service.GeneratorTemplate;
import com.zatca.sdk.service.InvoiceSigningService;
import com.zatca.sdk.service.flow.ValidationProcessorImpl;
import com.zatca.sdk.service.validation.Result;

@Service
public class InvoiceSignService {

    private final InvoiceRequestRepository invoiceRequestRepository;

    @Autowired
    public InvoiceSignService(InvoiceRequestRepository invoiceRequestRepository) {
        this.invoiceRequestRepository = invoiceRequestRepository;
    }

    public Result generateSignedInvoice(InvoiceRequest invoiceRequest){

        // Signing the Invoice
        GeneratorTemplate generateService = new InvoiceSigningService();
        ApplicationPropertyDto configuration = new ApplicationPropertyDto();
        configuration.setGenerateSignature(true);
        configuration.setInvoiceFileName(invoiceRequest.getInvoicePath().concat(String.valueOf(File.separatorChar)).concat(invoiceRequest.getInvoiceName()));
        configuration.setOutputInvoiceFileName(invoiceRequest.getInvoicePath().concat(String.valueOf(File.separatorChar)).concat(invoiceRequest.getSignedInvoiceName()));
        generateService.generate(configuration);

        // Generating QR Code
        String baseDir = System.getProperty("user.dir");
        String invoiceFilePath = baseDir + "/lib/zatca-einvoicing-sdk-234-R3.2.0/signedInvoice.xml";
        configuration.setGenerateQr(true);
        configuration.setInvoiceFileName(invoiceRequest.getInvoicePath()+invoiceRequest.getInvoiceName());
        configuration.setOutputInvoiceFileName(invoiceFilePath);
        generateService.generate(configuration);
        invoiceRequestRepository.save(invoiceRequest);

        // Validation the Invoice
        FileLoader fileLoader = new FileLoader();
        File file = fileLoader.loadFile(invoiceFilePath);
        ValidationProcessorImpl validationProcessorImpl = new ValidationProcessorImpl();
        try {
            return validationProcessorImpl.run(file);
        } catch (InvalidCanonicalizerException | CanonicalizationException | IOException | ParserConfigurationException
                | TransformerException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }


}


