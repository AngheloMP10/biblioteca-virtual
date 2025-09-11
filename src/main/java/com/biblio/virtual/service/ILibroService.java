package com.biblio.virtual.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.biblio.virtual.model.Libro;

public interface ILibroService {

	void save(Libro libro);

	Libro findById(Long id);

	List<Libro> findAll();

	Page<Libro> findAll(Pageable pageable);

	void delete(Long id);
}
