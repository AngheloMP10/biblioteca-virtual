package com.biblio.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblio.virtual.model.Libro;

public interface ILibroRepository extends JpaRepository<Libro, Long> {

}
