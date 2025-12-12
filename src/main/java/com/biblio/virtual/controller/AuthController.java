package com.biblio.virtual.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication; // IMPORTANTE
import org.springframework.security.core.context.SecurityContextHolder; // IMPORTANTE
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping; // IMPORTANTE
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblio.virtual.dto.AuthRequest;
import com.biblio.virtual.dto.AuthResponse;
import com.biblio.virtual.model.Usuario;
import com.biblio.virtual.repository.UsuarioRepository;
import com.biblio.virtual.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
		}

		Usuario usuario = usuarioRepository.findByUsername(authRequest.getUsername())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		// Generamos el token (asegurando que el rol vaya limpio)
		String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());

		return ResponseEntity.ok(new AuthResponse(token, usuario.getUsername(), usuario.getRole()));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
		if (usuarioRepository.findByUsername(authRequest.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("El usuario ya existe");
		}

		Usuario usuario = new Usuario();
		usuario.setUsername(authRequest.getUsername());
		usuario.setPassword(passwordEncoder.encode(authRequest.getPassword()));
		usuario.setRole("ROLE_USER");

		usuarioRepository.save(usuario);

		return ResponseEntity.ok("Usuario registrado exitosamente");
	}

	// 👇 EL MÉTODO DIAGNÓSTICO (ESTO ES ORO PURO PARA DEBUGGEAR) 👇
	@GetMapping("/me")
	public ResponseEntity<Map<String, Object>> verifyUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No hay autenticación"));
		}

		return ResponseEntity.ok(Map.of("username", auth.getName(), "authorities", auth.getAuthorities(),
				"isAuthenticated", auth.isAuthenticated()));
	}
}