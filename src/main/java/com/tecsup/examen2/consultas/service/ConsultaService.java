package com.tecsup.examen2.consultas.service;

import org.springframework.stereotype.Service;
import com.tecsup.examen2.consultas.repository.ConsultaRepository;
import com.tecsup.examen2.consultas.model.Consulta;
import com.tecsup.examen2.consultas.model.Diagnostico;
import java.time.LocalDate;
import java.util.*;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepo;

    public ConsultaService(ConsultaRepository consultaRepo) {
        this.consultaRepo = consultaRepo;
    }

    public List<Consulta> findAll() {
        return consultaRepo.findAll();
    }

    public Optional<Consulta> findById(String id) {
        return consultaRepo.findById(id);
    }

    public Consulta create(Consulta consulta) {
        if (consulta.getDiagnosticos() == null) {
            consulta.setDiagnosticos(new ArrayList<>());
        }
        return consultaRepo.save(consulta);
    }

    public Consulta update(String id, Consulta updated) {
        return consultaRepo.findById(id).map(existing -> {
            existing.setFecha(updated.getFecha());
            existing.setHora(updated.getHora());
            existing.setMotivoConsulta(updated.getMotivoConsulta());
            existing.setObservaciones(updated.getObservaciones());
            return consultaRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
    }

    public void delete(String id) {
        consultaRepo.deleteById(id);
    }

    public List<Consulta> findByPaciente(String idPaciente) {
        return consultaRepo.findByPacienteIdPaciente(idPaciente);
    }

    public List<Consulta> findByMedico(String idMedico) {
        return consultaRepo.findByMedicoIdMedico(idMedico);
    }

    public Optional<Consulta> findByCita(String idCita) {
        return consultaRepo.findByCitaIdCita(idCita);
    }

    public Consulta agregarDiagnostico(String idConsulta, Diagnostico diagnostico) {
        return consultaRepo.findById(idConsulta).map(consulta -> {
            if (consulta.getDiagnosticos() == null) {
                consulta.setDiagnosticos(new ArrayList<>());
            }
            consulta.getDiagnosticos().add(diagnostico);
            return consultaRepo.save(consulta);
        }).orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
    }

    public Consulta eliminarDiagnostico(String idConsulta, int indiceDiagnostico) {
        return consultaRepo.findById(idConsulta).map(consulta -> {
            if (consulta.getDiagnosticos() != null &&
                    indiceDiagnostico >= 0 &&
                    indiceDiagnostico < consulta.getDiagnosticos().size()) {
                consulta.getDiagnosticos().remove(indiceDiagnostico);
                return consultaRepo.save(consulta);
            }
            throw new RuntimeException("Índice de diagnóstico inválido");
        }).orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
    }

    public List<Diagnostico> getDiagnosticos(String idConsulta) {
        return consultaRepo.findById(idConsulta)
                .map(Consulta::getDiagnosticos)
                .orElse(new ArrayList<>());
    }
}
