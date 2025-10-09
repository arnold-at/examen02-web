package com.tecsup.examen2.consultas.model;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "detalle_receta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleReceta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receta", nullable = false)  // ⭐ Cambiado nombre de columna
    @JsonBackReference("receta-detalles")  // ⭐ NUEVO: Evita ciclos JSON
    private RecetaMedica recetaMedica;

    private String medicamento;
    private String dosis;
    private String frecuencia;
    private String duracion;
}

