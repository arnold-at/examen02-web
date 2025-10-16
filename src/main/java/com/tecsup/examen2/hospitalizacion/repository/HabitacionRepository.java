package com.tecsup.examen2.hospitalizacion.repository;

import com.tecsup.examen2.hospitalizacion.model.Habitacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface HabitacionRepository extends MongoRepository<Habitacion, String> {

    Optional<Habitacion> findByNumero(String numero);

    List<Habitacion> findByEstado(String estado);

    List<Habitacion> findByTipo(String tipo);

    List<Habitacion> findByEstado_AndTipo(String estado, String tipo);
}