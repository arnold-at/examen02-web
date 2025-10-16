package com.tecsup.examen2.hospitalizacion.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.index.Indexed;
import com.fasterxml.jackson.annotation.*;
import com.tecsup.examen2.Pacientes.model.Paciente;
import java.time.LocalDate;

@Document(collection = "hospitalizaciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Hospitalizacion {

    @Id
    private String idHosp;

    @DBRef
    @JsonIgnoreProperties({"citas", "consultas", "hospitalizaciones", "historiaClinica"})
    private Paciente paciente;

    @DBRef
    @JsonIgnoreProperties({"hospitalizaciones"})
    private Habitacion habitacion;

    @Indexed
    private LocalDate fechaIngreso;

    private LocalDate fechaAlta;
    private String diagnosticoIngreso;

    @Indexed
    private String estado;
}