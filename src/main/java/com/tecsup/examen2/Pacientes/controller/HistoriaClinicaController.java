package com.tecsup.examen2.Pacientes.controller;

import org.springframework.web.bind.annotation.*;
import com.tecsup.examen2.Pacientes.service.HistoriaClinicaService;
import com.tecsup.examen2.Pacientes.model.HistoriaClinica;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/historias")
@CrossOrigin(origins = "*")
public class HistoriaClinicaController {

    private final HistoriaClinicaService service;

    public HistoriaClinicaController(HistoriaClinicaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public HistoriaClinica get(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));
    }

    @PostMapping
    public HistoriaClinica create(@RequestBody HistoriaClinica historia) {
        return service.save(historia);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
