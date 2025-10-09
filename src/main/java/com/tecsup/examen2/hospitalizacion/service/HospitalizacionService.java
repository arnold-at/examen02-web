package com.tecsup.examen2.hospitalizacion.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tecsup.examen2.hospitalizacion.repository.HospitalizacionRepository;
import com.tecsup.examen2.hospitalizacion.repository.HabitacionRepository;
import com.tecsup.examen2.hospitalizacion.model.Hospitalizacion;
import com.tecsup.examen2.hospitalizacion.model.Habitacion;
import com.tecsup.examen2.Pacientes.repository.PacienteRepository;
import com.tecsup.examen2.Pacientes.model.Paciente;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class HospitalizacionService {
    private final HospitalizacionRepository repo;
    private final HabitacionRepository habitRepo;
    private final PacienteRepository pacienteRepo;

    public HospitalizacionService(HospitalizacionRepository repo,
                                  HabitacionRepository habitRepo,
                                  PacienteRepository pacienteRepo) {
        this.repo = repo;
        this.habitRepo = habitRepo;
        this.pacienteRepo = pacienteRepo;
    }

    public List<Hospitalizacion> findAll() {
        return repo.findAll();
    }

    public Hospitalizacion internar(Long idPaciente, Long idHabitacion, String diagnostico) {
        Paciente paciente = pacienteRepo.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Habitacion habitacion = habitRepo.findById(idHabitacion)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        if (!"disponible".equalsIgnoreCase(habitacion.getEstado())) {
            throw new RuntimeException("Habitación no disponible");
        }

        habitacion.setEstado("ocupada");
        habitRepo.save(habitacion);

        Hospitalizacion h = new Hospitalizacion();
        h.setPaciente(paciente);
        h.setHabitacion(habitacion);
        h.setFechaIngreso(LocalDate.now());
        h.setDiagnosticoIngreso(diagnostico);
        h.setEstado("activo");

        return repo.save(h);
    }

    public Hospitalizacion alta(Long idHosp) {
        Hospitalizacion h = repo.findById(idHosp)
                .orElseThrow(() -> new RuntimeException("Hospitalización no encontrada"));
        h.setFechaAlta(LocalDate.now());
        h.setEstado("dado de alta");

        Habitacion habitacion = h.getHabitacion();
        habitacion.setEstado("disponible");
        habitRepo.save(habitacion);

        return repo.save(h);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}