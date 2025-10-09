package com.tecsup.examen2.consultas.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import java.util.List;

@Entity
@Table(name = "receta_medica")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecetaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReceta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consulta", nullable = false)
    @JsonIgnore
    private Consulta consulta;

    private String indicaciones;

    @OneToMany(mappedBy = "recetaMedica", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("recetaMedica")
    private List<DetalleReceta> detalles;
}