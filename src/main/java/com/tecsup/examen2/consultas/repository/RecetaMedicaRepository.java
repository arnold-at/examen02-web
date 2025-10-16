package com.tecsup.examen2.consultas.repository;

import com.tecsup.examen2.consultas.model.RecetaMedica;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface RecetaMedicaRepository extends MongoRepository<RecetaMedica, String> {

    Optional<RecetaMedica> findByConsultaIdConsulta(String idConsulta);

    @Query("{'consulta.paciente.$id': ?0}")
    List<RecetaMedica> findByPacienteId(String idPaciente);

    @Query("{'consulta.medico.$id': ?0}")
    List<RecetaMedica> findByMedicoId(String idMedico);
}