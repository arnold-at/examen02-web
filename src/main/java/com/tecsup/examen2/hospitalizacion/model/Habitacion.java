package com.tecsup.examen2.hospitalizacion.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import com.fasterxml.jackson.annotation.*;

@Document(collection = "habitaciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Habitacion {

    @Id
    private String idHabitacion;

    @Indexed(unique = true)
    private String numero;

    private String tipo;

    @Indexed
    private String estado;
}