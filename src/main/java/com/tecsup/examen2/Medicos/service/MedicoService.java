package com.tecsup.examen2.Medicos.service;

import org.springframework.stereotype.Service;
import com.tecsup.examen2.Medicos.repository.MedicoRepository;
import com.tecsup.examen2.Medicos.repository.EspecialidadRepository;
import com.tecsup.examen2.Medicos.model.Medico;
import com.tecsup.examen2.Medicos.model.MedicoEspecialidad;
import com.tecsup.examen2.Medicos.model.Especialidad;
import java.util.*;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepo;
    private final EspecialidadRepository especialidadRepo;

    public MedicoService(MedicoRepository medicoRepo, EspecialidadRepository especialidadRepo) {
        this.medicoRepo = medicoRepo;
        this.especialidadRepo = especialidadRepo;
    }

    public List<Medico> findAll() {
        return medicoRepo.findAll();
    }

    public Optional<Medico> findById(String id) {
        return medicoRepo.findById(id);
    }

    public Medico create(Medico medico) {
        if (medico.getEstado() == null || medico.getEstado().isEmpty()) {
            medico.setEstado("ACTIVO");
        }

        if (medico.getEspecialidades() == null) {
            medico.setEspecialidades(new ArrayList<>());
        }

        return medicoRepo.save(medico);
    }

    public Medico update(String id, Medico updated) {
        return medicoRepo.findById(id).map(existing -> {
            existing.setNombres(updated.getNombres());
            existing.setApellidos(updated.getApellidos());
            existing.setColegiatura(updated.getColegiatura());
            existing.setTelefono(updated.getTelefono());
            existing.setCorreo(updated.getCorreo());
            existing.setEstado(updated.getEstado());
            return medicoRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Médico no encontrado"));
    }

    public void delete(String id) {
        medicoRepo.deleteById(id);
    }

    public List<Medico> buscar(String termino) {
        return medicoRepo.buscarPorNombreApellidoOColegiatura(termino);
    }

    public List<Medico> findByEstado(String estado) {
        return medicoRepo.findByEstado(estado);
    }

    public Medico agregarEspecialidad(String idMedico, String idEspecialidad) {
        Medico medico = medicoRepo.findById(idMedico)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        Especialidad especialidad = especialidadRepo.findById(idEspecialidad)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        boolean yaExiste = medico.getEspecialidades().stream()
                .anyMatch(me -> me.getEspecialidad().getIdEspecialidad().equals(idEspecialidad));

        if (yaExiste) {
            throw new RuntimeException("El médico ya tiene esta especialidad");
        }

        MedicoEspecialidad medicoEsp = MedicoEspecialidad.builder()
                .especialidad(especialidad)
                .build();

        if (medico.getEspecialidades() == null) {
            medico.setEspecialidades(new ArrayList<>());
        }

        medico.getEspecialidades().add(medicoEsp);
        return medicoRepo.save(medico);
    }

    public Medico eliminarEspecialidad(String idMedico, String idEspecialidad) {
        return medicoRepo.findById(idMedico).map(medico -> {
            if (medico.getEspecialidades() != null) {
                medico.getEspecialidades().removeIf(me ->
                        me.getEspecialidad().getIdEspecialidad().equals(idEspecialidad)
                );
                return medicoRepo.save(medico);
            }
            return medico;
        }).orElseThrow(() -> new RuntimeException("Médico no encontrado"));
    }

    public List<Medico> findByEspecialidad(String idEspecialidad) {
        return medicoRepo.findByEspecialidadId(idEspecialidad);
    }
}