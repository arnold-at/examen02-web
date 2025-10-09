package com.tecsup.examen2.Medicos.service;

import com.tecsup.examen2.Medicos.model.Medico;
import com.tecsup.examen2.Medicos.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    public Medico guardar(Medico medico) {
        return medicoRepository.save(medico);
    }

    public Medico obtenerPorId(Long id) {
        return medicoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        medicoRepository.deleteById(id);
    }
}
