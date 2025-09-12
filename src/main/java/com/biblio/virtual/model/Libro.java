package com.biblio.virtual.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "libros")
public class Libro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "El título no debe estar vacío")
	private String titulo;

	// En esta relación ManyToMany con Autor, este es el lado dueño,
	// así que agregamos @JsonManagedReference para manejar la serialización
	@ManyToMany
	@JoinTable(name = "libro_autor", joinColumns = @JoinColumn(name = "libro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
	@JsonManagedReference
	private List<Autor> autores;

	// En la relación ManyToOne con Genero, este es el lado inverso,
	// así que agregamos @JsonBackReference para evitar bucles
	@ManyToOne
	@JoinColumn(name = "genero_id")
	@JsonBackReference
	private Genero genero;

	@NotNull(message = "El año de publicación no debe estar vacío")
	@Column(name = "anio_publicacion")
	private Integer anioPublicacion;

	private boolean disponible;

	private String portada; // Imagen opcional del libro

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Integer getAnioPublicacion() {
		return anioPublicacion;
	}

	public void setAnioPublicacion(Integer anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}
}
