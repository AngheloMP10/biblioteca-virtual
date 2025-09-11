package com.biblio.virtual.repository;

import org.springframework.data.repository.CrudRepository;
import com.biblio.virtual.model.Autor;

public interface IAutorRepository extends CrudRepository<Autor, Long> {

}
