package com.tecsup.examen2.Medicos.repository;

import com.tecsup.examen2.Medicos.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
