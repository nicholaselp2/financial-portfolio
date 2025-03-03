package com.elpidoroun.financialportfolio.exceptions;

public class IllegalArgumentException extends RuntimeException{

    private final String errorType;

    public IllegalArgumentException(String message){
        super(message);
        this.errorType = "Invalid Argument";
    }
}