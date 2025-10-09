package com.tecsup.examen2.hospitalizacion.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import com.tecsup.examen2.hospitalizacion.model.Hospitalizacion;
import java.util.List;

@Entity
@Table(name = "habitacion")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHabitacion;

    private String numero;
    private String tipo;
    private String estado;

    @OneToMany(mappedBy = "habitacion", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Hospitalizacion> hospitalizaciones;
}