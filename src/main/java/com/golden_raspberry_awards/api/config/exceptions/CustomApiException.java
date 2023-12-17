package com.golden_raspberry_awards.api.config.exceptions;

import io.quarkus.runtime.annotations.StaticInitSafe;

@StaticInitSafe
public class CustomApiException extends RuntimeException {

    private int status;
    private String detail;

    public CustomApiException(String message, int status, String detail) {
        super(message);
        this.status = status;
        this.detail = detail;
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
}
