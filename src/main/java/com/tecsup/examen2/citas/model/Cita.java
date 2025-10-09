package com.tecsup.examen2.citas.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import com.tecsup.examen2.Pacientes.model.Paciente;
import com.tecsup.examen2.Medicos.model.Medico;
import com.tecsup.examen2.consultas.model.Consulta;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "cita")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    @JsonIgnoreProperties({"citas", "historiaClinica", "consultas", "hospitalizaciones", "hibernateLazyInitializer", "handler"})
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medico", nullable = false)
    @JsonIgnoreProperties({"citas", "especialidades", "hibernateLazyInitializer", "handler"})
    private Medico medico;

    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String estado;

    @OneToMany(mappedBy = "cita", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Consulta> consultas;
}