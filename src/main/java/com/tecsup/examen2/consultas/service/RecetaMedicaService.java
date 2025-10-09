package com.tecsup.examen2.consultas.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tecsup.examen2.consultas.repository.RecetaMedicaRepository;
import com.tecsup.examen2.consultas.model.RecetaMedica;

@Service
@Transactional
public class RecetaMedicaService {
    private final RecetaMedicaRepository repo;
    public RecetaMedicaService(RecetaMedicaRepository repo) { this.repo = repo; }

    public RecetaMedica save(RecetaMedica r) {
        if (r.getDetalles() != null) {
            r.getDetalles().forEach(d -> d.setRecetaMedica(r));
        }
        return repo.save(r);
    }

    public void delete(Long id) { repo.deleteById(id); }
}
