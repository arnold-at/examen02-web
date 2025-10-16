package com.tecsup.examen2.hospitalizacion.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.tecsup.examen2.hospitalizacion.service.HabitacionService;
import com.tecsup.examen2.hospitalizacion.model.Habitacion;
import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@CrossOrigin(origins = "*")
public class HabitacionController {

    private final HabitacionService service;

    public HabitacionController(HabitacionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Habitacion>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> get(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada")));
    }

    @PostMapping
    public ResponseEntity<Habitacion> create(@RequestBody Habitacion h) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(h));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> update(@PathVariable String id, @RequestBody Habitacion h) {
        return ResponseEntity.ok(service.update(id, h));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Habitacion>> porEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Habitacion>> porTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.findByTipo(tipo));
    }

    @GetMapping("/disponibles/tipo/{tipo}")
    public ResponseEntity<List<Habitacion>> disponiblesPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.findDisponiblesByTipo(tipo));
    }
}