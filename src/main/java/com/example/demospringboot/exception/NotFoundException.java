package com.example.demospringboot.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super("Data not found");
    }
    public NotFoundException(String message){
        super(message);
    }
}
