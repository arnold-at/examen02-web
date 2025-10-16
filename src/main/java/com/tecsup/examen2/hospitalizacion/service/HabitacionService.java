package com.tecsup.examen2.hospitalizacion.service;

import org.springframework.stereotype.Service;
import com.tecsup.examen2.hospitalizacion.repository.HabitacionRepository;
import com.tecsup.examen2.hospitalizacion.model.Habitacion;
import java.util.*;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepo;

    public HabitacionService(HabitacionRepository habitacionRepo) {
        this.habitacionRepo = habitacionRepo;
    }

    public List<Habitacion> findAll() {
        return habitacionRepo.findAll();
    }

    public Optional<Habitacion> findById(String id) {
        return habitacionRepo.findById(id);
    }

    public Habitacion create(Habitacion habitacion) {
        if (habitacion.getEstado() == null || habitacion.getEstado().isEmpty()) {
            habitacion.setEstado("DISPONIBLE");
        }
        return habitacionRepo.save(habitacion);
    }

    public Habitacion update(String id, Habitacion updated) {
        return habitacionRepo.findById(id).map(existing -> {
            existing.setNumero(updated.getNumero());
            existing.setTipo(updated.getTipo());
            existing.setEstado(updated.getEstado());
            return habitacionRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    }

    public void delete(String id) {
        habitacionRepo.deleteById(id);
    }

    public List<Habitacion> findByEstado(String estado) {
        return habitacionRepo.findByEstado(estado);
    }

    public List<Habitacion> findByTipo(String tipo) {
        return habitacionRepo.findByTipo(tipo);
    }

    public List<Habitacion> findDisponiblesByTipo(String tipo) {
        return habitacionRepo.findByEstado_AndTipo("DISPONIBLE", tipo);
    }
}
