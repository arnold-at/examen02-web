package com.tecsup.examen2.Medicos.repository;

import com.tecsup.examen2.Medicos.model.Medico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;
import java.util.List;

public interface MedicoRepository extends MongoRepository<Medico, String> {

    Optional<Medico> findByColegiatura(String colegiatura);

    List<Medico> findByEstado(String estado);

    List<Medico> findByNombresContainingIgnoreCase(String nombres);

    List<Medico> findByApellidosContainingIgnoreCase(String apellidos);

    @Query("{'especialidades.especialidad.$id': ?0}")
    List<Medico> findByEspecialidadId(String idEspecialidad);

    @Query("{'$or': [" +
            "{'nombres': {$regex: ?0, $options: 'i'}}, " +
            "{'apellidos': {$regex: ?0, $options: 'i'}}, " +
            "{'colegiatura': ?0}" +
            "]}")
    List<Medico> buscarPorNombreApellidoOColegiatura(String busqueda);
}