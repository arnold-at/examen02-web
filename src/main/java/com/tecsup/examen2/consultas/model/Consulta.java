package com.tecsup.examen2.consultas.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import com.tecsup.examen2.Pacientes.model.Paciente;
import com.tecsup.examen2.Medicos.model.Medico;
import com.tecsup.examen2.citas.model.Cita;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "consulta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsulta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cita")
    @JsonIgnoreProperties({"consultas", "paciente", "medico", "hibernateLazyInitializer", "handler"})
    private Cita cita;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medico", nullable = false)
    @JsonIgnoreProperties({"citas", "consultas", "especialidades", "hibernateLazyInitializer", "handler"})
    private Medico medico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    @JsonIgnoreProperties({"citas", "consultas", "hospitalizaciones", "historiaClinica", "hibernateLazyInitializer", "handler"})
    private Paciente paciente;

    private LocalDate fecha;
    private LocalTime hora;
    private String motivoConsulta;
    private String observaciones;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("consulta")
    private List<Diagnostico> diagnosticos;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("consulta")
    private List<RecetaMedica> recetas;
}