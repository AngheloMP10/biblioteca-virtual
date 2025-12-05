package com.biblio.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblio.virtual.model.Prestamo;
import java.util.List;

public interface IPrestamoRepository extends JpaRepository<Prestamo, Long> {

	// Para que un usuario vea SOLO sus préstamos
	List<Prestamo> findByUsuarioUsername(String username);
}
