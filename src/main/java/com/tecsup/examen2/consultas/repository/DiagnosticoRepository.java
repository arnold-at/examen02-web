package com.tecsup.examen2.consultas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tecsup.examen2.consultas.model.Diagnostico;
import java.util.List;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {

    @Query("SELECT d FROM Diagnostico d WHERE d.consulta.idConsulta = :idConsulta")
    List<Diagnostico> findByConsulta_IdConsulta(@Param("idConsulta") Long idConsulta);
}