package com.biblio.virtual.controller;

import com.biblio.virtual.model.Autor;
import com.biblio.virtual.service.IAutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

	private final IAutorService service;

	public AutorController(IAutorService service) {
		this.service = service;
	}

	// Solo ADMIN puede crear autores
	// Crear Autor
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Autor> guardar(@RequestBody Autor autor) {
		if (autor.getLibros() == null) {
			autor.setLibros(new java.util.ArrayList<>());
		}
		Autor nuevoAutor = service.save(autor);
		return ResponseEntity.ok(nuevoAutor);
	}

	// ADMIN y USER pueden ver un autor por ID
	// Leer Autor por ID
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
		Autor autor = service.findById(id);
		return (autor != null) ? ResponseEntity.ok(autor) : ResponseEntity.notFound().build();
	}

	// ADMIN y USER pueden listar todos los autores
	// Listar todos los autores
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<Autor>> listar() {
		return ResponseEntity.ok(service.findAll());
	}

	// Solo ADMIN puede actualizar
	// Actualizar Autor
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Autor> actualizar(@PathVariable Long id, @RequestBody Autor autorActualizado) {
		Autor autor = service.findById(id);
		if (autor == null) {
			return ResponseEntity.notFound().build();
		}
		autor.setNombre(autorActualizado.getNombre());
		autor.setUrlFoto(autorActualizado.getUrlFoto());
		if (autorActualizado.getLibros() != null) {
			autor.setLibros(autorActualizado.getLibros());
		}
		Autor actualizado = service.save(autor);
		return ResponseEntity.ok(actualizado);
	}

	// Solo ADMIN puede eliminar
	// Eliminar Autor
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		Autor autor = service.findById(id);
		if (autor == null) {
			return ResponseEntity.notFound().build();
		}
		service.delete(id);
		return ResponseEntity.ok("Autor eliminado correctamente");
	}
}
