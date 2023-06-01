package com.zatca.invoice.zatcaspringapi.Models;
import java.util.Objects;


public class Message {

    String type;
    String code;
    String category;
    String message;
    String status;


    public Message() {
    }

    public Message(String type, String code, String category, String message, String status) {
        this.type = type;
        this.code = code;
        this.category = category;
        this.message = message;
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Message type(String type) {
        setType(type);
        return this;
    }

    public Message code(String code) {
        setCode(code);
        return this;
    }

    public Message category(String category) {
        setCategory(category);
        return this;
    }

    public Message message(String message) {
        setMessage(message);
        return this;
    }

    public Message status(String status) {
        setStatus(status);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(type, message.type) && Objects.equals(code, message.code) && Objects.equals(category, message.category) && Objects.equals(message, message.message) && Objects.equals(status, message.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, code, category, message, status);
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", code='" + getCode() + "'" +
            ", category='" + getCategory() + "'" +
            ", message='" + getMessage() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
    
}
