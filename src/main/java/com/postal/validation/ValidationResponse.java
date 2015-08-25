package com.postal.validation;

/**
 * Created by brakhar on 02.08.15.
 */
public class ValidationResponse {

    private String status = null;
    private Object result = null;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
}
