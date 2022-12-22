package com.example.bookare.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    //programmani to'xtatmaydi
    private final String resourceName;
    private final String fieldName;
    private Object fieldValue ;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String authentification, String password) {
        super(String.format("%s not found with %s ", authentification, password));
        this.resourceName = authentification;
        this.fieldName = password;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}

