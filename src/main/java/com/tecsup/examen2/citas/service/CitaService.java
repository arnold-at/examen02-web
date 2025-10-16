package com.tecsup.examen2.citas.service;

import org.springframework.stereotype.Service;
import com.tecsup.examen2.citas.repository.CitaRepository;
import com.tecsup.examen2.citas.model.Cita;
import com.tecsup.examen2.Pacientes.repository.PacienteRepository;
import com.tecsup.examen2.Medicos.repository.MedicoRepository;
import java.time.LocalDate;
import java.util.*;

@Service
public class CitaService {

    private final CitaRepository citaRepo;
    private final PacienteRepository pacienteRepo;
    private final MedicoRepository medicoRepo;

    public CitaService(CitaRepository citaRepo,
                       PacienteRepository pacienteRepo,
                       MedicoRepository medicoRepo) {
        this.citaRepo = citaRepo;
        this.pacienteRepo = pacienteRepo;
        this.medicoRepo = medicoRepo;
    }

    public List<Cita> findAll() {
        return citaRepo.findAll();
    }

    public Optional<Cita> findById(String id) {
        return citaRepo.findById(id);
    }

    public Cita create(Cita cita) {
        if (cita.getEstado() == null || cita.getEstado().isEmpty()) {
            cita.setEstado("PENDIENTE");
        }

        if (cita.getPaciente() != null && cita.getPaciente().getIdPaciente() != null) {
            pacienteRepo.findById(cita.getPaciente().getIdPaciente())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        }

        if (cita.getMedico() != null && cita.getMedico().getIdMedico() != null) {
            medicoRepo.findById(cita.getMedico().getIdMedico())
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        }

        return citaRepo.save(cita);
    }

    public Cita update(String id, Cita updated) {
        return citaRepo.findById(id).map(existing -> {
            existing.setFecha(updated.getFecha());
            existing.setHora(updated.getHora());
            existing.setMotivo(updated.getMotivo());
            existing.setEstado(updated.getEstado());
            return citaRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    public void delete(String id) {
        citaRepo.deleteById(id);
    }

    public List<Cita> findByPaciente(String idPaciente) {
        return citaRepo.findByPacienteIdPaciente(idPaciente);
    }

    public List<Cita> findByMedico(String idMedico) {
        return citaRepo.findByMedicoIdMedico(idMedico);
    }

    public List<Cita> findByFecha(LocalDate fecha) {
        return citaRepo.findByFecha(fecha);
    }

    public List<Cita> findByEstado(String estado) {
        return citaRepo.findByEstado(estado);
    }

    public List<Cita> findCitasPendientesByPaciente(String idPaciente) {
        return citaRepo.findCitasPendientesByPaciente(idPaciente);
    }
}
