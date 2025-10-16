package com.tecsup.examen2.consultas.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.index.Indexed;
import com.fasterxml.jackson.annotation.*;
import com.tecsup.examen2.Pacientes.model.Paciente;
import com.tecsup.examen2.Medicos.model.Medico;
import com.tecsup.examen2.citas.model.Cita;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "consultas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Consulta {

    @Id
    private String idConsulta;

    @DBRef
    @JsonIgnoreProperties({"consultas", "paciente", "medico"})
    private Cita cita;

    @DBRef
    @JsonIgnoreProperties({"citas", "consultas", "especialidades"})
    private Medico medico;

    @DBRef
    @JsonIgnoreProperties({"citas", "consultas", "hospitalizaciones", "historiaClinica"})
    private Paciente paciente;

    @Indexed
    private LocalDate fecha;

    private LocalTime hora;
    private String motivoConsulta;
    private String observaciones;

    @Builder.Default
    private List<Diagnostico> diagnosticos = new ArrayList<>();
}