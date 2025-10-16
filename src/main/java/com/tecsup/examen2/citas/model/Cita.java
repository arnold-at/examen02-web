package com.tecsup.examen2.citas.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.index.Indexed;
import com.fasterxml.jackson.annotation.*;
import com.tecsup.examen2.Pacientes.model.Paciente;
import com.tecsup.examen2.Medicos.model.Medico;
import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "citas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cita {

    @Id
    private String idCita;

    @DBRef
    @JsonIgnoreProperties({"citas", "historiaClinica", "consultas", "hospitalizaciones"})
    private Paciente paciente;

    @DBRef
    @JsonIgnoreProperties({"citas", "especialidades"})
    private Medico medico;

    @Indexed
    private LocalDate fecha;

    private LocalTime hora;
    private String motivo;

    @Indexed
    private String estado;
}