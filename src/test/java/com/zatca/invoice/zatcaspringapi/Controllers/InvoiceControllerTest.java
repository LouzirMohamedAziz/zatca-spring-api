package com.zatca.invoice.zatcaspringapi.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;

@SpringBootTest
@AutoConfigureMockMvc
@SpringJUnitConfig
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    String baseDir = System.getProperty("user.dir");
    private final String testInvoicePath = "/lib/zatca-einvoicing-sdk-234-R3.2.0";

    public static String modifyBaseDir(String baseDir) {
        if (baseDir.contains("lib")) {
            return "";
        } else {
            return baseDir;
        }
    }

    @Test
    public void testInvoiceSigning() throws Exception {
        // Create a sample InvoiceRequest object for testing
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath(baseDir + testInvoicePath);
        invoiceRequest.setInvoiceName("invoice.xml");
        invoiceRequest.setSignedInvoiceName("signedInvoice.xml");
    
        // Perform the POST request to sign the invoice
        mockMvc.perform(MockMvcRequestBuilders.post("/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(invoiceRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

                
        // Check if the "signedInvoice.xml" file is generated
        String signedInvoicePath = baseDir + testInvoicePath + File.separator + "signedInvoice.xml";
        assertTrue(new File(signedInvoicePath).exists(), "The signedInvoice.xml file was not generated.");
    
    }
    

    @Test
    public void testGeneratedQRCode() throws Exception {
        // Create a sample InvoiceRequest object for testing
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath(baseDir + testInvoicePath);
        invoiceRequest.setInvoiceName("invoice.xml");
        invoiceRequest.setSignedInvoiceName("signedInvoice.xml");
    
        // Perform the POST request to sign the invoice
        mockMvc.perform(MockMvcRequestBuilders.post("/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(invoiceRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    String signedInvoicePath = baseDir + testInvoicePath + File.separator + "signedInvoice.xml";
    byte[] signedInvoiceContent = Files.readAllBytes(Paths.get(signedInvoicePath));
    // Parse the XML content
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(new ByteArrayInputStream(signedInvoiceContent));

    // Get the specified tag <cbc:ID>QR</cbc:ID> from the XML
    NodeList nodeList = document.getElementsByTagName("cbc:ID");
    assertTrue(nodeList.getLength() > 0, "The signedInvoice.xml file does not contain the <cbc:ID> tag.");
    
    // Get the first node (assuming there's only one in the XML)
    Element element = (Element) nodeList.item(0);
    String tagValue = element.getTextContent();
    
    // Ensure that the tag contains a non-empty value
    assertFalse(tagValue.isEmpty(), "The <cbc:ID> tag is empty in the signedInvoice.xml.");
}

    @Test
    public void testInvoiceValidation() throws Exception {
        // Create a sample InvoiceRequest object for testing
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoicePath(baseDir + testInvoicePath);
        invoiceRequest.setInvoiceName("invoice.xml");
        invoiceRequest.setSignedInvoiceName("signedInvoice.xml");

        // Perform the POST request to sign the invoice
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(invoiceRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Parse the response content to check validation output
        String responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody, "Response body is null.");

        // Check if the JSON response contains the specified strings
        assertTrue(responseBody.contains("\"valid\""));
        assertTrue(responseBody.contains("\"error\""));
        assertTrue(responseBody.contains("\"warning\""));
        assertTrue(responseBody.contains("\"category\""));
        assertTrue(responseBody.contains("\"validQrCode\""));
        assertTrue(responseBody.contains("\"validSignature\""));
    }

    // Helper method to convert an object to JSON bytes
    private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
}
