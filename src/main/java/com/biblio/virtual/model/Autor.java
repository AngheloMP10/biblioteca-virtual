package com.biblio.virtual.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList; // No olvides importar ArrayList

import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonIgnoreProperties; <--- BORRA ESTE IMPORT
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "autores")
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre del autor no debe estar vacío")
    private String nombre;

    @Column(name = "url_foto")
    private String urlFoto;

    @ManyToMany(mappedBy = "autores", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    // @JsonIgnoreProperties("autores") <--- BORRA ESTA LÍNEA (Redundante)
    @JsonIgnore // <--- ESTA ES LA QUE VALE. MANTENLA.
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

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}
}
