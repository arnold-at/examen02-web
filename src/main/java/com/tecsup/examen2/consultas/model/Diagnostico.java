package com.tecsup.examen2.consultas.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "diagnostico")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Diagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiagnostico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConsulta")
    @JsonIgnore
    private Consulta consulta;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String tipo;
}

