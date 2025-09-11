package com.biblio.virtual.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biblio.virtual.model.Autor;
import com.biblio.virtual.model.Libro;
import com.biblio.virtual.service.IAutorService;
import com.biblio.virtual.service.IGeneroService;
import com.biblio.virtual.service.ILibroService;

import jakarta.validation.Valid;

@Controller
public class LibrosController {

	private ILibroService libroService;
	private IGeneroService generoService;
	private IAutorService autorService;

	public LibrosController(ILibroService libroService, IGeneroService generoService, IAutorService autorService) {
		this.libroService = libroService;
		this.generoService = generoService;
		this.autorService = autorService;
	}

	@GetMapping("/libro")
	public String crear(Model model) {
		Libro libro = new Libro();
		model.addAttribute("libro", libro);
		model.addAttribute("generos", generoService.findAll());
		model.addAttribute("autores", autorService.findAll());
		model.addAttribute("titulo", "Nuevo Libro");
		return "libro";
	}

	@GetMapping("/libro/{id}")
	public String editar(@PathVariable(name = "id") Long id, Model model) {
		Libro libro = libroService.findById(id);

		if (libro == null) {
			return "redirect:/home"; // o página de error
		}

		String ids = "";
		for (Autor autor : libro.getAutores()) {
			if (ids.isEmpty()) {
				ids = autor.getId().toString();
			} else {
				ids += "," + autor.getId().toString();
			}
		}

		model.addAttribute("libro", libro);
		model.addAttribute("ids", ids);
		model.addAttribute("generos", generoService.findAll());
		model.addAttribute("autores", autorService.findAll());
		model.addAttribute("titulo", "Editar Libro");
		return "libro";
	}

	@PostMapping("/libro")
	public String guardar(@Valid Libro libro, BindingResult br, @ModelAttribute(name = "ids") String ids, Model model) {

		if (br.hasErrors()) {
			model.addAttribute("generos", generoService.findAll());
			model.addAttribute("autores", autorService.findAll());
			model.addAttribute("titulo", "Nuevo Libro");
			return "libro";
		}

		if (ids != null && !"".equals(ids)) {
			List<Long> idsAutores = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
			List<Autor> autores = autorService.findAllById(idsAutores);
			libro.setAutores(autores);
		}

		// portada temporal por defecto
		if (libro.getPortada() == null || libro.getPortada().isEmpty()) {
			libro.setPortada("_default.jpg");
		}

		libroService.save(libro);
		return "redirect:/home";
	}

	@GetMapping({ "/", "home", "/index" })
	public String home(Model model,
			@RequestParam(name = "pagina", required = false, defaultValue = "0") Integer pagina) {

		PageRequest pr = PageRequest.of(pagina, 6);
		Page<Libro> page = libroService.findAll(pr);

		model.addAttribute("libros", page.getContent());

		if (page.getTotalPages() > 0) {
			List<Integer> paginas = IntStream.rangeClosed(1, page.getTotalPages()).boxed().toList();
			model.addAttribute("paginas", paginas);
		}

		model.addAttribute("actual", pagina + 1);
		model.addAttribute("titulo", "Catálogo de Libros");
		model.addAttribute("msj", "Bienvenido al catálogo de la Biblioteca Virtual");
		model.addAttribute("tipoMsj", "success");
		return "home";
	}

	@GetMapping("/listado")
	public String listado(Model model, @RequestParam(required = false) String msj,
			@RequestParam(required = false) String tipoMsj) {

		model.addAttribute("titulo", "Listado de Libros");
		model.addAttribute("libros", libroService.findAll());

		if (msj != null && !msj.isEmpty() && tipoMsj != null && !tipoMsj.isEmpty()) {
			model.addAttribute("msj", msj);
			model.addAttribute("tipoMsj", tipoMsj);
		}

		return "listado";
	}

	@GetMapping("/libro/{id}/delete")
	public String eliminar(@PathVariable(name = "id") Long id, RedirectAttributes redirectAtt) {

		libroService.delete(id);

		redirectAtt.addAttribute("msj", "El libro fue eliminado correctamente.");
		redirectAtt.addAttribute("tipoMsj", "success");

		return "redirect:/listado";
	}
}