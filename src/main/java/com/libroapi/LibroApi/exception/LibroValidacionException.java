package com.libroapi.LibroApi.exception;

//Se lanza cuando faltan datos, etc.
public class LibroValidacionException extends RuntimeException {
    public LibroValidacionException(String message) {
        super(message);
    }
}
