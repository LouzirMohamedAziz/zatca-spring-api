package com.zatca.invoice.zatcaspringapi.Models;
import java.util.Objects;

public class CSRRequest {

    String csrConfigFilePath;


    public CSRRequest() {
    }

    public CSRRequest(String csrConfigFilePath) {
        this.csrConfigFilePath = csrConfigFilePath;
    }

    public String getCsrConfigFilePath() {
        return this.csrConfigFilePath;
    }

    public void setCsrConfigFilePath(String csrConfigFilePath) {
        this.csrConfigFilePath = csrConfigFilePath;
    }

    public CSRRequest csrConfigFilePath(String csrConfigFilePath) {
        setCsrConfigFilePath(csrConfigFilePath);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CSRRequest)) {
            return false;
        }
        CSRRequest cSRRequest = (CSRRequest) o;
        return Objects.equals(csrConfigFilePath, cSRRequest.csrConfigFilePath);
    }

    @Override
    public String toString() {
        return "{" +
            " csrConfigFilePath='" + getCsrConfigFilePath() + "'" +
            "}";
    }
    
}
