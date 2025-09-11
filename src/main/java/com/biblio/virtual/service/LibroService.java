package com.biblio.virtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblio.virtual.model.Libro;
import com.biblio.virtual.repository.ILibroRepository;

@Service
public class LibroService implements ILibroService {

	@Autowired
	private ILibroRepository repo;

	@Override
	public void save(Libro libro) {
		repo.save(libro);
	}

	@Override
	public Libro findById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public List<Libro> findAll() {
		return repo.findAll();
	}

	@Override
	public Page<Libro> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
}
