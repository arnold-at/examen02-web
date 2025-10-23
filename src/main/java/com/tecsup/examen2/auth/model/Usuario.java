package com.tecsup.examen2.auth.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private String nombres;

    private String apellidos;

    private String rol;

    private boolean activo;
}