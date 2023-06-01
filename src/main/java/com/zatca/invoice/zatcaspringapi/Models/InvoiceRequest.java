package com.zatca.invoice.zatcaspringapi.Models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvoiceRequest {

    @JsonProperty("otp")
    String otp;
    @JsonProperty("encodedInvoice")
    String encodedInvoice;


    public InvoiceRequest() {
    }

    public InvoiceRequest(String otp, String encodedInvoice) {
        this.otp = otp;
        this.encodedInvoice = encodedInvoice;
    }

    public String getOtp() {
        return this.otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEncodedInvoice() {
        return this.encodedInvoice;
    }

    public void setEncodedInvoice(String encodedInvoice) {
        this.encodedInvoice = encodedInvoice;
    }

    public InvoiceRequest otp(String otp) {
        setOtp(otp);
        return this;
    }

    public InvoiceRequest encodedInvoice(String encodedInvoice) {
        setEncodedInvoice(encodedInvoice);
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
        return Objects.equals(otp, invoiceRequest.otp) && Objects.equals(encodedInvoice, invoiceRequest.encodedInvoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(otp, encodedInvoice);
    }

    @Override
    public String toString() {
        return "{" +
            " otp='" + getOtp() + "'" +
            ", encodedInvoice='" + getEncodedInvoice() + "'" +
            "}";
    }
    
}
