package com.zatca.invoice.zatcaspringapi.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Entity
public class Qr {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int qrGenerationId;
    private String signedInvoiceFilePath;


    public Qr() {
    }

    public Qr(int qrGenerationId, String signedInvoiceFilePath) {
        this.qrGenerationId = qrGenerationId;
        this.signedInvoiceFilePath = signedInvoiceFilePath;
    }

    public int getQrGenerationId() {
        return this.qrGenerationId;
    }

    public void setQrGenerationId(int qrGenerationId) {
        this.qrGenerationId = qrGenerationId;
    }

    public String getSignedInvoiceFilePath() {
        return this.signedInvoiceFilePath;
    }

    public void setSignedInvoiceFilePath(String signedInvoiceFilePath) {
        this.signedInvoiceFilePath = signedInvoiceFilePath;
    }

    public Qr qrGenerationId(int qrGenerationId) {
        setQrGenerationId(qrGenerationId);
        return this;
    }

    public Qr signedInvoiceFilePath(String signedInvoiceFilePath) {
        setSignedInvoiceFilePath(signedInvoiceFilePath);
        return this;
    }
}