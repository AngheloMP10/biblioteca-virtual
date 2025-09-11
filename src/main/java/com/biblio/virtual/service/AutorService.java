package com.biblio.virtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblio.virtual.model.Autor;
import com.biblio.virtual.repository.IAutorRepository;

@Service
public class AutorService implements IAutorService {

	@Autowired
	private IAutorRepository repo;

	@Override
	public List<Autor> findAll() {
		return (List<Autor>) repo.findAll();
	}

	@Override
	public List<Autor> findAllById(List<Long> ids) {
		return (List<Autor>) repo.findAllById(ids);
	}

	@Override
	public Autor findById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Autor save(Autor autor) {
		return repo.save(autor);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
}
