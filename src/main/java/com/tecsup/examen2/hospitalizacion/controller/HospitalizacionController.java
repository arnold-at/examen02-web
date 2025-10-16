package com.tecsup.examen2.hospitalizacion.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.format.annotation.DateTimeFormat;
import com.tecsup.examen2.hospitalizacion.service.HospitalizacionService;
import com.tecsup.examen2.hospitalizacion.model.Hospitalizacion;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hospitalizaciones")
@CrossOrigin(origins = "*")
public class HospitalizacionController {

    private final HospitalizacionService service;

    public HospitalizacionController(HospitalizacionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Hospitalizacion>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospitalizacion> get(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospitalización no encontrada")));
    }

    @PostMapping
    public ResponseEntity<Hospitalizacion> create(@RequestBody Hospitalizacion h) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(h));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospitalizacion> update(@PathVariable String id, @RequestBody Hospitalizacion h) {
        return ResponseEntity.ok(service.update(id, h));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Hospitalizacion>> porPaciente(@PathVariable String idPaciente) {
        return ResponseEntity.ok(service.findByPaciente(idPaciente));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Hospitalizacion>> porEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @GetMapping("/paciente/{idPaciente}/activa")
    public ResponseEntity<Hospitalizacion> activaPorPaciente(@PathVariable String idPaciente) {
        return ResponseEntity.ok(service.findHospitalizacionActivaByPaciente(idPaciente)
                .orElseThrow(() -> new RuntimeException("No hay hospitalización activa")));
    }

    @PostMapping("/{id}/alta")
    public ResponseEntity<Hospitalizacion> darDeAlta(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAlta) {
        return ResponseEntity.ok(service.darDeAlta(id, fechaAlta));
    }
}
