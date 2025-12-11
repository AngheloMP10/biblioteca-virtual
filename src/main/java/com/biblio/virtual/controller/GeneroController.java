package com.biblio.virtual.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.biblio.virtual.model.Genero;
import com.biblio.virtual.service.IGeneroService;

@RestController
@RequestMapping("/generos")
public class GeneroController {

	private final IGeneroService service;

	public GeneroController(IGeneroService service) {
		this.service = service;
	}

	// Solo ADMIN puede crear géneros
	// CREATE - Crear nuevo género
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<Genero> guardar(@RequestBody Genero genero) {
		service.save(genero);
		return ResponseEntity.ok(genero);
	}

	// ADMIN y USER pueden listar géneros
	// READ - Listar todos los géneros
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<Genero>> listar() {
		return ResponseEntity.ok(service.findAll());
	}

	// ADMIN y USER pueden buscar un género por ID
	// READ - Buscar género por ID específico
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<Genero> buscarPorId(@PathVariable Long id) {
		Genero genero = service.findById(id);
		if (genero != null) {
			return ResponseEntity.ok(genero);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Solo ADMIN puede actualizar géneros
	// UPDATE - Actualizar género existente
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Genero> actualizar(@PathVariable Long id, @RequestBody Genero genero) {
		Genero existente = service.findById(id);
		if (existente != null) {
			existente.setNombre(genero.getNombre());
			service.save(existente);
			return ResponseEntity.ok(existente);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Solo ADMIN puede eliminar géneros
	// DELETE - Eliminar género por ID
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		Genero existente = service.findById(id);
		if (existente != null) {
			service.delete(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
