package com.tecsup.examen2.Medicos.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicoEspecialidad {

    @DBRef
    @JsonIgnoreProperties({"medicos", "hibernateLazyInitializer", "handler"})
    private Especialidad especialidad;
}