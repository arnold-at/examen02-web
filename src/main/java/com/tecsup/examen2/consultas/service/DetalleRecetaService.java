package com.tecsup.examen2.consultas.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tecsup.examen2.consultas.repository.DetalleRecetaRepository;
import com.tecsup.examen2.consultas.model.DetalleReceta;
import java.util.List;

@Service
@Transactional
public class DetalleRecetaService {
    private final DetalleRecetaRepository repo;
    public DetalleRecetaService(DetalleRecetaRepository repo) { this.repo = repo; }

    public List<DetalleReceta> findByReceta(Long idReceta) { return repo.findByRecetaMedica_IdReceta(idReceta); }

    public DetalleReceta save(DetalleReceta d) { return repo.save(d); }

    public void delete(Long id) { repo.deleteById(id); }
}
