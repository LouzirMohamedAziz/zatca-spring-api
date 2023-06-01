package com.zatca.invoice.zatcaspringapi.Models;

import java.util.List;
import java.util.Objects;


public class ValidationResults {

    List<InfoMessages> infoMessages;
    List<WarningMessages> warningMessages;
    List<ErrorMessages> errorMessages;
    String status;
    

    public ValidationResults() {
    }

    public ValidationResults(List<InfoMessages> infoMessages, List<WarningMessages> warningMessages, List<ErrorMessages> errorMessages, String status) {
        this.infoMessages = infoMessages;
        this.warningMessages = warningMessages;
        this.errorMessages = errorMessages;
        this.status = status;
    }

    public List<InfoMessages> getInfoMessages() {
        return this.infoMessages;
    }

    public void setInfoMessages(List<InfoMessages> infoMessages) {
        this.infoMessages = infoMessages;
    }

    public List<WarningMessages> getWarningMessages() {
        return this.warningMessages;
    }

    public void setWarningMessages(List<WarningMessages> warningMessages) {
        this.warningMessages = warningMessages;
    }

    public List<ErrorMessages> getErrorMessages() {
        return this.errorMessages;
    }

    public void setErrorMessages(List<ErrorMessages> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ValidationResults infoMessages(List<InfoMessages> infoMessages) {
        setInfoMessages(infoMessages);
        return this;
    }

    public ValidationResults warningMessages(List<WarningMessages> warningMessages) {
        setWarningMessages(warningMessages);
        return this;
    }

    public ValidationResults errorMessages(List<ErrorMessages> errorMessages) {
        setErrorMessages(errorMessages);
        return this;
    }

    public ValidationResults status(String status) {
        setStatus(status);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ValidationResults)) {
            return false;
        }
        ValidationResults validationResults = (ValidationResults) o;
        return Objects.equals(infoMessages, validationResults.infoMessages) && Objects.equals(warningMessages, validationResults.warningMessages) && Objects.equals(errorMessages, validationResults.errorMessages) && Objects.equals(status, validationResults.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infoMessages, warningMessages, errorMessages, status);
    }

    @Override
    public String toString() {
        return "{" +
            " infoMessages='" + getInfoMessages() + "'" +
            ", warningMessages='" + getWarningMessages() + "'" +
            ", errorMessages='" + getErrorMessages() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
    
}
