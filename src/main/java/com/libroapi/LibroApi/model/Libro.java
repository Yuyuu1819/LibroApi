package com.libroapi.LibroApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Libro {
    private static int idRef = 1;
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private boolean esDigital;
}
