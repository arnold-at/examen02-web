package com.tecsup.examen2.Pacientes.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.tecsup.examen2.Pacientes.service.PacienteService;
import com.tecsup.examen2.Pacientes.model.Paciente;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PacienteController {
    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado")));
    }

    @PostMapping
    public ResponseEntity<Paciente> create(@RequestBody Paciente p) {
        System.out.println("Recibiendo paciente: " + p);
        Paciente nuevo = service.create(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> update(@PathVariable Long id, @RequestBody Paciente p) {
        return ResponseEntity.ok(service.update(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}