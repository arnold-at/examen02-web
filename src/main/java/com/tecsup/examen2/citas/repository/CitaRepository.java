package com.tecsup.examen2.citas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.examen2.citas.model.Cita;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPaciente_IdPaciente(Long idPaciente);
    List<Cita> findByMedico_IdMedico(Long idMedico);
}
