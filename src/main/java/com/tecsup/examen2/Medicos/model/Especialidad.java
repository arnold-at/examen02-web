package com.tecsup.examen2.Medicos.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "especialidades")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Especialidad {

    @Id
    private String idEspecialidad;

    @Indexed(unique = true)
    private String nombre;

    private String descripcion;
}
