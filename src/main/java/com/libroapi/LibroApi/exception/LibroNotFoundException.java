package com.libroapi.LibroApi.exception;

// Se lanza cuando no se encuentra un libro por su id
public class LibroNotFoundException extends RuntimeException {
    public LibroNotFoundException(int id) {
        super("No se encontró ningún libro con el id: " + id);
    }
}
