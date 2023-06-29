// package com.zatca.invoice.zatcaspringapi.Controllers;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.HashMap;
// import java.util.Map;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.client.HttpClientErrorException;
// import org.springframework.web.client.HttpServerErrorException;

// import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;
// import com.zatca.invoice.zatcaspringapi.Models.Qr;
// import com.zatca.invoice.zatcaspringapi.Models.Validation;
// import com.zatca.invoice.zatcaspringapi.Services.InvoiceSignService;
// import com.zatca.invoice.zatcaspringapi.Services.QrService;
// import com.zatca.invoice.zatcaspringapi.Services.ValidationService;
// import com.zatca.sdk.service.validation.Result;


// public class InvoiceControllerTests {

//     private InvoiceSignService invoiceSignService;
//     private QrService qrService;
//     private ValidationService validationService;
//     private InvoiceController invoiceController;

//     @BeforeEach
//     public void setup() {
//         invoiceSignService = mock(InvoiceSignService.class);
//         qrService = mock(QrService.class);
//         validationService = mock(ValidationService.class);
//         invoiceController = new InvoiceController(invoiceSignService, qrService, validationService);
//     }

//     @Test
//     public void testHandleInvoiceRequest_WithValidInvoiceRequest_ShouldReturnSignInvoiceResponse() {
//         // Arrange
//         Map<String, Object> request = new HashMap<>();
//         String baseDir = System.getProperty("user.dir");
//         String invoiceFilePath = baseDir + "/lib/zatca-einvoicing-sdk-234-R3.2.0/signedInvoice.xml";
//         request.put("invoicePath", "/lib/zatca-einvoicing-sdk-234-R3.2.0/");
//         request.put("invoiceName", "invoice.xml");
//         request.put("signedInvoiceName", "signedInvoice.xml");
//         InvoiceRequest invoiceRequest = new InvoiceRequest();
//         invoiceRequest.setInvoicePath("/lib/zatca-einvoicing-sdk-234-R3.2.0/");
//         invoiceRequest.setInvoiceName("invoice.xml");
//         invoiceRequest.setSignedInvoiceName("signedInvoice.xml");
//         ResponseEntity<Object> expectedResponse = ResponseEntity.ok("Invoice signed successfully!");

//         // Act
//         ResponseEntity<Object> response = invoiceController.handleInvoiceRequest(request);

//         // Assert
//         assertEquals(expectedResponse, response);
//     }

//     @Test
//     public void testHandleInvoiceRequest_WithInvalidInvoiceRequest_ShouldReturnBadRequest() {
//         // Arrange
//         Map<String, Object> request = new HashMap<>();
//         request.put("invoicePath", "/path/to/invoice");
//         request.put("invoiceName", "invoice.pdf");
//         // Missing "signedInvoiceName" in the request
//         InvoiceRequest invoiceRequest = new InvoiceRequest();
//         invoiceRequest.setInvoicePath("/path/to/invoice");
//         invoiceRequest.setInvoiceName("invoice.pdf");
//         ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .body("Invalid invoice request!");

//         // Act
//         ResponseEntity<Object> response = invoiceController.handleInvoiceRequest(request);

//         // Assert
//         assertEquals(expectedResponse, response);
//     }

//     @Test
//     public void testHandleInvoiceRequest_WithValidInvoiceRequestButSignInvoiceServiceThrowsException_ShouldReturnInternalServerError() {
//         // Arrange
//         Map<String, Object> request = new HashMap<>();
//         request.put("invoicePath", "/path/to/invoice");
//         request.put("invoiceName", "invoice.pdf");
//         request.put("signedInvoiceName", "signed_invoice.pdf");
//         InvoiceRequest invoiceRequest = new InvoiceRequest();
//         invoiceRequest.setInvoicePath("/path/to/invoice");
//         invoiceRequest.setInvoiceName("invoice.pdf");
//         invoiceRequest.setSignedInvoiceName("signed_invoice.pdf");
//         ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body("Error signing invoice!");

//         when(invoiceSignService.generateSignedInvoice(invoiceRequest)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error signing invoice!"));

//         // Act
//         ResponseEntity<Object> response = invoiceController.handleInvoiceRequest(request);

//         // Assert
//         assertEquals(expectedResponse, response);
//     }


//     @Test
//     public void testGetQrCodeData_WithInvalidPathtoSignedInvoice_ShouldThrowHttpClientErrorException() {
//         // Arrange
//         Qr qr = new Qr();
//         when(qrService.generateQRCODE(qr)).thenReturn(null);

//         // Act and Assert
//         try {
//             qr.getSignedInvoiceFilePath();
//         } catch (HttpClientErrorException ex) {
//             assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
//             assertEquals("QR code not found!", ex.getMessage());
//         }
//     }

//     @Test
//     public void testValidateInvoice_WithValidInvoiceData_ShouldReturnValidationResult() {
//         // Arrange
//         String invoiceData = "InvoiceData123";
//         Validation expectedValidationResult = new Validation();
//         expectedValidationResult.setValid(true);

//         Result validationResponse = new Result();
//         validationResponse.setIsValid(true);
//         validationResponse.setMessage("Validation successful!");

//         when(validationService.validateInvoice(invoiceData)).thenReturn(validationResponse);

//         // Act
//         Validation validationResult = invoiceController.validateInvoice(invoiceData);

//         // Assert
//         assertEquals(expectedValidationResult, validationResult);
//     }

//     @Test
//     public void testValidateInvoice_WithInvalidInvoiceData_ShouldReturnValidationResult() {
//         // Arrange
//         String invoiceData = "InvalidInvoiceData123";
//         Validation expectedValidationResult = new Validation();
//         expectedValidationResult.setValid(false);

//         Result validationResponse = new Result();
//         validationResponse.setIsValid(false);
//         validationResponse.setMessage("Validation failed!");

//         when(validationService.validateInvoice(invoiceData)).thenReturn(validationResponse);

//         // Act
//         Validation validationResult = invoiceController.validateInvoice(invoiceData);

//         // Assert
//         assertEquals(expectedValidationResult, validationResult);
//     }
// }
