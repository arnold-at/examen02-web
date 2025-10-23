package com.tecsup.examen2.auth.repository;

import com.tecsup.examen2.auth.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
}