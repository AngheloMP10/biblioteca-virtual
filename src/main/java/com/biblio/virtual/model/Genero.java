package com.biblio.virtual.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList; // Importante inicializar

// Importamos JsonIgnore (la solución al error 500)
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "generos")
public class Genero implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "El nombre del género no debe estar vacío")
	private String nombre;

	// CAMBIO CRÍTICO:
	// Usamos @JsonIgnore en lugar de @JsonIgnoreProperties.
	// Esto evita que intente cargar la lista Lazy y rompa la app.
	@OneToMany(mappedBy = "genero")
	@JsonIgnore
	private List<Libro> libros = new ArrayList<>();

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}
}