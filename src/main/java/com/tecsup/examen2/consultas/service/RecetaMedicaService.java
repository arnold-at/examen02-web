package com.tecsup.examen2.consultas.service;

import org.springframework.stereotype.Service;
import com.tecsup.examen2.consultas.repository.RecetaMedicaRepository;
import com.tecsup.examen2.consultas.model.RecetaMedica;
import com.tecsup.examen2.consultas.model.DetalleReceta;
import java.util.*;

@Service
public class RecetaMedicaService {

    private final RecetaMedicaRepository recetaRepo;

    public RecetaMedicaService(RecetaMedicaRepository recetaRepo) {
        this.recetaRepo = recetaRepo;
    }

    public List<RecetaMedica> findAll() {
        return recetaRepo.findAll();
    }

    public Optional<RecetaMedica> findById(String id) {
        return recetaRepo.findById(id);
    }

    public RecetaMedica create(RecetaMedica receta) {
        if (receta.getDetalles() == null) {
            receta.setDetalles(new ArrayList<>());
        }
        return recetaRepo.save(receta);
    }

    public RecetaMedica update(String id, RecetaMedica updated) {
        return recetaRepo.findById(id).map(existing -> {
            existing.setIndicaciones(updated.getIndicaciones());
            return recetaRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Receta no encontrada"));
    }

    public void delete(String id) {
        recetaRepo.deleteById(id);
    }

    public Optional<RecetaMedica> findByConsulta(String idConsulta) {
        return recetaRepo.findByConsultaIdConsulta(idConsulta);
    }

    public RecetaMedica agregarDetalle(String idReceta, DetalleReceta detalle) {
        return recetaRepo.findById(idReceta).map(receta -> {
            if (receta.getDetalles() == null) {
                receta.setDetalles(new ArrayList<>());
            }
            receta.getDetalles().add(detalle);
            return recetaRepo.save(receta);
        }).orElseThrow(() -> new RuntimeException("Receta no encontrada"));
    }

    public RecetaMedica eliminarDetalle(String idReceta, int indiceDetalle) {
        return recetaRepo.findById(idReceta).map(receta -> {
            if (receta.getDetalles() != null &&
                    indiceDetalle >= 0 &&
                    indiceDetalle < receta.getDetalles().size()) {
                receta.getDetalles().remove(indiceDetalle);
                return recetaRepo.save(receta);
            }
            throw new RuntimeException("Índice de detalle inválido");
        }).orElseThrow(() -> new RuntimeException("Receta no encontrada"));
    }

    public List<DetalleReceta> getDetalles(String idReceta) {
        return recetaRepo.findById(idReceta)
                .map(RecetaMedica::getDetalles)
                .orElse(new ArrayList<>());
    }
}