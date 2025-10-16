package com.tecsup.examen2.Medicos.repository;

import com.tecsup.examen2.Medicos.model.Especialidad;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface EspecialidadRepository extends MongoRepository<Especialidad, String> {

    Optional<Especialidad> findByNombre(String nombre);

    List<Especialidad> findByNombreContainingIgnoreCase(String nombre);
}