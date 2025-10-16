package com.tecsup.examen2.citas.repository;

import com.tecsup.examen2.citas.model.Cita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends MongoRepository<Cita, String> {

    List<Cita> findByPacienteIdPaciente(String idPaciente);

    List<Cita> findByMedicoIdMedico(String idMedico);

    List<Cita> findByFecha(LocalDate fecha);

    List<Cita> findByEstado(String estado);

    List<Cita> findByPacienteIdPacienteAndEstado(String idPaciente, String estado);

    @Query("{'medico.$id': ?0, 'fecha': ?1}")
    List<Cita> findByMedicoAndFecha(String idMedico, LocalDate fecha);

    @Query("{'fecha': {$gte: ?0, $lte: ?1}}")
    List<Cita> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("{'paciente.$id': ?0, 'estado': 'PENDIENTE'}")
    List<Cita> findCitasPendientesByPaciente(String idPaciente);
}
