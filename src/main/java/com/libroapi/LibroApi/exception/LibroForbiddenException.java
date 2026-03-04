package com.libroapi.LibroApi.exception;

// Se lanza cuando la operación no está permitida por permisos
public class LibroForbiddenException extends RuntimeException {
    public LibroForbiddenException(String message) {
        super(message);
    }
}
