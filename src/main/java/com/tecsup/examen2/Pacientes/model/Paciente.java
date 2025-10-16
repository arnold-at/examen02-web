package com.tecsup.examen2.Pacientes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
import com.fasterxml.jackson.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "pacientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Paciente {

    @Id
    private String idPaciente;

    @Indexed(unique = true)
    private String dni;

    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String sexo;
    private String direccion;
    private String telefono;

    @Indexed
    private String correo;

    private String estado;

    @DBRef
    @JsonManagedReference("paciente-historia")
    private HistoriaClinica historiaClinica;

}