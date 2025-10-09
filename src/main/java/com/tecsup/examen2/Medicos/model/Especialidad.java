package com.tecsup.examen2.Medicos.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "especialidad")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecialidad;

    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicoEspecialidad> medicos = new ArrayList<>();

}

