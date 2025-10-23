package com.tecsup.examen2.auth.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginResponse {
    private String token;
    private String username;
    private String nombres;
    private String apellidos;
    private String rol;
}