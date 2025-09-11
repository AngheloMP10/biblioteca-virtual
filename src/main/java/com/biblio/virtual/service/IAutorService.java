package com.biblio.virtual.service;

import java.util.List;
import com.biblio.virtual.model.Autor;

public interface IAutorService {

	List<Autor> findAll();

	List<Autor> findAllById(List<Long> ids);
}
