package com.tecsup.examen2.Pacientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.examen2.Pacientes.model.HistoriaClinica;

public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
}
