package com.tecsup.examen2.Pacientes.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import com.tecsup.examen2.citas.model.Cita;
import com.tecsup.examen2.consultas.model.Consulta;
import com.tecsup.examen2.hospitalizacion.model.Hospitalizacion;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "paciente")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;

    private String dni;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String sexo;
    private String direccion;
    private String telefono;
    private String correo;
    private String estado;

    @OneToOne(mappedBy = "paciente", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("paciente-historia")
    private HistoriaClinica historiaClinica;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cita> citas;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Hospitalizacion> hospitalizaciones;
}