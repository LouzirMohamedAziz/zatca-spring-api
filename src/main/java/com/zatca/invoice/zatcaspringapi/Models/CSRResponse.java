package com.zatca.invoice.zatcaspringapi.Models;
import java.util.Objects;

public class CSRResponse {

    String requestID;
    String dispositionMessage;
    String binarySecurityToken;
    String secret;
    String errors;

    public CSRResponse() {
    }

    public CSRResponse(String requestID, String dispositionMessage, String binarySecurityToken, String secret, String errors) {
        this.requestID = requestID;
        this.dispositionMessage = dispositionMessage;
        this.binarySecurityToken = binarySecurityToken;
        this.secret = secret;
        this.errors = errors;
    }

    public String getRequestID() {
        return this.requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getDispositionMessage() {
        return this.dispositionMessage;
    }

    public void setDispositionMessage(String dispositionMessage) {
        this.dispositionMessage = dispositionMessage;
    }

    public String getBinarySecurityToken() {
        return this.binarySecurityToken;
    }

    public void setBinarySecurityToken(String binarySecurityToken) {
        this.binarySecurityToken = binarySecurityToken;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getErrors() {
        return this.errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public CSRResponse requestID(String requestID) {
        setRequestID(requestID);
        return this;
    }

    public CSRResponse dispositionMessage(String dispositionMessage) {
        setDispositionMessage(dispositionMessage);
        return this;
    }

    public CSRResponse binarySecurityToken(String binarySecurityToken) {
        setBinarySecurityToken(binarySecurityToken);
        return this;
    }

    public CSRResponse secret(String secret) {
        setSecret(secret);
        return this;
    }

    public CSRResponse errors(String errors) {
        setErrors(errors);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CSRResponse)) {
            return false;
        }
        CSRResponse cSRResponse = (CSRResponse) o;
        return Objects.equals(requestID, cSRResponse.requestID) && Objects.equals(dispositionMessage, cSRResponse.dispositionMessage) && Objects.equals(binarySecurityToken, cSRResponse.binarySecurityToken) && Objects.equals(secret, cSRResponse.secret) && Objects.equals(errors, cSRResponse.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID, dispositionMessage, binarySecurityToken, secret, errors);
    }

    @Override
    public String toString() {
        return "{" +
            " requestID='" + getRequestID() + "'" +
            ", dispositionMessage='" + getDispositionMessage() + "'" +
            ", binarySecurityToken='" + getBinarySecurityToken() + "'" +
            ", secret='" + getSecret() + "'" +
            ", errors='" + getErrors() + "'" +
            "}";
    }

    
}
