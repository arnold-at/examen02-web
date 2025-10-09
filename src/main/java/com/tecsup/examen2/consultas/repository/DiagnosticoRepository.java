package com.tecsup.examen2.consultas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.examen2.consultas.model.Diagnostico;
import java.util.List;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
    List<Diagnostico> findByConsulta_IdConsulta(Long idConsulta);
}
