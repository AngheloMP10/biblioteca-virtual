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
}
