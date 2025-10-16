package com.tecsup.examen2.hospitalizacion.repository;

import com.tecsup.examen2.hospitalizacion.model.Hospitalizacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HospitalizacionRepository extends MongoRepository<Hospitalizacion, String> {

    List<Hospitalizacion> findByPacienteIdPaciente(String idPaciente);

    List<Hospitalizacion> findByHabitacionIdHabitacion(String idHabitacion);

    List<Hospitalizacion> findByEstado(String estado);

    @Query("{'paciente.$id': ?0, 'estado': 'EN_CURSO'}")
    Optional<Hospitalizacion> findHospitalizacionActivaByPaciente(String idPaciente);

    @Query("{'fechaIngreso': {$gte: ?0, $lte: ?1}}")
    List<Hospitalizacion> findByFechaIngresoBetween(LocalDate inicio, LocalDate fin);

    @Query("{'habitacion.$id': ?0, 'estado': 'EN_CURSO'}")
    Optional<Hospitalizacion> findHospitalizacionActivaEnHabitacion(String idHabitacion);
}