package com.tecsup.examen2.citas.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.format.annotation.DateTimeFormat;
import com.tecsup.examen2.citas.service.CitaService;
import com.tecsup.examen2.citas.model.Cita;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Cita>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> get(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada")));
    }

    @PostMapping
    public ResponseEntity<Cita> create(@RequestBody Cita c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> update(@PathVariable String id, @RequestBody Cita c) {
        return ResponseEntity.ok(service.update(id, c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Cita>> porPaciente(@PathVariable String idPaciente) {
        return ResponseEntity.ok(service.findByPaciente(idPaciente));
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<Cita>> porMedico(@PathVariable String idMedico) {
        return ResponseEntity.ok(service.findByMedico(idMedico));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Cita>> porFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.findByFecha(fecha));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cita>> porEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @GetMapping("/paciente/{idPaciente}/pendientes")
    public ResponseEntity<List<Cita>> pendientesPorPaciente(@PathVariable String idPaciente) {
        return ResponseEntity.ok(service.findCitasPendientesByPaciente(idPaciente));
    }
}
