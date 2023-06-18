// package com.zatca.invoice.zatcaspringapi.Controllers;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.zatca.invoice.zatcaspringapi.Models.RequestData;

// @RestController
// @RequestMapping("/invoice")
// public class FacadeController {

//     private final SignInvoiceController signController;
//     private final QrGenerationController generateController;
//     private final InvoiceValidationController validateController;

//     public FacadeController(SignInvoiceController signController, QrGenerationController generateController, InvoiceValidationController validateController) {
//         this.signController = signController;
//         this.generateController = generateController;
//         this.validateController = validateController;
//     }
// @PostMapping()
// public ResponseEntity<String> handleEndpoint(@RequestBody Map<String, Object> requestBody) {
//     String operation = (String) requestBody.get("operation");

//     if (operation != null) {
//         switch (operation) {
//             case "sign":
//                 SignRequest signRequest = createSignRequest(requestBody);
//                 return signController.handleSign(signRequest);
//             case "generate":
//                 GenerateQRRequest generateQRRequest = createGenerateQRRequest(requestBody);
//                 return generateController.handleGenerateQR(generateQRRequest);
//             case "validate":
//                 ValidateInvoiceRequest validateInvoiceRequest = createValidateInvoiceRequest(requestBody);
//                 return validateController.handleValidateInvoice(validateInvoiceRequest);
//             default:
//                 // Handle invalid operation
//                 return ResponseEntity.badRequest().body("Invalid operation");
//         }
//     } else {
//         // Handle missing operation field
//         return ResponseEntity.badRequest().body("Operation field is missing");
//     }
// }

// private SignRequest createSignRequest(Map<String, Object> requestBody) {
//     String invoicePath = (String) requestBody.get("invoicePath");
//     String invoiceName = (String) requestBody.get("invoiceName");
//     String signedInvoiceName = (String) requestBody.get("signedInvoiceName");
    
//     return new SignRequest(invoicePath, invoiceName, signedInvoiceName);
// }

// private GenerateQRRequest createGenerateQRRequest(Map<String, Object> requestBody) {
//     String signedInvoiceFilePath = (String) requestBody.get("signedInvoiceFilePath");
    
//     return new GenerateQRRequest(signedInvoiceFilePath);
// }

// private ValidateInvoiceRequest createValidateInvoiceRequest(Map<String, Object> requestBody) {
//     String signedInvoiceFile = (String) requestBody.get("signedInvoiceFile");
    
//     return new ValidateInvoiceRequest(signedInvoiceFile);
// }

// }
