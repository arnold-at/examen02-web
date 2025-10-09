package com.tecsup.examen2.hospitalizacion.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.examen2.hospitalizacion.model.Habitacion;
import java.util.List;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByEstado(String estado);
}

