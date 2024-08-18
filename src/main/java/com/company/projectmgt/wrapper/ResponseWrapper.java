package com.company.projectmgt.wrapper;

import java.io.Serializable;
import java.util.Date;

public class ResponseWrapper<T> implements Serializable {
    private T data = null;
    private String message = "Request was successful";
    private int code = 200;
    private Long timestamp = new Date().getTime();

    public ResponseWrapper() {
    }

    public ResponseWrapper(T data, String message, int code, Long timestamp) {
        this.data = data;
        this.message = message;
        this.code = code;
        this.timestamp = timestamp;
    }

    public ResponseWrapper(T data, String message, int code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public ResponseWrapper(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ResponseWrapper(String message, int code) {
        this.code = code;
        this.message = message;
    }

    public ResponseWrapper(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
