package com.tecsup.examen2.hospitalizacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.examen2.hospitalizacion.model.Hospitalizacion;
import java.util.List;

public interface HospitalizacionRepository extends JpaRepository<Hospitalizacion, Long> {
    List<Hospitalizacion> findByEstado(String estado);
}
