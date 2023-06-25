package com.zatca.invoice.zatcaspringapi.Models;

public enum EndpointBalancer {
    SIGN,
    QR,
    VALIDATION;

    public String getValue() {
        return name().toLowerCase();
    }
}
