package com.tecsup.examen2.Pacientes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tecsup.examen2.Pacientes.repository.PacienteRepository;
import com.tecsup.examen2.Pacientes.repository.HistoriaClinicaRepository;
import com.tecsup.examen2.Pacientes.model.Paciente;
import com.tecsup.examen2.Pacientes.model.HistoriaClinica;
import java.time.LocalDate;
import java.util.*;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepo;
    private final HistoriaClinicaRepository historiaRepo;

    public PacienteService(PacienteRepository pacienteRepo,
                           HistoriaClinicaRepository historiaRepo) {
        this.pacienteRepo = pacienteRepo;
        this.historiaRepo = historiaRepo;
    }

    public List<Paciente> findAll() {
        return pacienteRepo.findAll();
    }

    public Optional<Paciente> findById(String id) {
        return pacienteRepo.findById(id);
    }

    public Paciente create(Paciente paciente) {
        if (pacienteRepo.existsByDni(paciente.getDni())) {
            throw new RuntimeException("Ya existe un paciente con el DNI: " + paciente.getDni());
        }

        if (paciente.getEstado() == null || paciente.getEstado().isEmpty()) {
            paciente.setEstado("ACTIVO");
        }

        Paciente nuevoPaciente = pacienteRepo.save(paciente);

        HistoriaClinica historia = HistoriaClinica.builder()
                .paciente(nuevoPaciente)
                .fechaApertura(LocalDate.now())
                .observaciones("Historia clínica creada automáticamente")
                .antecedentes(new ArrayList<>())
                .build();

        HistoriaClinica historiaGuardada = historiaRepo.save(historia);

        nuevoPaciente.setHistoriaClinica(historiaGuardada);
        return pacienteRepo.save(nuevoPaciente);
    }

    public Paciente update(String id, Paciente updated) {
        return pacienteRepo.findById(id).map(existing -> {
            existing.setNombres(updated.getNombres());
            existing.setApellidos(updated.getApellidos());
            existing.setDni(updated.getDni());
            existing.setTelefono(updated.getTelefono());
            existing.setCorreo(updated.getCorreo());
            existing.setDireccion(updated.getDireccion());
            existing.setFechaNacimiento(updated.getFechaNacimiento());
            existing.setSexo(updated.getSexo());
            existing.setEstado(updated.getEstado());
            return pacienteRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id));
    }

    public void delete(String id) {
        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        if (paciente.getHistoriaClinica() != null) {
            historiaRepo.deleteById(paciente.getHistoriaClinica().getIdHistoria());
        }

        pacienteRepo.deleteById(id);
    }

    public List<Paciente> buscar(String termino) {
        return pacienteRepo.buscarPorNombreApellidoODni(termino);
    }

    public List<Paciente> findByEstado(String estado) {
        return pacienteRepo.findByEstado(estado);
    }
}