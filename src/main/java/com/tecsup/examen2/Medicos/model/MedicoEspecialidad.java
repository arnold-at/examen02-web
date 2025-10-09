package com.tecsup.examen2.Medicos.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medico_especialidad")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicoEspecialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicoEsp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medico")
    @JsonIgnore
    private Medico medico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_especialidad")
    @JsonIgnoreProperties({"medicos", "hibernateLazyInitializer", "handler"})
    private Especialidad especialidad;
}