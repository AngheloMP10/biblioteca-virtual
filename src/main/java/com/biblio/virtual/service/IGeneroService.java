package com.biblio.virtual.service;

import java.util.List;
import com.biblio.virtual.model.Genero;

public interface IGeneroService {

	void save(Genero genero);

	Genero findById(Long id);

	void delete(Long id);

	List<Genero> findAll();
}
