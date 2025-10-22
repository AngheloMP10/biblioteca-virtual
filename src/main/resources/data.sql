-- ========================
-- AUTORES
-- ========================
INSERT INTO autores (nombre, url_foto) VALUES ('Gabriel García Márquez', NULL);
INSERT INTO autores (nombre, url_foto) VALUES ('Mario Vargas Llosa', NULL);
INSERT INTO autores (nombre, url_foto) VALUES ('Isabel Allende', NULL);

-- ========================
-- GÉNEROS
-- ========================
INSERT INTO generos (nombre) VALUES ('Realismo Mágico');
INSERT INTO generos (nombre) VALUES ('Novela Histórica');
INSERT INTO generos (nombre) VALUES ('Drama');

-- ========================
-- LIBROS
-- ========================
INSERT INTO libros (titulo, anio_publicacion, disponible, portada, genero_id)
VALUES ('Cien Años de Soledad', 1967, true, NULL, 1);

INSERT INTO libros (titulo, anio_publicacion, disponible, portada, genero_id)
VALUES ('La Ciudad y los Perros', 1963, true, NULL, 2);

INSERT INTO libros (titulo, anio_publicacion, disponible, portada, genero_id)
VALUES ('La Casa de los Espíritus', 1982, true, NULL, 1);

-- ========================
-- RELACIÓN LIBROS - AUTORES (tabla intermedia libro_autor)
-- ========================
INSERT INTO libro_autor (libro_id, autor_id) VALUES (1, 1); -- Cien años de soledad -> García Márquez
INSERT INTO libro_autor (libro_id, autor_id) VALUES (2, 2); -- La ciudad y los perros -> Vargas Llosa
INSERT INTO libro_autor (libro_id, autor_id) VALUES (3, 3); -- La casa de los espíritus -> Allende