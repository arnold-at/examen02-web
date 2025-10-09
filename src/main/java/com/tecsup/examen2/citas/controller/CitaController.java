package com.tecsup.examen2.citas.controller;

import org.springframework.web.bind.annotation.*;
import com.tecsup.examen2.citas.service.CitaService;
import com.tecsup.examen2.citas.model.Cita;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService service;
    public CitaController(CitaService service) { this.service = service; }

    @GetMapping
    public List<Cita> list() { return service.findAll(); }

    @GetMapping("/{id}")
    public Cita get(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    @PostMapping
    public Cita create(@RequestBody Cita c) { return service.create(c); }

    @PutMapping("/{id}")
    public Cita update(@PathVariable Long id, @RequestBody Cita c) { return service.update(id, c); }

    @PutMapping("/{id}/reprogramar")
    public Cita reprogramar(@PathVariable Long id, @RequestBody Cita c) { return service.reprogramar(id, c); }

    @PutMapping("/{id}/cancelar")
    public void cancelar(@PathVariable Long id) { service.cancelar(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}