package com.tecsup.examen2.Pacientes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.fasterxml.jackson.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "historias_clinicas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistoriaClinica {

    @Id
    private String idHistoria;

    @DBRef
    @JsonBackReference("paciente-historia")
    private Paciente paciente;

    private LocalDate fechaApertura;
    private String observaciones;

    @Builder.Default
    private List<AntecedenteMedico> antecedentes = new ArrayList<>();
}

