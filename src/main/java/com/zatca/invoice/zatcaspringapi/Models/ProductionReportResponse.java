package com.zatca.invoice.zatcaspringapi.Models;
import java.util.Objects;

public class ProductionReportResponse {

    ValidationResults validationResults;
    String reportingStatus;


    public ProductionReportResponse() {
    }

    public ProductionReportResponse(ValidationResults validationResults, String reportingStatus) {
        this.validationResults = validationResults;
        this.reportingStatus = reportingStatus;
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

    public ProductionReportResponse validationResults(ValidationResults validationResults) {
        setValidationResults(validationResults);
        return this;
    }

    public ProductionReportResponse reportingStatus(String reportingStatus) {
        setReportingStatus(reportingStatus);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductionReportResponse)) {
            return false;
        }
        ProductionReportResponse productionReportResponse = (ProductionReportResponse) o;
        return Objects.equals(validationResults, productionReportResponse.validationResults) && Objects.equals(reportingStatus, productionReportResponse.reportingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validationResults, reportingStatus);
    }

    @Override
    public String toString() {
        return "{" +
            " validationResults='" + getValidationResults() + "'" +
            ", reportingStatus='" + getReportingStatus() + "'" +
            "}";
    }
    
}
