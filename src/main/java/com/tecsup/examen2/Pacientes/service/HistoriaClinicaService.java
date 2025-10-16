package com.tecsup.examen2.Pacientes.service;

import org.springframework.stereotype.Service;
import com.tecsup.examen2.Pacientes.repository.HistoriaClinicaRepository;
import com.tecsup.examen2.Pacientes.model.HistoriaClinica;
import com.tecsup.examen2.Pacientes.model.AntecedenteMedico;
import java.util.*;

@Service
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository historiaRepo;

    public HistoriaClinicaService(HistoriaClinicaRepository historiaRepo) {
        this.historiaRepo = historiaRepo;
    }

    public List<HistoriaClinica> findAll() {
        return historiaRepo.findAll();
    }

    public Optional<HistoriaClinica> findById(String id) {
        return historiaRepo.findById(id);
    }

    public Optional<HistoriaClinica> findByPacienteId(String idPaciente) {
        return historiaRepo.findByPacienteIdPaciente(idPaciente);
    }

    public HistoriaClinica update(String id, HistoriaClinica updated) {
        return historiaRepo.findById(id).map(existing -> {
            existing.setObservaciones(updated.getObservaciones());
            return historiaRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));
    }

    public HistoriaClinica agregarAntecedente(String idHistoria, AntecedenteMedico antecedente) {
        return historiaRepo.findById(idHistoria).map(historia -> {
            if (historia.getAntecedentes() == null) {
                historia.setAntecedentes(new ArrayList<>());
            }
            historia.getAntecedentes().add(antecedente);
            return historiaRepo.save(historia);
        }).orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));
    }

    public HistoriaClinica eliminarAntecedente(String idHistoria, int indiceAntecedente) {
        return historiaRepo.findById(idHistoria).map(historia -> {
            if (historia.getAntecedentes() != null &&
                    indiceAntecedente >= 0 &&
                    indiceAntecedente < historia.getAntecedentes().size()) {
                historia.getAntecedentes().remove(indiceAntecedente);
                return historiaRepo.save(historia);
            }
            throw new RuntimeException("Índice de antecedente inválido");
        }).orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));
    }

    public List<AntecedenteMedico> getAntecedentes(String idHistoria) {
        return historiaRepo.findById(idHistoria)
                .map(HistoriaClinica::getAntecedentes)
                .orElse(new ArrayList<>());
    }
}
