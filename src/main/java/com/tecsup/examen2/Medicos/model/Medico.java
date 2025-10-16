package com.tecsup.examen2.Medicos.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.*;

@Document(collection = "medicos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Medico {

    @Id
    private String idMedico;

    private String nombres;
    private String apellidos;

    @Indexed(unique = true)
    private String colegiatura;

    private String telefono;

    @Indexed
    private String correo;

    private String estado;

    @Builder.Default
    private List<MedicoEspecialidad> especialidades = new ArrayList<>();
}