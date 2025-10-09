package com.tecsup.examen2.hospitalizacion.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tecsup.examen2.hospitalizacion.repository.HabitacionRepository;
import com.tecsup.examen2.hospitalizacion.model.Habitacion;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HabitacionService {
    private final HabitacionRepository repo;

    public HabitacionService(HabitacionRepository repo) {
        this.repo = repo;
    }

    public List<Habitacion> findAll() {
        return repo.findAll();
    }

    public Optional<Habitacion> findById(Long id) {
        return repo.findById(id);
    }

    public Habitacion create(Habitacion h) {
        if (h.getEstado() == null || h.getEstado().isEmpty()) {
            h.setEstado("disponible");
        }
        return repo.save(h);
    }

    public Habitacion update(Long id, Habitacion updated) {
        return repo.findById(id).map(existing -> {
            existing.setNumero(updated.getNumero());
            existing.setTipo(updated.getTipo());
            existing.setEstado(updated.getEstado());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}