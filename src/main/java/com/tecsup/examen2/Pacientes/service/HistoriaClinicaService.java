package com.tecsup.examen2.Pacientes.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tecsup.examen2.Pacientes.repository.HistoriaClinicaRepository;
import com.tecsup.examen2.Pacientes.repository.PacienteRepository;
import com.tecsup.examen2.Pacientes.model.HistoriaClinica;
import com.tecsup.examen2.Pacientes.model.Paciente;
import java.util.Optional;

@Service
@Transactional
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository repo;
    private final PacienteRepository pacienteRepo;

    public HistoriaClinicaService(HistoriaClinicaRepository repo,
                                  PacienteRepository pacienteRepo) {
        this.repo = repo;
        this.pacienteRepo = pacienteRepo;
    }

    public Optional<HistoriaClinica> findById(Long id) {
        return repo.findById(id);
    }

    public HistoriaClinica save(HistoriaClinica historia) {
        if (historia.getPaciente() != null && historia.getPaciente().getIdPaciente() != null) {
            Long idPaciente = historia.getPaciente().getIdPaciente();
            Paciente paciente = pacienteRepo.findById(idPaciente)
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

            paciente.setHistoriaClinica(historia);
            historia.setPaciente(paciente);
        } else {
            throw new RuntimeException("Se requiere un paciente válido para crear la historia clínica");
        }

        if (historia.getAntecedentes() != null) {
            historia.getAntecedentes().forEach(a -> a.setHistoriaClinica(historia));
        }

        return repo.save(historia);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}