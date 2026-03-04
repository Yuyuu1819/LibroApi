package com.libroapi.LibroApi.service;

import com.libroapi.LibroApi.exception.LibroForbiddenException;
import com.libroapi.LibroApi.exception.LibroNotFoundException;
import com.libroapi.LibroApi.exception.LibroValidacionException;
import com.libroapi.LibroApi.model.Libro;
import com.libroapi.LibroApi.repository.LibroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

// Este service tiene todas las validaciones para no saturar LibroService y que sea más "clean"

@Slf4j
@Service
public class LibroValidacionesService {

    private final LibroRepository libroRepository;

    //constructor con inyección de dependencias
    public LibroValidacionesService(LibroRepository libroRepository) { this.libroRepository = libroRepository; }

    /// VALIDACIONES DE POST ENDPOINT: Agregar libro \{^u^}/

    // Llamamos a todas las validaciones pertinentes al endpoint
    public void validarAgregarLibro(Libro libro) {
        validarDatosCompletos(libro);
        validarNoEsColleenHoover(libro.getAutor());
        validarLongitudAutor(libro.getAutor());
        validarFormato(libro);
        validarVivesEnEnsenada();
    }

    // 1. ¿El libro tiene sus datos completos?
    private void validarDatosCompletos(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank() ||
                libro.getAutor()  == null || libro.getAutor().isBlank()  ||
                libro.getGenero() == null || libro.getGenero().isBlank()) {
            throw new LibroValidacionException("El libro debe tener título, autor y género. No puede haber campos vacíos.");
        }
    }

    // 2. No puede haber libros de Colleen Hoover
    private void validarNoEsColleenHoover(String autor) {
        if (autor != null && autor.equalsIgnoreCase("Colleen Hoover")) {
            throw new LibroValidacionException("No se permiten libros de Colleen Hoover en esta biblioteca. Sorry not sorry.");
        }
    }

    // 3. ¿El nombre del autor tiene más de 100 caracteres?
    private void validarLongitudAutor(String autor) {
        if (autor != null && autor.length() > 100) {
            throw new LibroValidacionException("El nombre del autor no puede tener más de 100 caracteres. Quién tiene un nombre tan largo?");
        }
    }

    // 4 & 5. ¿Es digital? ¿Es físico? — el campo esDigital ya define el formato,
    //        así que solo logueamos cuál es para que quede registro.
    private void validarFormato(Libro libro) {
        if (libro.isEsDigital()) {
            log.info("El libro '{}' es digital.", libro.getTitulo());
        } else {
            log.info("El libro '{}' es físico.", libro.getTitulo());
        }
    }

    // 6. ¿Vives en Ensenada? — Vivimos en Ensenada, siempre pasa ✅
    private void validarVivesEnEnsenada() {
        log.info("¿Vives en Ensenada? Sí, siempre. Qué linda ciudad. Validación aprobada 🌊");
    }

    /// VALIDACIONES DE DELETE ENDPOINT: Eliminar libro \{^u^}/

    // Llamamos a todas las validaciones pertinentes al endpoint
    public void validarEliminarLibro(int id) {
        validarLibroExiste(id);
        validarMePrestasDinero();
        validarTienesPermisos();
        validarSoloGegeAkutami(id);
        validarFormatoParaEliminar(id);
    }

    // 1. ¿El libro existe?
    private void validarLibroExiste(int id) {
        if (libroRepository.getLibroById(id) == null) {
            throw new LibroNotFoundException(id);
        }
    }

    // 2. ¿Me prestas dinero? — Validación filosófica, siempre pasa con un log curioso
    private void validarMePrestasDinero() {
        log.info("¿Me prestas dinero? ...Ignorando esta pregunta y continuando. Validación aprobada 💸");
    }

    // 3. ¿Tienes permisos? — Permisos hardcoded en true porque no hay auth implementada aún
    private void validarTienesPermisos() {
        boolean tienePermisos = true;
        if (!tienePermisos) {
            throw new LibroForbiddenException("No tienes permisos para eliminar libros.");
        }
        log.info("Permisos verificados correctamente.");
    }

    // 4. No pueden eliminarse libros a menos de que sean de Gege Akutami
    private void validarSoloGegeAkutami(int id) {
        Libro libro = libroRepository.getLibroById(id);
        if (libro != null && !libro.getAutor().equalsIgnoreCase("Gege Akutami")) {
            throw new LibroForbiddenException(
                    "Solo pueden eliminarse libros de Gege Akutami. '" + libro.getTitulo() + "' no cumple esta regla."
            );
        }
    }

    // 5. ¿Es digital? — Solo logueamos, no bloquea la operación
    private void validarFormatoParaEliminar(int id) {
        Libro libro = libroRepository.getLibroById(id);
        if (libro != null) {
            if (libro.isEsDigital()) {
                log.info("El libro a eliminar '{}' es digital.", libro.getTitulo());
            } else {
                log.info("El libro a eliminar '{}' es físico.", libro.getTitulo());
            }
        }
    }

    /// VALIDACIONES DE PUT ENDPOINT: Actualizar libro \{^u^}/

    // Llamamos a todas las validaciones pertinentes al endpoint
    public void validarActualizarLibro(int id, Libro libroActualizado) {
        validarLibroExiste(id);
        validarDatosNoNulos(libroActualizado);
        validarLongitudAutor(libroActualizado.getAutor());
        validarNoEsColleenHoover(libroActualizado.getAutor());
    }

    // 2. Los datos a cambiar no son null?
    private void validarDatosNoNulos(Libro libro) {
        if (libro.getTitulo() == null || libro.getAutor() == null || libro.getGenero() == null) {
            throw new LibroValidacionException("Los datos a actualizar no pueden ser null. Manda título, autor y género.");
        }
    }

    /// VALIDACIONES DE GET ENDPOINT: Obtener todos los libros \{^u^}/

    // Llamamos a todas las validaciones pertinentes al endpoint
       public void validarGetLibros() {
        validarHayLibros();
        validarPermisosLectura();
        validarMenosDe2000Libros();
        validarHayPanEnCasa();
        validarHayMasLibrosDigitales();
    }

    // 1. ¿Hay libros?
    private void validarHayLibros() {
        if (libroRepository.getLibros().isEmpty()) {
            throw new LibroNotFoundException(0); // Reutilizamos pero con mensaje propio :0
        }
    }

    // 2. ¿Tienes permisos?
    private void validarPermisosLectura() {
        boolean tienePermisos = true;
        if (!tienePermisos) {
            throw new LibroForbiddenException("No tienes permisos para ver los libros.");
        }
        log.info("Permisos de lectura verificados.");
    }

    // 3. Debe haber menos de 2,000 libros
    private void validarMenosDe2000Libros() {
        List<Libro> libros = libroRepository.getLibros();
        if (libros.size() >= 2000) {
            throw new LibroValidacionException("La biblioteca tiene 2,000 libros o más. Eso es demasiado. Dona algunos primero.");
        }
    }

    // 4. ¿Hay pan en casa? — Validación existencial, siempre pasa con un recordatorio
    private void validarHayPanEnCasa() {
        log.info("¿Hay pan en casa? Esperemos que sí, pero no de Tecate. Validación aprobada 🍞");
    }

    // 5. ¿Hay más libros digitales? — Solo informativo, no bloquea
    private void validarHayMasLibrosDigitales() {
        List<Libro> libros = libroRepository.getLibros();
        long digitales = libros.stream().filter(Libro::isEsDigital).count();
        long fisicos   = libros.size() - digitales;
        if (digitales > fisicos) {
            log.info("Hay más libros digitales ({}) que físicos ({}).", digitales, fisicos);
        } else {
            log.info("Hay más libros físicos ({}) que digitales ({}).", fisicos, digitales);
        }
    }

    /// VALIDACIONES DE GET (id) ENDPOINT: Obtener libro \{^u^}/

    // Llamamos a todas las validaciones pertinentes al endpoint
        public void validarGetLibroById(int id) {
        validarIdTieneLibro(id);
        validarQuieresCookies();
        validarHayMatchaEnCasa();
        validarNoHayIdsDuplicados(id);
    }

    // 1. ¿El id tiene un libro asignado?
    private void validarIdTieneLibro(int id) {
        if (libroRepository.getLibroById(id) == null) {
            throw new LibroNotFoundException(id);
        }
    }

    // 2. ¿Quieres cookies? — Validación muy importante
    private void validarQuieresCookies() {
        log.info("¿Quieres cookies? La verdadera pregunta es... ¿quiên no? Validación aprobada 🍪");
    }

    // 3. ¿Hay matcha en casa?
    private void validarHayMatchaEnCasa() {
        log.info("¿Hay matcha en casa? Obvioo. Validación aprobada 🍵");
    }

    // 4. ¿No hay ids duplicados? — La lista en memoria no permite duplicados por cómo
    //    se construye, pero lo verificamos de todas formas
    private void validarNoHayIdsDuplicados(int id) {
        long count = libroRepository.getLibros().stream()
                .filter(l -> l.getId() == id)
                .count();
        if (count > 1) {
            throw new LibroValidacionException("Se encontraron ids duplicados para el id: " + id + ". Algo está muy mal.");
        }
        log.info("Sin ids duplicados para el id {}. Todo clean.", id);
    }
}
