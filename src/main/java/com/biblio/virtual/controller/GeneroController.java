package com.biblio.virtual.controller;

import org.springframework.web.bind.annotation.*;
import com.biblio.virtual.model.Genero;
import com.biblio.virtual.service.IGeneroService;

@RestController
@RequestMapping("/genero")
public class GeneroController {

	private final IGeneroService service;

	public GeneroController(IGeneroService service) {
		this.service = service;
	}

	@PostMapping
	public Long guardar(@RequestParam String nombre) {
		Genero genero = new Genero();
		genero.setNombre(nombre);
		service.save(genero);
		return genero.getId();
	}

	@GetMapping("/{id}")
	public String buscarPorId(@PathVariable Long id) {
		Genero genero = service.findById(id);
		return (genero != null) ? genero.getNombre() : "No encontrado";
	}
}
