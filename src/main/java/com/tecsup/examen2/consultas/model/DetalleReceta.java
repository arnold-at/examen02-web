package com.tecsup.examen2.consultas.model;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleReceta {

    private String medicamento;
    private String dosis;
    private String frecuencia;
    private String duracion;
}
