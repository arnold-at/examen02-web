package com.tecsup.examen2.consultas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.examen2.consultas.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> { }
