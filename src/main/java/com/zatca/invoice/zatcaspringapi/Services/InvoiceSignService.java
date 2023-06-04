package com.zatca.invoice.zatcaspringapi.Services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;
import com.zatca.invoice.zatcaspringapi.Repositories.InvoiceRequestRepository;
import com.zatca.sdk.dto.ApplicationPropertyDto;
import com.zatca.sdk.service.GeneratorTemplate;
import com.zatca.sdk.service.InvoiceSigningService;

@Service
public class InvoiceSignService {

    private final InvoiceRequestRepository invoiceRequestRepository;

    @Autowired
    public InvoiceSignService(InvoiceRequestRepository invoiceRequestRepository) {
        this.invoiceRequestRepository = invoiceRequestRepository;
    }

    public InvoiceRequest generateSignedInvoice(InvoiceRequest invoiceRequest){
        GeneratorTemplate generateService = new InvoiceSigningService();
        ApplicationPropertyDto configuration = new ApplicationPropertyDto();
        configuration.setGenerateSignature(true);
        configuration.setInvoiceFileName(invoiceRequest.getInvoicePath().concat(String.valueOf(File.separatorChar)).concat(invoiceRequest.getInvoiceName()));
        configuration.setOutputInvoiceFileName(invoiceRequest.getInvoicePath().concat(String.valueOf(File.separatorChar)).concat(invoiceRequest.getSignedInvoiceName()));
        generateService.generate(configuration);
        return invoiceRequestRepository.save(invoiceRequest);
        
    }}


