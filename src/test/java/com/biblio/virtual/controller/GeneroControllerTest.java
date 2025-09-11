package com.biblio.virtual.controller;

import com.biblio.virtual.model.Genero;
import com.biblio.virtual.service.IGeneroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeneroController.class)
class GeneroControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IGeneroService generoService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void listarGeneros() throws Exception {
		Genero g1 = new Genero();
		g1.setId(1L);
		g1.setNombre("Drama");

		Genero g2 = new Genero();
		g2.setId(2L);
		g2.setNombre("Comedia");

		List<Genero> generos = Arrays.asList(g1, g2);
		when(generoService.findAll()).thenReturn(generos);

		mockMvc.perform(get("/generos")).andExpect(status().isOk()).andExpect(jsonPath("$[0].nombre").value("Drama"))
				.andExpect(jsonPath("$[1].nombre").value("Comedia"));
	}

	@Test
	void buscarGeneroPorId() throws Exception {
		Genero genero = new Genero();
		genero.setId(1L);
		genero.setNombre("Terror");

		when(generoService.findById(1L)).thenReturn(genero);

		mockMvc.perform(get("/generos/1")).andExpect(status().isOk()).andExpect(jsonPath("$.nombre").value("Terror"));
	}

	@Test
	void crearGenero() throws Exception {
		Genero genero = new Genero();
		genero.setId(1L);
		genero.setNombre("Aventura");

		mockMvc.perform(post("/generos").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(genero))).andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("Aventura"));

		verify(generoService, times(1)).save(genero);
	}

	@Test
	void actualizarGenero() throws Exception {
		Genero existente = new Genero();
		existente.setId(1L);
		existente.setNombre("Sci-Fi");

		when(generoService.findById(1L)).thenReturn(existente);

		Genero actualizado = new Genero();
		actualizado.setNombre("Ciencia Ficción");

		mockMvc.perform(put("/generos/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(actualizado))).andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("Ciencia Ficción"));

		verify(generoService, times(1)).save(existente);
	}

	@Test
	void eliminarGenero() throws Exception {
		Genero existente = new Genero();
		existente.setId(1L);
		existente.setNombre("Romance");

		when(generoService.findById(1L)).thenReturn(existente);

		mockMvc.perform(delete("/generos/1")).andExpect(status().isNoContent());

		verify(generoService, times(1)).delete(1L);
	}
}
