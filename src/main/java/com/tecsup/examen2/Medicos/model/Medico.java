package com.tecsup.examen2.Medicos.model;

import com.fasterxml.jackson.annotation.*;
import com.tecsup.examen2.citas.model.Cita;
import com.tecsup.examen2.consultas.model.Consulta;
import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "medico")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedico;

    private String nombres;
    private String apellidos;
    private String colegiatura;
    private String telefono;
    private String correo;
    private String estado;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("medico")
    private List<MedicoEspecialidad> especialidades = new ArrayList<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cita> citas;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Consulta> consultas;
}