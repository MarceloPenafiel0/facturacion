package com.example.facturacion.exceptions;

public class BadJsonException extends RuntimeException{

    public BadJsonException(){
        super("Bad JSON Format");
    }

}
