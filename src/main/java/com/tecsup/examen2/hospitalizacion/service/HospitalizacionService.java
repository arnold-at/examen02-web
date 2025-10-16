package com.tecsup.examen2.hospitalizacion.service;

import org.springframework.stereotype.Service;
import com.tecsup.examen2.hospitalizacion.repository.HospitalizacionRepository;
import com.tecsup.examen2.hospitalizacion.repository.HabitacionRepository;
import com.tecsup.examen2.hospitalizacion.model.Hospitalizacion;
import com.tecsup.examen2.hospitalizacion.model.Habitacion;
import java.time.LocalDate;
import java.util.*;

@Service
public class HospitalizacionService {

    private final HospitalizacionRepository hospitalizacionRepo;
    private final HabitacionRepository habitacionRepo;

    public HospitalizacionService(HospitalizacionRepository hospitalizacionRepo,
                                  HabitacionRepository habitacionRepo) {
        this.hospitalizacionRepo = hospitalizacionRepo;
        this.habitacionRepo = habitacionRepo;
    }

    public List<Hospitalizacion> findAll() {
        return hospitalizacionRepo.findAll();
    }

    public Optional<Hospitalizacion> findById(String id) {
        return hospitalizacionRepo.findById(id);
    }

    public Hospitalizacion create(Hospitalizacion hospitalizacion) {
        if (hospitalizacion.getEstado() == null || hospitalizacion.getEstado().isEmpty()) {
            hospitalizacion.setEstado("EN_CURSO");
        }

        if (hospitalizacion.getHabitacion() != null) {
            habitacionRepo.findById(hospitalizacion.getHabitacion().getIdHabitacion())
                    .ifPresent(habitacion -> {
                        habitacion.setEstado("OCUPADA");
                        habitacionRepo.save(habitacion);
                    });
        }

        return hospitalizacionRepo.save(hospitalizacion);
    }

    public Hospitalizacion update(String id, Hospitalizacion updated) {
        return hospitalizacionRepo.findById(id).map(existing -> {
            existing.setFechaAlta(updated.getFechaAlta());
            existing.setDiagnosticoIngreso(updated.getDiagnosticoIngreso());
            existing.setEstado(updated.getEstado());

            if ("FINALIZADA".equals(updated.getEstado()) && existing.getHabitacion() != null) {
                habitacionRepo.findById(existing.getHabitacion().getIdHabitacion())
                        .ifPresent(habitacion -> {
                            habitacion.setEstado("DISPONIBLE");
                            habitacionRepo.save(habitacion);
                        });
            }

            return hospitalizacionRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Hospitalización no encontrada"));
    }

    public void delete(String id) {
        Hospitalizacion hosp = hospitalizacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospitalización no encontrada"));

        if (hosp.getHabitacion() != null) {
            habitacionRepo.findById(hosp.getHabitacion().getIdHabitacion())
                    .ifPresent(habitacion -> {
                        habitacion.setEstado("DISPONIBLE");
                        habitacionRepo.save(habitacion);
                    });
        }

        hospitalizacionRepo.deleteById(id);
    }


    public List<Hospitalizacion> findByPaciente(String idPaciente) {
        return hospitalizacionRepo.findByPacienteIdPaciente(idPaciente);
    }

    public List<Hospitalizacion> findByEstado(String estado) {
        return hospitalizacionRepo.findByEstado(estado);
    }

    public Optional<Hospitalizacion> findHospitalizacionActivaByPaciente(String idPaciente) {
        return hospitalizacionRepo.findHospitalizacionActivaByPaciente(idPaciente);
    }

    public List<Hospitalizacion> findByHabitacion(String idHabitacion) {
        return hospitalizacionRepo.findByHabitacionIdHabitacion(idHabitacion);
    }

    public List<Hospitalizacion> findByFechaIngresoBetween(LocalDate inicio, LocalDate fin) {
        return hospitalizacionRepo.findByFechaIngresoBetween(inicio, fin);
    }

    public Hospitalizacion darDeAlta(String id, LocalDate fechaAlta) {
        return hospitalizacionRepo.findById(id).map(hosp -> {
            hosp.setFechaAlta(fechaAlta);
            hosp.setEstado("FINALIZADA");

            if (hosp.getHabitacion() != null) {
                habitacionRepo.findById(hosp.getHabitacion().getIdHabitacion())
                        .ifPresent(habitacion -> {
                            habitacion.setEstado("DISPONIBLE");
                            habitacionRepo.save(habitacion);
                        });
            }

            return hospitalizacionRepo.save(hosp);
        }).orElseThrow(() -> new RuntimeException("Hospitalización no encontrada"));
    }
}