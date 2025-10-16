package com.tecsup.examen2.Pacientes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.tecsup.examen2.Pacientes.model.Paciente;
import java.util.Optional;
import java.util.List;

public interface PacienteRepository extends MongoRepository<Paciente, String> {

    boolean existsByDni(String dni);

    Optional<Paciente> findByDni(String dni);

    Optional<Paciente> findByCorreo(String correo);

    List<Paciente> findByEstado(String estado);

    List<Paciente> findByNombresContainingIgnoreCase(String nombres);

    List<Paciente> findByApellidosContainingIgnoreCase(String apellidos);

    @Query("{'$or': [" +
            "{'nombres': {$regex: ?0, $options: 'i'}}, " +
            "{'apellidos': {$regex: ?0, $options: 'i'}}, " +
            "{'dni': ?0}" +
            "]}")
    List<Paciente> buscarPorNombreApellidoODni(String busqueda);
}