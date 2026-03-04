package com.libroapi.LibroApi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // Para LibroValidacionException lanza 400 Bad Request
    @ExceptionHandler(LibroValidacionException.class)
    public ResponseEntity<String> handleValidacion(LibroValidacionException ex) {
        log.error("Error de validación: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Para LibroNotFoundException lanza 404 Not Found
    @ExceptionHandler(LibroNotFoundException.class)
    public ResponseEntity<String> handleNotFound(LibroNotFoundException ex) {
        log.error("Libro no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Paraa LibroForbiddenException lanza 403 Forbidden
    @ExceptionHandler(LibroForbiddenException.class)
    public ResponseEntity<String> handleForbidden(LibroForbiddenException ex) {
        log.error("Operación no permitida: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    // Para cualquier otra excepción no contemplada lanza 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrió un error inesperado: " + ex.getMessage());
    }
}