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
    String baseDir = System.getProperty("user.dir");

    @Autowired
    public InvoiceSignService(InvoiceRequestRepository invoiceRequestRepository) {
        this.invoiceRequestRepository = invoiceRequestRepository;
    }

    public static String modifyBaseDir(String baseDir) {
        if (baseDir.contains("lib")) {
            return "";
        } else {
            return baseDir;
        }
    }
    public Result generateSignedInvoice(InvoiceRequest invoiceRequest){

        // Signing the Invoice
        GeneratorTemplate generateService = new InvoiceSigningService();
        ApplicationPropertyDto configuration = new ApplicationPropertyDto();
        configuration.setGenerateSignature(true);
        System.out.println("Loading and Signing the following Invoice: "+invoiceRequest.getInvoicePath().concat(String.valueOf(File.separatorChar)).concat(invoiceRequest.getInvoiceName()));
        configuration.setInvoiceFileName(invoiceRequest.getInvoicePath().concat(String.valueOf(File.separatorChar)).concat(invoiceRequest.getInvoiceName()));
        System.out.println("Generating Signed Invoice:  "+invoiceRequest.getInvoicePath().concat(String.valueOf(File.separatorChar)).concat(invoiceRequest.getSignedInvoiceName()));
        configuration.setOutputInvoiceFileName(invoiceRequest.getInvoicePath().concat(String.valueOf(File.separatorChar)).concat(invoiceRequest.getSignedInvoiceName()));
        generateService.generate(configuration);
        System.out.println("Signed Invoice Generated Successfuly! ");

        // Generating QR Code
        String signedInvoiceFilePath = invoiceRequest.getInvoicePath().concat("/").concat(invoiceRequest.getSignedInvoiceName());
        System.out.println("Signed Invoice File Path: "+signedInvoiceFilePath);
        configuration.setGenerateQr(true);
        configuration.setInvoiceFileName(invoiceRequest.getInvoicePath()+invoiceRequest.getInvoiceName());
        configuration.setOutputInvoiceFileName(signedInvoiceFilePath);
        generateService.generate(configuration);
        invoiceRequestRepository.save(invoiceRequest);

        // Validate the Invoice
        FileLoader fileLoader = new FileLoader();
        File file = fileLoader.loadFile(signedInvoiceFilePath);
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


