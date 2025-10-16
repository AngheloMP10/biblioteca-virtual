package com.biblio.virtual.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.biblio.virtual.model.Libro;
import com.biblio.virtual.service.IAutorService;
import com.biblio.virtual.service.IGeneroService;
import com.biblio.virtual.service.ILibroService;

@RestController
@RequestMapping("/libros")
public class LibrosController {

	private final ILibroService libroService;
	private final IGeneroService generoService;
	private final IAutorService autorService;

	public LibrosController(ILibroService libroService, IGeneroService generoService, IAutorService autorService) {
		this.libroService = libroService;
		this.generoService = generoService;
		this.autorService = autorService;
	}

	// Solo ADMIN puede crear nuevos libros
	// CREATE - Crear nuevo libro con validación de portada por defecto
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Libro> guardar(@RequestBody Libro libro) {
		// Portada por defecto
		if (libro.getPortada() == null || libro.getPortada().isEmpty()) {
			libro.setPortada("_default.jpg");
		}
		libroService.save(libro);
		return ResponseEntity.ok(libro);
	}

	// ADMIN y USER pueden listar libros
	// READ - Listar todos los libros
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping
	public ResponseEntity<List<Libro>> listar() {
		return ResponseEntity.ok(libroService.findAll());
	}

	// ADMIN y USER pueden buscar libro por ID
	// READ - Buscar libro por ID específico
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping("/{id}")
	public ResponseEntity<Libro> buscarPorId(@PathVariable Long id) {
		Libro libro = libroService.findById(id);
		if (libro != null) {
			return ResponseEntity.ok(libro);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Solo ADMIN puede actualizar libros
	// UPDATE - Actualizar libro existente con manejo completo de relaciones
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Libro> actualizar(@PathVariable Long id, @RequestBody Libro libro) {
		Libro existente = libroService.findById(id);
		if (existente != null) {
			existente.setTitulo(libro.getTitulo());
			existente.setPortada(libro.getPortada());
			existente.setAnioPublicacion(libro.getAnioPublicacion());
			existente.setDisponible(libro.isDisponible());

			// Relación con autores
			if (libro.getAutores() != null && !libro.getAutores().isEmpty()) {
				existente.setAutores(libro.getAutores());
			}

			// Relación con género
			if (libro.getGenero() != null) {
				existente.setGenero(libro.getGenero());
			}

			libroService.save(existente);
			return ResponseEntity.ok(existente);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Solo ADMIN puede eliminar libros
	// DELETE - Eliminar libro por ID
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		Libro existente = libroService.findById(id);
		if (existente != null) {
			libroService.delete(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
