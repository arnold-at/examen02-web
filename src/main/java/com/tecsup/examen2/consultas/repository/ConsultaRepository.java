package com.tecsup.examen2.consultas.repository;

import com.tecsup.examen2.consultas.model.Consulta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepository extends MongoRepository<Consulta, String> {

    List<Consulta> findByPacienteIdPaciente(String idPaciente);

    List<Consulta> findByMedicoIdMedico(String idMedico);

    Optional<Consulta> findByCitaIdCita(String idCita);

    List<Consulta> findByFecha(LocalDate fecha);

    @Query("{'fecha': {$gte: ?0, $lte: ?1}}")
    List<Consulta> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("{'paciente.$id': ?0, 'fecha': {$gte: ?1, $lte: ?2}}")
    List<Consulta> findByPacienteAndFechaBetween(String idPaciente, LocalDate inicio, LocalDate fin);
}