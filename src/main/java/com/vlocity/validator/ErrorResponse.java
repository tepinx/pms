package com.vlocity.validator;

public class ErrorResponse {

    private String field;
    private Object value;
    private String code;
    private String message;

    public ErrorResponse() {
        super();
    }

    public ErrorResponse(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
    
    public ErrorResponse(String field, Object value, String code, String message) {
        super();
        this.field = field;
        this.value = value;
        this.code = code;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
