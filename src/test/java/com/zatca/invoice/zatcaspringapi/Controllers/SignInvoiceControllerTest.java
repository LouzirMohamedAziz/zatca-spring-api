// package com.zatca.invoice.zatcaspringapi.Controllers;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.*;

// import org.apache.logging.log4j.core.Logger;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.client.HttpClientErrorException;
// import org.springframework.web.client.HttpServerErrorException;

// import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;
// import com.zatca.invoice.zatcaspringapi.Services.InvoiceSignService;

// class SignInvoiceControllerTest {

//     @Mock
//     private InvoiceSignService invoiceSignService;

//     @Mock
//     private Logger mockLogger;

//     @InjectMocks
//     private SignInvoiceController signInvoiceController;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         signInvoiceController.log = mockLogger;
//     }

//     @Test
//     void signInvoice_Success() {
        
//         InvoiceRequest invoiceRequest = new InvoiceRequest();

//         // Mock the behavior of the invoiceSignService
//         // For example, assume the service call is successful and doesn't throw any exceptions
//         doNothing().when(invoiceSignService).generateSignedInvoice(invoiceRequest);

//         // Call the signInvoice method
//         String result = signInvoiceController.signInvoice(invoiceRequest);

//         // Perform assertions to validate the result
//         assertEquals("Invoice signed successfully!", result);
//         verifyNoInteractions(mockLogger);
//     }

//     @Test
//     void signInvoice_HttpClientErrorException() {
//         // Prepare test data
//         InvoiceRequest invoiceRequest = new InvoiceRequest();

//         // Mock the behavior of the invoiceSignService
//         // For example, assume a HttpClientErrorException is thrown with 400 Bad Request status
//         when(invoiceSignService.generateSignedInvoice(invoiceRequest)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

//         // Call the signInvoice method
//         String result = signInvoiceController.signInvoice(invoiceRequest);

//         // Perform assertions to validate the result and the log messages
//         assertEquals("Invoice signed successfully!", result);
//         verify(mockLogger).error("Client error occurred. Status Code: 400 BAD_REQUEST, Response: null");
//     }

//     @Test
//     void signInvoice_HttpServerErrorException() {
//         // Prepare test data
//         InvoiceRequest invoiceRequest = new InvoiceRequest();

//         // Mock the behavior of the invoiceSignService
//         // For example, assume a HttpServerErrorException is thrown with 500 Internal Server Error status
//         when(invoiceSignService.generateSignedInvoice(invoiceRequest)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

//         // Call the signInvoice method
//         String result = signInvoiceController.signInvoice(invoiceRequest);

//         // Perform assertions to validate the result and the log messages
//         assertEquals("Invoice signed successfully!", result);
//         verify(mockLogger).error("Server error occurred. Status Code: 500 INTERNAL_SERVER_ERROR, Response: null");
//     }

//     @Test
//     void signInvoice_UnexpectedException() {
//         // Prepare test data
//         InvoiceRequest invoiceRequest = new InvoiceRequest();

//         // Mock the behavior of the invoiceSignService
//         // For example, assume an unexpected exception is thrown
//         when(invoiceSignService.generateSignedInvoice(invoiceRequest)).thenThrow(new RuntimeException("Unexpected exception"));

//         // Call the signInvoice method
//         String result = signInvoiceController.signInvoice(invoiceRequest);

//         // Perform assertions to validate the result and the log messages
//         assertEquals("Invoice signed successfully!", result);
//         verify(mockLogger).error("An unexpected error occurred during signInvoice", any(RuntimeException.class));
//     }
// }
