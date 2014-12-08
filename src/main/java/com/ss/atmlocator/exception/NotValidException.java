package com.ss.atmlocator.exception;

//exception for validators
public class NotValidException extends Exception {
    public NotValidException(){
        super();
    }
    public NotValidException(String message){
        super(message);
    }
}
