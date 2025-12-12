package com.biblio.virtual.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "libros")
public class Libro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "El título no debe estar vacío")
	private String titulo;

	// CAMBIO 1: FetchType.EAGER para que los autores carguen SIEMPRE
	// @JsonIgnoreProperties evita el bucle infinito (Libro -> Autor -> Libro...)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "libro_autor", joinColumns = @JoinColumn(name = "libro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
	@JsonIgnoreProperties("libros")
	private List<Autor> autores = new ArrayList<>();

	// CAMBIO 2: FetchType.EAGER para asegurar que el género llegue al Front
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "genero_id")
	@JsonIgnoreProperties("libros")
	private Genero genero;

	@NotNull(message = "El año de publicación no debe estar vacío")
	@Column(name = "anio_publicacion")
	private Integer anioPublicacion;

	private boolean disponible;

	private String portada;

	// CAMBIO 3 (CRÍTICO): @JsonIgnore
	// Esto arregla el Error 500. Le dice al sistema:
	// "No intentes convertir la lista de préstamos a JSON, ignórala".
	@OneToMany(mappedBy = "libro", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Prestamo> prestamos = new ArrayList<>();

	// --- Getters y Setters ---

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

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
}