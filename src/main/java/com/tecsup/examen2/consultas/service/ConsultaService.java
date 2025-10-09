package com.tecsup.examen2.consultas.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tecsup.examen2.consultas.repository.ConsultaRepository;
import com.tecsup.examen2.consultas.model.Consulta;
import com.tecsup.examen2.Pacientes.repository.PacienteRepository;
import com.tecsup.examen2.Medicos.repository.MedicoRepository;
import com.tecsup.examen2.citas.repository.CitaRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConsultaService {
    private final ConsultaRepository repo;
    private final PacienteRepository pacienteRepo;
    private final MedicoRepository medicoRepo;
    private final CitaRepository citaRepo;

    public ConsultaService(ConsultaRepository repo,
                           PacienteRepository pacienteRepo,
                           MedicoRepository medicoRepo,
                           CitaRepository citaRepo) {
        this.repo = repo;
        this.pacienteRepo = pacienteRepo;
        this.medicoRepo = medicoRepo;
        this.citaRepo = citaRepo;
    }

    @Transactional(readOnly = true)
    public List<Consulta> findAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Consulta> findById(Long id) {
        Optional<Consulta> consulta = repo.findById(id);
        consulta.ifPresent(c -> {
            if (c.getDiagnosticos() != null) {
                c.getDiagnosticos().size();
            }
            if (c.getRecetas() != null) {
                c.getRecetas().size();
                c.getRecetas().forEach(r -> {
                    if (r.getDetalles() != null) {
                        r.getDetalles().size();
                    }
                });
            }
        });
        return consulta;
    }

    public Consulta create(Consulta c) {
        var paciente = pacienteRepo.findById(c.getPaciente().getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        var medico = medicoRepo.findById(c.getMedico().getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        c.setPaciente(paciente);
        c.setMedico(medico);

        if (c.getCita() != null && c.getCita().getIdCita() != null) {
            var cita = citaRepo.findById(c.getCita().getIdCita())
                    .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
            c.setCita(cita);
        }

        return repo.save(c);
    }

    public Consulta update(Long id, Consulta updated) {
        return repo.findById(id).map(existing -> {
            if (updated.getPaciente() != null && updated.getPaciente().getIdPaciente() != null) {
                var paciente = pacienteRepo.findById(updated.getPaciente().getIdPaciente())
                        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
                existing.setPaciente(paciente);
            }

            if (updated.getMedico() != null && updated.getMedico().getIdMedico() != null) {
                var medico = medicoRepo.findById(updated.getMedico().getIdMedico())
                        .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
                existing.setMedico(medico);
            }

            if (updated.getCita() != null && updated.getCita().getIdCita() != null) {
                var cita = citaRepo.findById(updated.getCita().getIdCita())
                        .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
                existing.setCita(cita);
            }

            existing.setFecha(updated.getFecha());
            existing.setHora(updated.getHora());
            existing.setMotivoConsulta(updated.getMotivoConsulta());
            existing.setObservaciones(updated.getObservaciones());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}