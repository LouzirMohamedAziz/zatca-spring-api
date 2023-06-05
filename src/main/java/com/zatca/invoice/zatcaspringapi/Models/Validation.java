package com.zatca.invoice.zatcaspringapi.Models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Validation {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int validationId;
    private String signedInvoiceFile;


    public Validation() {
    }

    public Validation(int validationId, String signedInvoiceFile) {
        this.validationId = validationId;
        this.signedInvoiceFile = signedInvoiceFile;
    }

    public int getValidationId() {
        return this.validationId;
    }

    public void setValidationId(int validationId) {
        this.validationId = validationId;
    }

    public String getSignedInvoiceFile() {
        return this.signedInvoiceFile;
    }

    public void setSignedInvoiceFile(String signedInvoiceFile) {
        this.signedInvoiceFile = signedInvoiceFile;
    }

    public Validation validationId(int validationId) {
        setValidationId(validationId);
        return this;
    }

    public Validation signedInvoiceFile(String signedInvoiceFile) {
        setSignedInvoiceFile(signedInvoiceFile);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Validation)) {
            return false;
        }
        Validation validation = (Validation) o;
        return validationId == validation.validationId && Objects.equals(signedInvoiceFile, validation.signedInvoiceFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validationId, signedInvoiceFile);
    }

    @Override
    public String toString() {
        return "{" +
            " validationId='" + getValidationId() + "'" +
            ", signedInvoiceFile='" + getSignedInvoiceFile() + "'" +
            "}";
    }
    


}
