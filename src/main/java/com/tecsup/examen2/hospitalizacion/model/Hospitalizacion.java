package com.tecsup.examen2.hospitalizacion.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import com.tecsup.examen2.Pacientes.model.Paciente;
import java.time.LocalDate;

@Entity
@Table(name = "hospitalizacion")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Hospitalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHosp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    @JsonIgnoreProperties({"citas", "consultas", "hospitalizaciones", "historiaClinica", "hibernateLazyInitializer", "handler"})
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_habitacion", nullable = false)
    @JsonIgnoreProperties({"hospitalizaciones", "hibernateLazyInitializer", "handler"})
    private Habitacion habitacion;

    private LocalDate fechaIngreso;
    private LocalDate fechaAlta;
    private String diagnosticoIngreso;
    private String estado;
}