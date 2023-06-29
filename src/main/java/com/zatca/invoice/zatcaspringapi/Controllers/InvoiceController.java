package com.zatca.invoice.zatcaspringapi.Controllers;

import java.util.Map;

import org.apache.logging.log4j.core.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;
import com.zatca.invoice.zatcaspringapi.Models.Qr;
import com.zatca.invoice.zatcaspringapi.Models.Validation;
import com.zatca.invoice.zatcaspringapi.Services.InvoiceSignService;
import com.zatca.invoice.zatcaspringapi.Services.QrService;
import com.zatca.invoice.zatcaspringapi.Services.ValidationService;
import com.zatca.sdk.service.validation.Result;


@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceSignService invoiceSignService;
    private final QrService qrService;
    private final ValidationService validationService;
    Logger log;

    public InvoiceController(InvoiceSignService invoiceSignService, QrService qrService, ValidationService validationService) {
        this.invoiceSignService = invoiceSignService;
        this.qrService = qrService;
        this.validationService = validationService;
    }

@PostMapping
public ResponseEntity<Object> handleInvoiceRequest(@RequestBody Map<String, Object> request) {
    if (request.containsKey("invoicePath") && request.containsKey("invoiceName") && request.containsKey("signedInvoiceName")) {
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath((String) request.get("invoicePath"));
        invoiceRequest.setInvoiceName((String) request.get("invoiceName"));
        invoiceRequest.setSignedInvoiceName((String) request.get("signedInvoiceName"));
        return signInvoice(invoiceRequest);
    } else if (request.containsKey("signedInvoiceFile")) {
        Validation validation = new Validation();
        validation.setSignedInvoiceFile((String) request.get("signedInvoiceFile"));
        return validateInvoiceImpl(validation);
    } else if (request.containsKey("signedInvoiceFilePath")) {
        Qr qr = new Qr();
        qr.setSignedInvoiceFilePath((String) request.get("signedInvoiceFilePath"));
        return createQRCode(qr);
    } else {
        return ResponseEntity.badRequest().body("Invalid request body");
    }
}


    ResponseEntity<Object> validateInvoiceImpl(Validation validation) {
        try {
            Result result = validationService.validityCheck(validation);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();
            if (statusCode.is4xxClientError()) {
                log.error("Client error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            } else if (statusCode.is5xxServerError()) {
                log.error("Server error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            } else {
                log.error("HTTP error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            }
            return ResponseEntity.status(statusCode).body(responseBody);
        } catch (Exception e) {
            log.error("An unexpected error occurred during validation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during validation");
        }
    }

    private ResponseEntity<Object> createQRCode(Qr qr) {
        try {
            qrService.generateQRCODE(qr);
            return ResponseEntity.ok("QR code generated successfully!");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();
            if (statusCode.is4xxClientError()) {
                log.error("Client error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            } else if (statusCode.is5xxServerError()) {
                log.error("Server error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            } else {
                log.error("HTTP error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            }
            return ResponseEntity.status(statusCode).body(responseBody);
        } catch (Exception e) {
            log.error("An unexpected error occurred during QR generation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during QR generation");
        }
    }

    private ResponseEntity<Object> signInvoice(InvoiceRequest invoiceRequest) {
        try {
            invoiceSignService.generateSignedInvoice(invoiceRequest);
            return ResponseEntity.ok("Invoice signed successfully!");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();
            if (statusCode.is4xxClientError()) {
                log.error("Client error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            } else if (statusCode.is5xxServerError()) {
                log.error("Server error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            } else {
                log.error("HTTP error occurred. Status Code: {}, Response: {}", statusCode, responseBody);
            }
            return ResponseEntity.status(statusCode).body(responseBody);
        } catch (Exception e) {
            log.error("An unexpected error occurred during invoice signing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during invoice signing");
        }
    }
}