package com.tecsup.examen2.consultas.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "recetas_medicas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecetaMedica {

    @Id
    private String idReceta;

    @DBRef
    @JsonIgnore
    private Consulta consulta;

    private String indicaciones;

    @Builder.Default
    @JsonIgnoreProperties("recetaMedica")
    private List<DetalleReceta> detalles = new ArrayList<>();
}