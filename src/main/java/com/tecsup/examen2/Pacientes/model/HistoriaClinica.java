package com.tecsup.examen2.Pacientes.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "historia_clinica")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistoria;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    @JsonBackReference("paciente-historia")  // ⭐ IMPORTANTE
    private Paciente paciente;

    private LocalDate fechaApertura;
    private String observaciones;

    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("historia-antecedentes")
    private List<AntecedenteMedico> antecedentes;
}
