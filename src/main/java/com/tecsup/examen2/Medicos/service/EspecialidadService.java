package com.tecsup.examen2.Medicos.service;

import com.tecsup.examen2.Medicos.model.Especialidad;
import com.tecsup.examen2.Medicos.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    public List<Especialidad> listarTodas() {
        return especialidadRepository.findAll();
    }

    public Especialidad guardar(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public Especialidad obtenerPorId(Long id) {
        return especialidadRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        especialidadRepository.deleteById(id);
    }
}
