package com.biblio.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblio.virtual.model.Autor;

public interface IAutorRepository extends JpaRepository<Autor, Long> {
}
