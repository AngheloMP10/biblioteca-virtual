-- ========================
-- AUTORES
-- ========================
INSERT INTO autores (id, nombre, url_foto) VALUES (1, 'Gabriel García Márquez', NULL);
INSERT INTO autores (id, nombre, url_foto) VALUES (2, 'Mario Vargas Llosa', NULL);
INSERT INTO autores (id, nombre, url_foto) VALUES (3, 'Isabel Allende', NULL);

-- ========================
-- GÉNEROS
-- ========================
INSERT INTO generos (id, nombre) VALUES (1, 'Realismo Mágico');
INSERT INTO generos (id, nombre) VALUES (2, 'Novela Histórica');
INSERT INTO generos (id, nombre) VALUES (3, 'Drama');

-- ========================
-- LIBROS
-- ========================
INSERT INTO libros (id, titulo, anio_publicacion, disponible, portada, genero_id)
VALUES (1, 'Cien Años de Soledad', 1967, true, NULL, 1);

INSERT INTO libros (id, titulo, anio_publicacion, disponible, portada, genero_id)
VALUES (2, 'La Ciudad y los Perros', 1963, true, NULL, 2);

INSERT INTO libros (id, titulo, anio_publicacion, disponible, portada, genero_id)
VALUES (3, 'La Casa de los Espíritus', 1982, true, NULL, 1);

-- ========================
-- RELACIÓN LIBROS - AUTORES (tabla intermedia libro_autor)
-- ========================
INSERT INTO libro_autor (libro_id, autor_id) VALUES (1, 1); -- Cien años de soledad -> García Márquez
INSERT INTO libro_autor (libro_id, autor_id) VALUES (2, 2); -- La ciudad y los perros -> Vargas Llosa
INSERT INTO libro_autor (libro_id, autor_id) VALUES (3, 3); -- La casa de los espíritus -> Allende
