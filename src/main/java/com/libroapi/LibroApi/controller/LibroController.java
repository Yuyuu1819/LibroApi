package com.libroapi.LibroApi.controller;

import com.libroapi.LibroApi.model.Libro;
import com.libroapi.LibroApi.repository.LibroRepository;
import com.libroapi.LibroApi.service.LibroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/libros")
public class LibroController {
    // Abstracción
    private LibroService libroService;
    // Inyeccción de dependencias
    public LibroController(LibroService libroService){this.libroService = libroService;}

    @PostMapping
    public ResponseEntity<Libro> addLibro(@RequestBody Libro libroSecuela){
        libroService.addLibro(libroSecuela); log.info("Se agregó el libro.");
        return new  ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Libro>> getLibros(){
        List<Libro> libros = libroService.getLibros();
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable int id){
        Libro librito = libroService.getLibroById(id);
        return ResponseEntity.ok(librito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLibro(@PathVariable int id, @RequestBody Libro libro){
        boolean hecho = libroService.updateLibro(id,libro);
        log.info("Se actualizó el libro con id: {}", id);
        return ResponseEntity.ok("Se actualizó el libro con id: " + id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLibro(@PathVariable int id){
        libroService.deleteLibroById(id);
        log.info("Se eliminó el libro con id: {}", id);
        return ResponseEntity.ok("Se eliminó el libro con id: " + id);
    }

}
