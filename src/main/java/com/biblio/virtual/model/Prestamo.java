package com.biblio.virtual.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // <--- Importante

@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_solicitud", nullable = false)
	private LocalDate fechaSolicitud;

	@Column(name = "fecha_devolucion")
	private LocalDate fechaDevolucion;

	// PENDIENTE | APROBADO | RECHAZADO
	@Column(nullable = false)
	private String estado;

	// RELACIÓN CON USUARIO
	// Usamos EAGER para asegurar que los datos del usuario viajen con el préstamo
	// @JsonIgnoreProperties evita que entremos en bucle con la lista del usuario
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = false)
	@JsonIgnoreProperties({ "prestamos", "password", "role" })
	// Nota: Ocultamos 'prestamos' para evitar errores, y 'password' por seguridad.
	private Usuario usuario;

	// RELACIÓN CON LIBRO
	// Usamos EAGER para que veas el título y portada en la tabla de préstamos
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "libro_id", nullable = false)
	@JsonIgnoreProperties("prestamos")
	private Libro libro;

	public Prestamo() {
	}

	// Getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}
}