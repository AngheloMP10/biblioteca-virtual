package com.biblio.virtual.controller;

import com.biblio.virtual.model.Autor;
import com.biblio.virtual.service.IAutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

	private final IAutorService service;

	public AutorController(IAutorService service) {
		this.service = service;
	}

	// Solo ADMIN puede crear autores
	// Crear Autor
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Autor> guardar(@RequestParam String nombre, @RequestParam(required = false) String urlFoto) {
		Autor autor = new Autor();
		autor.setNombre(nombre);
		autor.setUrlFoto(urlFoto);
		Autor nuevoAutor = service.save(autor);
		return ResponseEntity.ok(nuevoAutor);
	}

	// ADMIN y USER pueden ver un autor por ID
	// Leer Autor por ID
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping("/{id}")
	public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
		Autor autor = service.findById(id);
		return (autor != null) ? ResponseEntity.ok(autor) : ResponseEntity.notFound().build();
	}

	// ADMIN y USER pueden listar todos los autores
	// Listar todos los autores
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping
	public ResponseEntity<List<Autor>> listar() {
		return ResponseEntity.ok(service.findAll());
	}

	// Solo ADMIN puede actualizar
	// Actualizar Autor
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Autor> actualizar(@PathVariable Long id, @RequestParam String nombre,
			@RequestParam(required = false) String urlFoto) {
		Autor autor = service.findById(id);
		if (autor == null) {
			return ResponseEntity.notFound().build();
		}
		autor.setNombre(nombre);
		autor.setUrlFoto(urlFoto);
		Autor actualizado = service.save(autor);
		return ResponseEntity.ok(actualizado);
	}

	// Solo ADMIN puede eliminar
	// Eliminar Autor
	@PreAuthorize("hasRole('ADMIN')")
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
