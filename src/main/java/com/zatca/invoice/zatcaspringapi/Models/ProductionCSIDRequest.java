package com.zatca.invoice.zatcaspringapi.Models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ProductionCSIDRequest {

    @JsonProperty("compliance_request_id")
    String complianceRequestId;

    public ProductionCSIDRequest() {
    }

    public ProductionCSIDRequest(String complianceRequestId) {
        this.complianceRequestId = complianceRequestId;
    }

    public String getComplianceRequestId() {
        return this.complianceRequestId;
    }

    public void setComplianceRequestId(String complianceRequestId) {
        this.complianceRequestId = complianceRequestId;
    }

    public ProductionCSIDRequest complianceRequestId(String complianceRequestId) {
        setComplianceRequestId(complianceRequestId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductionCSIDRequest)) {
            return false;
        }
        ProductionCSIDRequest productionCSIDRequest = (ProductionCSIDRequest) o;
        return Objects.equals(complianceRequestId, productionCSIDRequest.complianceRequestId);
    }


    @Override
    public String toString() {
        return "{" +
            " complianceRequestId='" + getComplianceRequestId() + "'" +
            "}";
    }

    
}
