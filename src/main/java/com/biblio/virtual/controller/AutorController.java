package com.biblio.virtual.controller;

import com.biblio.virtual.model.Autor;
import com.biblio.virtual.service.IAutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

	private final IAutorService service;

	public AutorController(IAutorService service) {
		this.service = service;
	}

	// Crear Autor 
	@PostMapping
	public ResponseEntity<Autor> guardar(@RequestParam String nombre, @RequestParam(required = false) String urlFoto) {
		Autor autor = new Autor();
		autor.setNombre(nombre);
		autor.setUrlFoto(urlFoto);
		Autor nuevoAutor = service.save(autor);
		return ResponseEntity.ok(nuevoAutor);
	}

	// Leer Autor por ID
	@GetMapping("/{id}")
	public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
		Autor autor = service.findById(id);
		return (autor != null) ? ResponseEntity.ok(autor) : ResponseEntity.notFound().build();
	}

	// Listar todos los autores
	@GetMapping
	public ResponseEntity<List<Autor>> listar() {
		return ResponseEntity.ok(service.findAll());
	}

	// Actualizar Autor
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

	// Eliminar Autor
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
