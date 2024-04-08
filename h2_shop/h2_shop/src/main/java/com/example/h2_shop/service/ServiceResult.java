package com.example.h2_shop.service;

import com.example.h2_shop.exception.message.MessageError;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

public class ServiceResult<T> implements Serializable {
    private HttpStatus status;
    private String message;
    private String code;
    private transient T data;
    private List<MessageError> messError;

    public ServiceResult() {

    }

    public ServiceResult(T data, HttpStatus status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public ServiceResult(T data, HttpStatus status, String message, String code) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MessageError> getMessError() {
        return messError;
    }

    public void setMessError(List<MessageError> messError) {
        this.messError = messError;
    }
}
