package com.libroapi.LibroApi.service;

import com.libroapi.LibroApi.model.Libro;
import com.libroapi.LibroApi.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    //Inyección de dependencias
    private final LibroRepository libroRepository;
    private final LibroValidacionesService libroValidacionesService;
    public LibroService(LibroRepository libroRepository, LibroValidacionesService libroValidacionesService) {
        this.libroRepository = libroRepository;
        this.libroValidacionesService = libroValidacionesService;
    }

    //Función para ver libros
    public List<Libro> getLibros(){
        libroValidacionesService.validarGetLibros();
        return libroRepository.getLibros();
    }

    //Función para encontrar un libro por du id
    public Libro getLibroById(int id){
        libroValidacionesService.validarGetLibroById(id);
        return libroRepository.getLibroById(id);}

    //Función para agregar libro
    public int addLibro(Libro libro){
        libroValidacionesService.validarAgregarLibro(libro);
        return libroRepository.addLibro(libro); }

    //Función para actualizar libro
    public boolean updateLibro(int id, Libro libro){
        libroValidacionesService.validarActualizarLibro(id, libro);
        return libroRepository.updateLibro(id, libro); }

    //Función para eliminar libro por su id
    public void deleteLibroById(int id){
        libroValidacionesService.validarEliminarLibro(id);
        libroRepository.deleteLibroById(id);}
}
