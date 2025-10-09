package com.tecsup.examen2.Medicos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medico_especialidad")
public class MedicoEspecialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicoEsp;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;

    public Long getIdMedicoEsp() {
        return idMedicoEsp;
    }

    public void setIdMedicoEsp(Long idMedicoEsp) {
        this.idMedicoEsp = idMedicoEsp;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
}

