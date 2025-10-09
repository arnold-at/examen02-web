package com.tecsup.examen2.hospitalizacion.controller;

import org.springframework.web.bind.annotation.*;
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
    public List<Habitacion> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Habitacion get(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    }

    @PostMapping
    public Habitacion create(@RequestBody Habitacion h) {
        return service.create(h);
    }

    @PutMapping("/{id}")
    public Habitacion update(@PathVariable Long id, @RequestBody Habitacion h) {
        return service.update(id, h);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
