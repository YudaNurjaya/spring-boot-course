package com.example.demospringboot.model.response;

public abstract class CommonResponse {
    private String status;
    private String message;
    private String code;

    public CommonResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public CommonResponse(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
