package com.tecsup.examen2.citas.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tecsup.examen2.citas.model.Cita;
import com.tecsup.examen2.citas.repository.CitaRepository;
import com.tecsup.examen2.Pacientes.repository.PacienteRepository;
import com.tecsup.examen2.Medicos.repository.MedicoRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CitaService(CitaRepository citaRepository,
                       PacienteRepository pacienteRepository,
                       MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    public Optional<Cita> findById(Long id) {
        return citaRepository.findById(id);
    }

    public Cita create(Cita cita) {
        var paciente = pacienteRepository.findById(cita.getPaciente().getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        var medico = medicoRepository.findById(cita.getMedico().getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        cita.setPaciente(paciente);
        cita.setMedico(medico);

        if (cita.getEstado() == null || cita.getEstado().isEmpty()) {
            cita.setEstado("programada");
        }

        return citaRepository.save(cita);
    }

    public Cita update(Long id, Cita citaActualizada) {
        Cita citaExistente = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (citaActualizada.getPaciente() != null &&
                citaActualizada.getPaciente().getIdPaciente() != null) {
            var paciente = pacienteRepository.findById(citaActualizada.getPaciente().getIdPaciente())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            citaExistente.setPaciente(paciente);
        }

        if (citaActualizada.getMedico() != null &&
                citaActualizada.getMedico().getIdMedico() != null) {
            var medico = medicoRepository.findById(citaActualizada.getMedico().getIdMedico())
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
            citaExistente.setMedico(medico);
        }

        if (citaActualizada.getFecha() != null) {
            citaExistente.setFecha(citaActualizada.getFecha());
        }
        if (citaActualizada.getHora() != null) {
            citaExistente.setHora(citaActualizada.getHora());
        }
        if (citaActualizada.getMotivo() != null) {
            citaExistente.setMotivo(citaActualizada.getMotivo());
        }
        if (citaActualizada.getEstado() != null) {
            citaExistente.setEstado(citaActualizada.getEstado());
        }

        return citaRepository.save(citaExistente);
    }

    public Cita reprogramar(Long id, Cita nuevaFechaHora) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setFecha(nuevaFechaHora.getFecha());
        cita.setHora(nuevaFechaHora.getHora());
        cita.setEstado("reprogramada");

        return citaRepository.save(cita);
    }

    public void cancelar(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setEstado("cancelada");
        citaRepository.save(cita);
    }

    public void delete(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada");
        }
        citaRepository.deleteById(id);
    }
}
