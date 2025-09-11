package com.biblio.virtual.service;

import com.biblio.virtual.model.Libro;
import com.biblio.virtual.repository.ILibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LibroServiceTest {

	@Mock
	private ILibroRepository repo;

	@InjectMocks
	private LibroService service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Inicializa los mocks
	}

	@Test
	void testSave() {
		Libro libro = new Libro();
		libro.setId(1L);
		libro.setTitulo("Clean Code");

		service.save(libro);

		verify(repo, times(1)).save(libro);
	}

	@Test
	void testFindById() {
		Libro libro = new Libro();
		libro.setId(1L);
		libro.setTitulo("Spring Boot");

		when(repo.findById(1L)).thenReturn(Optional.of(libro));

		Libro resultado = service.findById(1L);

		assertThat(resultado).isNotNull();
		assertThat(resultado.getTitulo()).isEqualTo("Spring Boot");
		verify(repo, times(1)).findById(1L);
	}

	@Test
	void testFindById_NotFound() {
		when(repo.findById(99L)).thenReturn(Optional.empty());

		Libro resultado = service.findById(99L);

		assertThat(resultado).isNull();
		verify(repo, times(1)).findById(99L);
	}

	@Test
	void testFindAll() {
		Libro l1 = new Libro();
		l1.setId(1L);
		l1.setTitulo("Libro 1");

		Libro l2 = new Libro();
		l2.setId(2L);
		l2.setTitulo("Libro 2");

		when(repo.findAll()).thenReturn(Arrays.asList(l1, l2));

		List<Libro> resultado = service.findAll();

		assertThat(resultado).hasSize(2);
		assertThat(resultado.get(0).getTitulo()).isEqualTo("Libro 1");
		verify(repo, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Libro l1 = new Libro();
		l1.setId(1L);
		l1.setTitulo("Libro 1");

		PageRequest pageable = PageRequest.of(0, 10);
		Page<Libro> page = new PageImpl<>(List.of(l1), pageable, 1);

		when(repo.findAll(pageable)).thenReturn(page);

		Page<Libro> resultado = service.findAll(pageable);

		assertThat(resultado.getContent()).hasSize(1);
		assertThat(resultado.getContent().get(0).getTitulo()).isEqualTo("Libro 1");
		verify(repo, times(1)).findAll(pageable);
	}

	@Test
	void testDelete() {
		Long id = 1L;

		service.delete(id);

		verify(repo, times(1)).deleteById(id);
	}
}
