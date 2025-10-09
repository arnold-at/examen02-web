package com.tecsup.examen2.Pacientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.examen2.Pacientes.model.AntecedenteMedico;
import java.util.List;

public interface AntecedenteMedicoRepository extends JpaRepository<AntecedenteMedico, Long> {
    List<AntecedenteMedico> findAllByHistoriaClinica_IdHistoria(Long idHistoria);
}
