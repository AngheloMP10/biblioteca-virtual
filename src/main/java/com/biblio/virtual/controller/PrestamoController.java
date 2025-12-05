package com.biblio.virtual.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.biblio.virtual.model.Prestamo;
import com.biblio.virtual.service.IPrestamoService;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

	private final IPrestamoService prestamoService;

	public PrestamoController(IPrestamoService prestamoService) {
		this.prestamoService = prestamoService;
	}

	// Solicitar préstamo (USER / ADMIN)
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PostMapping("/solicitar/{libroId}")
	public ResponseEntity<?> solicitarPrestamo(@PathVariable Long libroId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		prestamoService.solicitarPrestamo(libroId, username);
		return ResponseEntity.ok("Solicitud de préstamo enviada con éxito");
	}

	// Listar TODOS los préstamos (ADMIN)
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/todos")
	public ResponseEntity<List<Prestamo>> listarTodos() {
		return ResponseEntity.ok(prestamoService.findAll());
	}

	// Mis préstamos (USER)
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/mios")
	public ResponseEntity<List<Prestamo>> misPrestamos() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		return ResponseEntity.ok(prestamoService.findByUsername(username));
	}
}
