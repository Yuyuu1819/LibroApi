package com.libroapi.LibroApi.repository;

import com.libroapi.LibroApi.model.Libro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroRepository {
    //Lista de libros
    private List<Libro> libros = new ArrayList<>(List.of(new Libro(0, "The Hunger Games", "Suzanne Collins", "distopía", false)));

    //Función para ver libros
    public List<Libro> getLibros() {return libros;}

    //Función para encontrar libro por su id
    public Libro getLibroById(int id){
        for (Libro libro : libros) {
            if(libro.getId() == id){ return libro; }
        } return null;
    }

    //Función para ver agregar libro
    public int addLibro(Libro libro){ libros.add(libro); return libro.getId(); }

    //Función para eliminar libro
    public boolean deleteLibroById(int id){
        Libro libro = getLibroById(id);
        if (libro != null) {
            libros.remove(getLibroById(id));
            return true;
        } return false;
    }

    //Función para actualizar libro
    public boolean updateLibro(int id, Libro libroUpdated){
        Libro libro = getLibroById(id);
        if (libro != null) {
            libro.setTitulo(libroUpdated.getTitulo());
            libro.setAutor(libroUpdated.getAutor());
            libro.setGenero(libroUpdated.getGenero());
            return true;
        } return false;
    }
}
