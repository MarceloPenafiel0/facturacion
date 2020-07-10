package com.example.facturacion.exceptions;

public class IllegalChargeOperationException extends RuntimeException{
    public IllegalChargeOperationException(){
        super("Operacion de Cargo no Permitida");
    }
}
