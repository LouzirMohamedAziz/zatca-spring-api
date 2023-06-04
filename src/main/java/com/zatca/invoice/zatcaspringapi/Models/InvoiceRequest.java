package com.zatca.invoice.zatcaspringapi.Models;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@EntityScan
public class InvoiceRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int invoiceSignId;
    private String invoicePath;
    private String invoiceName;
    private String signedInvoiceName;


    public InvoiceRequest() {
    }

    public InvoiceRequest(int invoiceSignId, String invoicePath, String invoiceName, String signedInvoiceName) {
        this.invoiceSignId = invoiceSignId;
        this.invoicePath = invoicePath;
        this.invoiceName = invoiceName;
        this.signedInvoiceName = signedInvoiceName;
    }

    public int getInvoiceSignId() {
        return this.invoiceSignId;
    }

    public void setInvoiceSignId(int invoiceSignId) {
        this.invoiceSignId = invoiceSignId;
    }

    public String getInvoicePath() {
        return this.invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }

    public String getInvoiceName() {
        return this.invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getSignedInvoiceName() {
        return this.signedInvoiceName;
    }

    public void setSignedInvoiceName(String signedInvoiceName) {
        this.signedInvoiceName = signedInvoiceName;
    }

    public InvoiceRequest invoiceSignId(int invoiceSignId) {
        setInvoiceSignId(invoiceSignId);
        return this;
    }

    public InvoiceRequest invoicePath(String invoicePath) {
        setInvoicePath(invoicePath);
        return this;
    }

    public InvoiceRequest invoiceName(String invoiceName) {
        setInvoiceName(invoiceName);
        return this;
    }

    public InvoiceRequest signedInvoiceName(String signedInvoiceName) {
        setSignedInvoiceName(signedInvoiceName);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof InvoiceRequest)) {
            return false;
        }
        InvoiceRequest invoiceRequest = (InvoiceRequest) o;
        return invoiceSignId == invoiceRequest.invoiceSignId && Objects.equals(invoicePath, invoiceRequest.invoicePath) && Objects.equals(invoiceName, invoiceRequest.invoiceName) && Objects.equals(signedInvoiceName, invoiceRequest.signedInvoiceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceSignId, invoicePath, invoiceName, signedInvoiceName);
    }

    @Override
    public String toString() {
        return "{" +
            " invoiceSignId='" + getInvoiceSignId() + "'" +
            ", invoicePath='" + getInvoicePath() + "'" +
            ", invoiceName='" + getInvoiceName() + "'" +
            ", signedInvoiceName='" + getSignedInvoiceName() + "'" +
            "}";
    }

    
}
