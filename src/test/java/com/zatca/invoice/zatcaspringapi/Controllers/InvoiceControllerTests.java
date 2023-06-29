package com.zatca.invoice.zatcaspringapi.Controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;
import com.zatca.invoice.zatcaspringapi.Models.Qr;
import com.zatca.invoice.zatcaspringapi.Models.Validation;
import com.zatca.invoice.zatcaspringapi.Services.InvoiceSignService;
import com.zatca.invoice.zatcaspringapi.Services.QrService;
import com.zatca.invoice.zatcaspringapi.Services.ValidationService;
import com.zatca.sdk.service.validation.Result;


public class InvoiceControllerTests {

    private InvoiceSignService invoiceSignService;
    private QrService qrService;
    private ValidationService validationService;
    private InvoiceController invoiceController;
    String baseDir = System.getProperty("user.dir");

    @BeforeEach
    public void setup() {
        invoiceSignService = mock(InvoiceSignService.class);
        qrService = mock(QrService.class);
        validationService = mock(ValidationService.class);
        invoiceController = new InvoiceController(invoiceSignService, qrService, validationService);
    }

    @Test
    public void testHandleInvoiceRequest_WithValidInvoiceRequest_ShouldReturnSignInvoiceResponse() {
        Map<String, Object> request = new HashMap<>();
        request.put("invoicePath", baseDir+"/lib/zatca-einvoicing-sdk-234-R3.2.0/");
        request.put("invoiceName", "invoice.xml");
        request.put("signedInvoiceName", "signedInvoice.xml");
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath(baseDir+"/lib/zatca-einvoicing-sdk-234-R3.2.0/");
        invoiceRequest.setInvoiceName("invoice.xml");
        invoiceRequest.setSignedInvoiceName("signedInvoice.xml");
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok("Invoice signed successfully!");
        ResponseEntity<Object> response = invoiceController.handleInvoiceRequest(request);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testHandleInvoiceRequest_WithInvalidInvoiceRequest_ShouldReturnBadRequest() {
        Map<String, Object> request = new HashMap<>();
        request.put("invoicePath", baseDir+"/lib/zatca-einvoicing-sdk-234-R3.2.0/");
        request.put("invoiceName", "invoice.xml");
        request.put("signedInvoiceName", "signedInvoice.xml");
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath(baseDir+"/lib/zatca-einvoicing-sdk-234-R3.2.0/");
        invoiceRequest.setInvoiceName("invoice.xml");
        invoiceRequest.setSignedInvoiceName("signedInvoice.xml");
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid invoice request!");
        ResponseEntity<Object> response = invoiceController.handleInvoiceRequest(request);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testHandleInvoiceRequest_WithValidInvoiceRequestButSignInvoiceServiceThrowsException_ShouldReturnInternalServerError() {
        Map<String, Object> request = new HashMap<>();
        request.put("invoicePath", baseDir+"/lib/zatca-einvoicing-sdk-234-R3.2.0/");
        request.put("invoiceName", "invoice.xml");
        request.put("signedInvoiceName", "signedInvoice.xml");
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath(baseDir+"/lib/zatca-einvoicing-sdk-234-R3.2.0/");
        invoiceRequest.setInvoiceName("invoice.xml");
        invoiceRequest.setSignedInvoiceName("signedInvoice.xml");
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error signing invoice!");

        when(invoiceSignService.generateSignedInvoice(invoiceRequest)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error signing invoice!"));
        ResponseEntity<Object> response = invoiceController.handleInvoiceRequest(request);
        assertEquals(expectedResponse, response);
    }


    @Test
    public void testGetQrCodeData_WithInvalidPathtoSignedInvoice_ShouldThrowHttpClientErrorException() {
        Qr qr = new Qr();
        when(qrService.generateQRCODE(qr)).thenReturn(null);
        try {
            qr.getSignedInvoiceFilePath();
        } catch (HttpClientErrorException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
            assertEquals("QR code not found!", ex.getMessage());
        }
    }

    @Test
    public void testSuccessfulValidation() {
        Validation validation = new Validation();
        Result expectedResult = new Result();
        when(validationService.validityCheck(validation)).thenReturn(expectedResult);
        ResponseEntity<Object> response = invoiceController.validateInvoiceImpl(validation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResult, response.getBody());
        verify(validationService, times(1)).validityCheck(validation);
    }

    @Test
    public void testInternalServerError() {
        Validation validation = new Validation();
        when(validationService.validityCheck(validation)).thenReturn(null);
        ResponseEntity<Object> response = invoiceController.validateInvoiceImpl(validation);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(validationService, times(1)).validityCheck(validation);
    }
}

