package com.example.facturacion.exceptions;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(){
        super("No data found");
    }
}
