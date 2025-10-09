package com.tecsup.examen2.Pacientes.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "antecedente_medico")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AntecedenteMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAntecedente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHistoria")
    @JsonBackReference("historia-antecedentes")  // ✅ AHORA COINCIDE
    private HistoriaClinica historiaClinica;

    private String tipo; // alergias, enfermedades previas, cirugías

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
