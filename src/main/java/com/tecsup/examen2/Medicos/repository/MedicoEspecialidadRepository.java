package com.tecsup.examen2.Medicos.repository;

import com.tecsup.examen2.Medicos.model.MedicoEspecialidad;
import com.tecsup.examen2.Medicos.model.Medico;
import com.tecsup.examen2.Medicos.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoEspecialidadRepository extends JpaRepository<MedicoEspecialidad, Long> {
    boolean existsByMedicoAndEspecialidad(Medico medico, Especialidad especialidad);
}