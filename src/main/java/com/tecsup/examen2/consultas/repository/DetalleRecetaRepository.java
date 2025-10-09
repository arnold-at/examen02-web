package com.tecsup.examen2.consultas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.examen2.consultas.model.DetalleReceta;
import java.util.List;

public interface DetalleRecetaRepository extends JpaRepository<DetalleReceta, Long> {
    List<DetalleReceta> findByRecetaMedica_IdReceta(Long idReceta);
}
