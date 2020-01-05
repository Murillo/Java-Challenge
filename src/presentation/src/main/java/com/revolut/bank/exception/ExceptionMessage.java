package com.revolut.bank.exception;

public final class ExceptionMessage {
    private String message;

    private ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ExceptionMessage of(String message){
        return new ExceptionMessage(message);
    }
}
