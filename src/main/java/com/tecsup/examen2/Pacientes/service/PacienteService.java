package com.tecsup.examen2.Pacientes.service;

import org.springframework.stereotype.Service;
import com.tecsup.examen2.Pacientes.repository.PacienteRepository;
import com.tecsup.examen2.Pacientes.model.Paciente;
import java.util.*;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PacienteService {
    private final PacienteRepository repo;
    public PacienteService(PacienteRepository repo) { this.repo = repo; }

    public List<Paciente> findAll() { return repo.findAll(); }

    public Optional<Paciente> findById(Long id) { return repo.findById(id); }

    public Paciente create(Paciente p) {
        return repo.save(p);
    }

    public Paciente update(Long id, Paciente updated) {
        return repo.findById(id).map(existing -> {
            existing.setNombres(updated.getNombres());
            existing.setApellidos(updated.getApellidos());
            existing.setDni(updated.getDni());
            existing.setTelefono(updated.getTelefono());
            existing.setCorreo(updated.getCorreo());
            existing.setDireccion(updated.getDireccion());
            existing.setFechaNacimiento(updated.getFechaNacimiento());
            existing.setSexo(updated.getSexo());
            existing.setEstado(updated.getEstado());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    public void delete(Long id) { repo.deleteById(id); }
}