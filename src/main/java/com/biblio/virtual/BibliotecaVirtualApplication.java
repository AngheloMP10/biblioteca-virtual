package com.biblio.virtual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biblio.virtual.model.Usuario;
import com.biblio.virtual.repository.UsuarioRepository;

@SpringBootApplication
public class BibliotecaVirtualApplication {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaVirtualApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData() {
		return args -> {
			if (usuarioRepository.findByUsername("admin").isEmpty()) {
				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRole("ADMIN");
				usuarioRepository.save(admin);
				System.out.println("Usuario ADMIN creado: admin / admin123");
			}

			if (usuarioRepository.findByUsername("user").isEmpty()) {
				Usuario user = new Usuario();
				user.setUsername("user");
				user.setPassword(passwordEncoder.encode("user123"));
				user.setRole("USER");
				usuarioRepository.save(user);
				System.out.println("Usuario USER creado: user / user123");
			}
		};
	}
}
