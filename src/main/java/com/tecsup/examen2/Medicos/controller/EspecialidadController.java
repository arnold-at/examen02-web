package com.tecsup.examen2.Medicos.controller;

import com.tecsup.examen2.Medicos.model.Especialidad;
import com.tecsup.examen2.Medicos.service.EspecialidadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public ResponseEntity<List<Especialidad>> listar() {
        return ResponseEntity.ok(especialidadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtener(@PathVariable String id) {
        return ResponseEntity.ok(especialidadService.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada")));
    }

    @PostMapping
    public ResponseEntity<Especialidad> crear(@RequestBody Especialidad especialidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadService.create(especialidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> actualizar(@PathVariable String id, @RequestBody Especialidad e) {
        return ResponseEntity.ok(especialidadService.update(id, e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        especialidadService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/{termino}")
    public ResponseEntity<List<Especialidad>> buscar(@PathVariable String termino) {
        return ResponseEntity.ok(especialidadService.buscar(termino));
    }
}