package com.tecsup.examen2.Pacientes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.tecsup.examen2.Pacientes.model.HistoriaClinica;
import java.util.Optional;

public interface HistoriaClinicaRepository extends MongoRepository<HistoriaClinica, String> {

    Optional<HistoriaClinica> findByPacienteIdPaciente(String idPaciente);
}