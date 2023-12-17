package com.golden_raspberry_awards.api.application.core.domain.exception;

public class ErrorBodyResponse {

    private String message;
    private int status;
    private String detail;
    private String timestamp;

    public ErrorBodyResponse(String message, int status, String detail, String timestamp) {
        this.message = message;
        this.status = status;
        this.detail = detail;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", detail='" + detail + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
