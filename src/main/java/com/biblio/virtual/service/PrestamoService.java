package com.biblio.virtual.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblio.virtual.model.Libro;
import com.biblio.virtual.model.Prestamo;
import com.biblio.virtual.model.Usuario;
import com.biblio.virtual.repository.ILibroRepository;
import com.biblio.virtual.repository.IPrestamoRepository;
import com.biblio.virtual.repository.UsuarioRepository;

@Service
public class PrestamoService implements IPrestamoService {

	private final IPrestamoRepository prestamoRepo;
	private final UsuarioRepository usuarioRepo;
	private final ILibroRepository libroRepo;

	public PrestamoService(IPrestamoRepository prestamoRepo, UsuarioRepository usuarioRepo,
			ILibroRepository libroRepo) {
		this.prestamoRepo = prestamoRepo;
		this.usuarioRepo = usuarioRepo;
		this.libroRepo = libroRepo;
	}

	@Override
	@Transactional
	public Prestamo solicitarPrestamo(Long libroId, String username) {

		Usuario usuario = usuarioRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Libro libro = libroRepo.findById(libroId).orElseThrow(() -> new RuntimeException("Libro no encontrado"));

		if (!libro.isDisponible()) {
			throw new RuntimeException("El libro no está disponible");
		}

		Prestamo prestamo = new Prestamo();
		prestamo.setUsuario(usuario);
		prestamo.setLibro(libro);
		prestamo.setFechaSolicitud(LocalDate.now());
		prestamo.setEstado("PENDIENTE");

		return prestamoRepo.save(prestamo);
	}

	@Override
	public List<Prestamo> findAll() {
		return prestamoRepo.findAll();
	}

	@Override
	public List<Prestamo> findByUsername(String username) {
		return prestamoRepo.findByUsuarioUsername(username);
	}
}
