package com.biblio.virtual.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

// Importamos la protección para JSON
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String role; // "ROLE_ADMIN" o "ROLE_USER"

	// AGREGAMOS ESTO: Relación con Préstamos
	// Usamos @JsonIgnore para que al loguearte no intente descargar
	// todo el historial de préstamos y cause un error o lentitud.
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Prestamo> prestamos = new ArrayList<>();

	// Constructores
	public Usuario() {
	}

	public Usuario(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
}