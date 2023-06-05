package com.zatca.invoice.zatcaspringapi.Models;
import java.util.Objects;

public class ComplianceResponse {

    ValidationResults validationResults;
    WarningMessages warningMessages;
    ErrorMessages errorMessages;
    String reportingStatus;
    String clearanceStatus;
    String qrSellertStatus;
    String qrBuyertStatus;


    public ComplianceResponse() {
    }

    public ComplianceResponse(ValidationResults validationResults, String reportingStatus, String clearanceStatus, String qrSellertStatus, String qrBuyertStatus) {
        this.validationResults = validationResults;
        this.reportingStatus = reportingStatus;
        this.clearanceStatus = clearanceStatus;
        this.qrSellertStatus = qrSellertStatus;
        this.qrBuyertStatus = qrBuyertStatus;
    }

    public ValidationResults getValidationResults() {
        return this.validationResults;
    }

    public void setValidationResults(ValidationResults validationResults) {
        this.validationResults = validationResults;
    }

    public String getReportingStatus() {
        return this.reportingStatus;
    }

    public void setReportingStatus(String reportingStatus) {
        this.reportingStatus = reportingStatus;
    }

    public String getClearanceStatus() {
        return this.clearanceStatus;
    }

    public void setClearanceStatus(String clearanceStatus) {
        this.clearanceStatus = clearanceStatus;
    }

    public String getQrSellertStatus() {
        return this.qrSellertStatus;
    }

    public void setQrSellertStatus(String qrSellertStatus) {
        this.qrSellertStatus = qrSellertStatus;
    }

    public String getQrBuyertStatus() {
        return this.qrBuyertStatus;
    }

    public void setQrBuyertStatus(String qrBuyertStatus) {
        this.qrBuyertStatus = qrBuyertStatus;
    }

    public ComplianceResponse validationResults(ValidationResults validationResults) {
        setValidationResults(validationResults);
        return this;
    }

    public ComplianceResponse reportingStatus(String reportingStatus) {
        setReportingStatus(reportingStatus);
        return this;
    }

    public ComplianceResponse clearanceStatus(String clearanceStatus) {
        setClearanceStatus(clearanceStatus);
        return this;
    }

    public ComplianceResponse qrSellertStatus(String qrSellertStatus) {
        setQrSellertStatus(qrSellertStatus);
        return this;
    }

    public ComplianceResponse qrBuyertStatus(String qrBuyertStatus) {
        setQrBuyertStatus(qrBuyertStatus);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ComplianceResponse)) {
            return false;
        }
        ComplianceResponse complianceResponse = (ComplianceResponse) o;
        return Objects.equals(validationResults, complianceResponse.validationResults) && Objects.equals(reportingStatus, complianceResponse.reportingStatus) && Objects.equals(clearanceStatus, complianceResponse.clearanceStatus) && Objects.equals(qrSellertStatus, complianceResponse.qrSellertStatus) && Objects.equals(qrBuyertStatus, complianceResponse.qrBuyertStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validationResults, reportingStatus, clearanceStatus, qrSellertStatus, qrBuyertStatus);
    }

    @Override
    public String toString() {
        return "{" +
            " validationResults='" + getValidationResults() + "'" +
            ", reportingStatus='" + getReportingStatus() + "'" +
            ", clearanceStatus='" + getClearanceStatus() + "'" +
            ", qrSellertStatus='" + getQrSellertStatus() + "'" +
            ", qrBuyertStatus='" + getQrBuyertStatus() + "'" +
            "}";
    }
    
}
