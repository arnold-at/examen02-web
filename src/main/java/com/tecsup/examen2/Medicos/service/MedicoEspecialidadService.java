package com.tecsup.examen2.Medicos.service;

import com.tecsup.examen2.Medicos.model.*;
import com.tecsup.examen2.Medicos.repository.*;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MedicoEspecialidadService {

    private final MedicoEspecialidadRepository medicoEspecialidadRepo;
    private final MedicoRepository medicoRepo;
    private final EspecialidadRepository especialidadRepo;

    public MedicoEspecialidadService(MedicoEspecialidadRepository medicoEspecialidadRepo,
                                     MedicoRepository medicoRepo,
                                     EspecialidadRepository especialidadRepo) {
        this.medicoEspecialidadRepo = medicoEspecialidadRepo;
        this.medicoRepo = medicoRepo;
        this.especialidadRepo = especialidadRepo;
    }

    public MedicoEspecialidad asignarEspecialidad(Long idMedico, Long idEspecialidad) {
        Medico medico = medicoRepo.findById(idMedico)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        Especialidad especialidad = especialidadRepo.findById(idEspecialidad)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        boolean yaExiste = medicoEspecialidadRepo.existsByMedicoAndEspecialidad(medico, especialidad);
        if (yaExiste) {
            throw new RuntimeException("El médico ya tiene asignada esta especialidad");
        }

        MedicoEspecialidad me = new MedicoEspecialidad();
        me.setMedico(medico);
        me.setEspecialidad(especialidad);

        return medicoEspecialidadRepo.save(me);
    }

    public void eliminar(Long id) {
        medicoEspecialidadRepo.deleteById(id);
    }
}