package com.zatca.invoice.zatcaspringapi.Services;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zatca.sdk.service.InvoiceRequestGenerationService;

@Service
public class InvoiceService {

    Logger log;

    public String generateInvoice(String invoicePath, String invoiceName) {
        var invoiceRequest = "";
        var invoice = invoicePath.concat(String.valueOf(File.separatorChar)).concat(invoiceName);
        try {
            var xmlFile = FileUtils.readFileToString(new File(invoice), StandardCharsets.UTF_8);
            InvoiceRequestGenerationService invoiceRequestGenerationService = new InvoiceRequestGenerationService();
            Properties invoiceProps = invoiceRequestGenerationService.getInvoiceData(xmlFile);
            invoiceRequest = invoiceRequestGenerationService.prepareRequestBodyString(invoiceProps);

        } catch (Exception exception) {

            log.error("unable to generate invoice hash" + exception);
        }
        
        return invoice;
    }

}
