package com.biblio.virtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.biblio.virtual.model.Usuario;
import com.biblio.virtual.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		// Asegurarnos de que el rol tenga el prefijo ROLE_
		String role = usuario.getRole().startsWith("ROLE_") ? 
				usuario.getRole() : "ROLE_" + usuario.getRole();
		return User.builder()
				.username(usuario.getUsername())
				.password(usuario.getPassword())
				.authorities(role)
				.build();
	}

}