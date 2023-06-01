package com.zatca.invoice.zatcaspringapi.Services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.zatca.config.ResourcesPaths;
import com.zatca.invoice.zatcaspringapi.Models.CSRRequest;
import com.zatca.invoice.zatcaspringapi.Models.CSRResponse;
import com.zatca.invoice.zatcaspringapi.Models.ComplianceResponse;
import com.zatca.invoice.zatcaspringapi.Models.ProductionCSIDRequest;
import com.zatca.invoice.zatcaspringapi.Models.ProductionReportResponse;
import com.zatca.sdk.dto.ApplicationPropertyDto;
import com.zatca.sdk.service.CsrGenerationService;
import com.zatca.sdk.service.GeneratorTemplate;
import com.zatca.sdk.service.InvoiceRequestGenerationService;
import com.zatca.sdk.service.InvoiceSigningService;

@Component
public class CSRequestService {

    public CSRResponse retrieveComplianceCSID(String otp) throws Exception {

        var complianceURL = "https://gw-apic-gov.gazt.gov.sa/e-invoicing/developer-portal/compliance";
        Path filePath = Path.of("C:\\development\\zatca\\certs\\taxpayer0.csr");
        String content = Files.readString(filePath);
        CSRRequest csrRequest = new CSRRequest();
        csrRequest.setCsr(content);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("OTP", otp);
        httpHeaders.add("Accept-Version", "V2");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CSRRequest> request = new HttpEntity<>(csrRequest, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CSRResponse> response = restTemplate
                .exchange(complianceURL, HttpMethod.POST, request, CSRResponse.class);
        return response.getBody();

    }

    public CSRResponse retrieveProductionCSID(String otp, String complianceRequestId, String userName, String password) throws Exception {

        var prodCSIDURL = "https://gw-apic-gov.gazt.gov.sa/e-invoicing/developer-portal/production/csids";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("OTP", otp);
        httpHeaders.add("Accept-Version", "V2");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(userName, password);
        ProductionCSIDRequest productionCSIDRequest = new ProductionCSIDRequest();
        productionCSIDRequest.setComplianceRequestId(complianceRequestId);
        HttpEntity<ProductionCSIDRequest> request = new HttpEntity<>(productionCSIDRequest, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CSRResponse> response = restTemplate
                .exchange(prodCSIDURL, HttpMethod.POST, request, CSRResponse.class);
        return response.getBody();

    }

    public ComplianceResponse reportComplianceInvoice(String invoiceRequest, String userName, String password) throws Exception {

        var complianceURL = "https://gw-apic-gov.gazt.gov.sa/e-invoicing/developer-portal/compliance/invoices";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept-Language", "en");
        httpHeaders.add("Accept-Version", "V2");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(userName, password);
        HttpEntity<String> request = new HttpEntity<>(invoiceRequest, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ComplianceResponse> response = restTemplate
                .exchange(complianceURL, HttpMethod.POST, request, ComplianceResponse.class);
        return response.getBody();

    }

    public ProductionReportResponse reportProductionInvoice(String invoiceRequest, String userName, String password) throws Exception {
        var prodReportURL = "https://gw-apic-gov.gazt.gov.sa/e-invoicing/developer-portal/invoices/reporting/single";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept-Language", "en");
        httpHeaders.add("Accept-Version", "V2");
        httpHeaders.add("Clearance-Status", "0");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(userName, password);
        HttpEntity<String> request = new HttpEntity<>(invoiceRequest, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductionReportResponse> response = restTemplate
                .exchange(prodReportURL, HttpMethod.POST, request, ProductionReportResponse.class);
        return response.getBody();

    }

    public String generateInvoiceRequest(String invoicePath, String invoiceName) {

        var invoiceRequest = "";
        var invoice = invoicePath.concat(String.valueOf(File.separatorChar)).concat(invoiceName);
        try {
            var xmlFile = FileUtils.readFileToString(new File(invoice), StandardCharsets.UTF_8);
            InvoiceRequestGenerationService invoiceRequestGenerationService = new InvoiceRequestGenerationService();
            Properties invoiceProps = invoiceRequestGenerationService.getInvoiceData(xmlFile);
            invoiceRequest = invoiceRequestGenerationService.prepareRequestBodyString(invoiceProps);
        } catch (Exception exception) {
            System.out.println("unable to generate invoice hash" + exception);
        }
        return invoiceRequest;

    }

    public void generateCSR(String privateKeyFile) {

        GeneratorTemplate generateService = new CsrGenerationService();
        ApplicationPropertyDto configuration = new ApplicationPropertyDto();
        configuration.setGenerateCsr(true);
        configuration.setCsrConfigFileName("/Users/louzir/Documents/Spring/zatca-sdk/zatca-einvoicing-sdk-234-R3.2.0/csr-config-template.properties");
        configuration.setCsrFileName("Users/louzir/Documents/Spring/zatca-sdk/zatca-einvoicing-sdk-234-R3.2.0/taxpayer-template.csr");
        configuration.setPrivateKeyFileName(privateKeyFile);
        generateService.generate(configuration);
    }

    public void generateSignedInvoice(String invoicePath, String invoiceName, String signedInvoiceName) {

        GeneratorTemplate generateService = new InvoiceSigningService();
        ApplicationPropertyDto configuration = new ApplicationPropertyDto();
        configuration.setGenerateSignature(true);
        configuration.setInvoiceFileName(invoicePath.concat(String.valueOf(File.separatorChar)).concat(invoiceName));
        configuration.setOutputInvoiceFileName(invoicePath.concat(String.valueOf(File.separatorChar)).concat(signedInvoiceName));
        generateService.generate(configuration);
    }

    public ConcurrentMap<String, String> process(String otp, String invoicePath, String invoiceName) throws Exception {

        ConcurrentMap<String, String> statusMap = initializeStatusMap();
        ResourcesPaths paths = ResourcesPaths.getInstance();
        var privateKey = paths.getPrivateKeyPath();
        System.out.println("Generate CSR Request..");
        generateCSR(privateKey);
        statusMap.put("Generate CSR", "PASSED");
        System.out.println("CSR Request Generated..");
        System.out.println("Retrieving Compliance CSID..");
        CSRResponse csrResponse = retrieveComplianceCSID(otp);
        statusMap.put("Retrieving Compliance CSID", "PASSED");
        System.out.println("Compliance CSID Retrieved Successfully..");
        var securityToken = csrResponse.getBinarySecurityToken();
        var securityPassword = csrResponse.getSecret();
        var csrRequestId = csrResponse.getRequestID();
        byte[] securityTokenBytes = securityToken.getBytes("UTF-8");
        var decodedCertificate = Base64.getDecoder().decode(securityTokenBytes);
        FileUtils.copyInputStreamToFile(new ByteArrayInputStream(decodedCertificate),
                new File(paths.getCertificatePath()));
        //var invoicePath = "C:\\development\\zatca\\zatca-einvoicing-sdk-231-R3.1.7\\Apps";
        //var invoiceName = "invoice-standard-0";
        // var invoiceName = "xmlsample5";
        var invoiceXmlName = String.format("%s.%s", invoiceName, "xml");
        var signedInvoiceName = String.format("%s-%s-%s.%s", invoiceName, "signed",
                (new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date()), "xml");
        System.out.println("Signing Invoice..");
        generateSignedInvoice(invoicePath, invoiceXmlName, signedInvoiceName);
        statusMap.put("Sign Invoice", "PASSED");
        System.out.println("Invoice Signed Successfully..");
        var invoiceRequest = generateInvoiceRequest(invoicePath, signedInvoiceName);
        System.out.println("Invoice Request:{}" + invoiceRequest);
        System.out.println("Requesting Compliance Report for Invoice... ");
        ComplianceResponse complianceResponse = reportComplianceInvoice(invoiceRequest, securityToken, securityPassword);
        System.out.println("Compliance Report Clearance Status: {}"+ complianceResponse.getClearanceStatus());
        statusMap.put("Sign Invoice", complianceResponse.getClearanceStatus());
        System.out.println("Retrieving Production CSID..");
        CSRResponse prodCSRResponse = retrieveProductionCSID(otp, csrRequestId, securityToken, securityPassword);
        System.out.println("Production CSID Retrieved..");
        statusMap.put("Retrieving Production CSID", "PASSED");
        System.out.println("Requesting Invoice Report... ");
        ProductionReportResponse reportResponse = reportProductionInvoice(invoiceRequest, prodCSRResponse.getBinarySecurityToken(), prodCSRResponse.getSecret());
        System.out.println("Invoice Reporting Status:{}"+ reportResponse.getReportingStatus());
        statusMap.put("Invoice Reporting Status", reportResponse.getReportingStatus());
        return statusMap;

    }
    
    private ConcurrentMap<String, String>  initializeStatusMap() {

        ConcurrentMap<String, String> statusMap = new ConcurrentHashMap<>();
        statusMap.put("Generate CSR", "PENDING");
        statusMap.put("Retrieving Compliance CSID", "PENDING");
        statusMap.put("Sign Invoice", "PENDING");
        statusMap.put("Retrieving Production CSID", "PENDING");
        statusMap.put("Invoice Reporting Status", "PENDING");
        return statusMap;

    }
}
