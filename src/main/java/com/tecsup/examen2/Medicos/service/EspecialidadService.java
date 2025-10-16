package com.tecsup.examen2.Medicos.service;

import org.springframework.stereotype.Service;
import com.tecsup.examen2.Medicos.repository.EspecialidadRepository;
import com.tecsup.examen2.Medicos.model.Especialidad;
import java.util.*;

@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepo;

    public EspecialidadService(EspecialidadRepository especialidadRepo) {
        this.especialidadRepo = especialidadRepo;
    }

    public List<Especialidad> findAll() {
        return especialidadRepo.findAll();
    }

    public Optional<Especialidad> findById(String id) {
        return especialidadRepo.findById(id);
    }

    public Especialidad create(Especialidad especialidad) {
        return especialidadRepo.save(especialidad);
    }

    public Especialidad update(String id, Especialidad updated) {
        return especialidadRepo.findById(id).map(existing -> {
            existing.setNombre(updated.getNombre());
            existing.setDescripcion(updated.getDescripcion());
            return especialidadRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
    }

    public void delete(String id) {
        especialidadRepo.deleteById(id);
    }

    public List<Especialidad> buscar(String termino) {
        return especialidadRepo.findByNombreContainingIgnoreCase(termino);
    }
}
