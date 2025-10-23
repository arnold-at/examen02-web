package com.tecsup.examen2.auth.service;

import com.tecsup.examen2.auth.dto.*;
import com.tecsup.examen2.auth.model.Usuario;
import com.tecsup.examen2.auth.repository.UsuarioRepository;
import com.tecsup.examen2.config.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseGet(() -> {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setUsername(request.getUsername());
                    nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));
                    nuevoUsuario.setNombres(request.getUsername());
                    nuevoUsuario.setApellidos("Usuario");
                    nuevoUsuario.setRol("ADMIN");
                    nuevoUsuario.setActivo(true);
                    return usuarioRepository.save(nuevoUsuario);
                });

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol());

        return LoginResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .rol(usuario.getRol())
                .build();
    }

    public Usuario register(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El usuario ya existe");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }
}