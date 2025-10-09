package com.tecsup.examen2.consultas.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tecsup.examen2.consultas.repository.DiagnosticoRepository;
import com.tecsup.examen2.consultas.model.Diagnostico;
import java.util.List;

@Service
@Transactional
public class DiagnosticoService {
    private final DiagnosticoRepository repo;
    public DiagnosticoService(DiagnosticoRepository repo) { this.repo = repo; }

    public List<Diagnostico> findByConsulta(Long idConsulta) {
        return repo.findByConsulta_IdConsulta(idConsulta);
    }

    public Diagnostico save(Diagnostico d) { return repo.save(d); }

    public void delete(Long id) { repo.deleteById(id); }
}

