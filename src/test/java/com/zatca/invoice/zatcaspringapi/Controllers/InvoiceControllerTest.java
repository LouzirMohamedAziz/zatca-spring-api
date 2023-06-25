package com.zatca.invoice.zatcaspringapi.Controllers;

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

    @BeforeEach
    public void setup() {
        invoiceSignService = mock(InvoiceSignService.class);
        qrService = mock(QrService.class);
        validationService = mock(ValidationService.class);
        invoiceController = new InvoiceController(invoiceSignService, qrService, validationService);
    }

    @Test
    public void testHandleInvoiceRequest_WithValidInvoiceRequest_ShouldReturnSignInvoiceResponse() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("invoicePath", "/path/to/invoice");
        request.put("invoiceName", "invoice.pdf");
        request.put("signedInvoiceName", "signed_invoice.pdf");
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath("/path/to/invoice");
        invoiceRequest.setInvoiceName("invoice.pdf");
        invoiceRequest.setSignedInvoiceName("signed_invoice.pdf");
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok("Invoice signed successfully!");

        // Act
        ResponseEntity<Object> response = invoiceController.handleInvoiceRequest(request);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testHandleInvoiceRequest_WithInvalidInvoiceRequest_ShouldReturnBadRequest() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("invoicePath", "/path/to/invoice");
        request.put("invoiceName", "invoice.pdf");
        // Missing "signedInvoiceName" in the request
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath("/path/to/invoice");
        invoiceRequest.setInvoiceName("invoice.pdf");
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid invoice request!");

        // Act
        ResponseEntity<Object> response = invoiceController.handleInvoiceRequest(request);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testHandleInvoiceRequest_WithValidInvoiceRequestButSignInvoiceServiceThrowsException_ShouldReturnInternalServerError() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("invoicePath", "/path/to/invoice");
        request.put("invoiceName", "invoice.pdf");
        request.put("signedInvoiceName", "signed_invoice.pdf");
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath("/path/to/invoice");
        invoiceRequest.setInvoiceName("invoice.pdf");
        invoiceRequest.setSignedInvoiceName("signed_invoice.pdf");
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error signing invoice!");

        when(invoiceSignService.signInvoice(invoiceRequest)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error signing invoice!"));

        // Act
        ResponseEntity<Object> response = invoiceController.handleInvoiceRequest(request);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetQrCodeData_WithValidQrId_ShouldReturnQrCodeData() {
        // Arrange
        String qrId = "123456";
        Qr expectedQrCodeData = new Qr();
        expectedQrCodeData.setId(qrId);
        expectedQrCodeData.setData("QrCodeData123");

        when(qrService.getQrCodeData(qrId)).thenReturn(expectedQrCodeData);

        // Act
        Qr qrCodeData = invoiceController.getQrCodeData(qrId);

        // Assert
        assertEquals(expectedQrCodeData, qrCodeData);
    }

    @Test
    public void testGetQrCodeData_WithInvalidQrId_ShouldThrowHttpClientErrorException() {
        // Arrange
        String qrId = "123456";

        when(qrService.getQrCodeData(qrId)).thenReturn(null);

        // Act and Assert
        try {
            invoiceController.getQrCodeData(qrId);
        } catch (HttpClientErrorException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
            assertEquals("QR code not found!", ex.getMessage());
        }
    }

    @Test
    public void testValidateInvoice_WithValidInvoiceData_ShouldReturnValidationResult() {
        // Arrange
        String invoiceData = "InvoiceData123";
        Validation expectedValidationResult = new Validation();
        expectedValidationResult.setValid(true);

        Result validationResponse = new Result();
        validationResponse.setIsValid(true);
        validationResponse.setMessage("Validation successful!");

        when(validationService.validateInvoice(invoiceData)).thenReturn(validationResponse);

        // Act
        Validation validationResult = invoiceController.validateInvoice(invoiceData);

        // Assert
        assertEquals(expectedValidationResult, validationResult);
    }

    @Test
    public void testValidateInvoice_WithInvalidInvoiceData_ShouldReturnValidationResult() {
        // Arrange
        String invoiceData = "InvalidInvoiceData123";
        Validation expectedValidationResult = new Validation();
        expectedValidationResult.setValid(false);

        Result validationResponse = new Result();
        validationResponse.setIsValid(false);
        validationResponse.setMessage("Validation failed!");

        when(validationService.validateInvoice(invoiceData)).thenReturn(validationResponse);

        // Act
        Validation validationResult = invoiceController.validateInvoice(invoiceData);

        // Assert
        assertEquals(expectedValidationResult, validationResult);
    }
}
