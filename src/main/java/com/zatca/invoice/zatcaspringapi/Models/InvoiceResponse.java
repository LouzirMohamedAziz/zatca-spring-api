package com.zatca.invoice.zatcaspringapi.Models;
import java.util.Objects;


public class InvoiceResponse {

    String signedInvoice;
    String invoiceHash;
    ComplianceResponse complianceResponse;

    public InvoiceResponse() {
    }

    public InvoiceResponse(String signedInvoice, String invoiceHash, ComplianceResponse complianceResponse) {
        this.signedInvoice = signedInvoice;
        this.invoiceHash = invoiceHash;
        this.complianceResponse = complianceResponse;
    }

    public String getSignedInvoice() {
        return this.signedInvoice;
    }

    public void setSignedInvoice(String signedInvoice) {
        this.signedInvoice = signedInvoice;
    }

    public String getInvoiceHash() {
        return this.invoiceHash;
    }

    public void setInvoiceHash(String invoiceHash) {
        this.invoiceHash = invoiceHash;
    }

    public ComplianceResponse getComplianceResponse() {
        return this.complianceResponse;
    }

    public void setComplianceResponse(ComplianceResponse complianceResponse) {
        this.complianceResponse = complianceResponse;
    }

    public InvoiceResponse signedInvoice(String signedInvoice) {
        setSignedInvoice(signedInvoice);
        return this;
    }

    public InvoiceResponse invoiceHash(String invoiceHash) {
        setInvoiceHash(invoiceHash);
        return this;
    }

    public InvoiceResponse complianceResponse(ComplianceResponse complianceResponse) {
        setComplianceResponse(complianceResponse);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof InvoiceResponse)) {
            return false;
        }
        InvoiceResponse invoiceResponse = (InvoiceResponse) o;
        return Objects.equals(signedInvoice, invoiceResponse.signedInvoice) && Objects.equals(invoiceHash, invoiceResponse.invoiceHash) && Objects.equals(complianceResponse, invoiceResponse.complianceResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signedInvoice, invoiceHash, complianceResponse);
    }

    @Override
    public String toString() {
        return "{" +
            " signedInvoice='" + getSignedInvoice() + "'" +
            ", invoiceHash='" + getInvoiceHash() + "'" +
            ", complianceResponse='" + getComplianceResponse() + "'" +
            "}";
    }

    
}
