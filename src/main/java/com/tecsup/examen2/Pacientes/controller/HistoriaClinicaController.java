package com.tecsup.examen2.Pacientes.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tecsup.examen2.Pacientes.service.HistoriaClinicaService;
import com.tecsup.examen2.Pacientes.model.HistoriaClinica;
import com.tecsup.examen2.Pacientes.model.AntecedenteMedico;
import java.util.List;

@RestController
@RequestMapping("/api/historias")
@CrossOrigin(origins = "*")
public class HistoriaClinicaController {

    private final HistoriaClinicaService service;

    public HistoriaClinicaController(HistoriaClinicaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HistoriaClinica>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinica> get(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id)
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada")));
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<HistoriaClinica> getByPaciente(@PathVariable String idPaciente) {
        return ResponseEntity.ok(service.findByPacienteId(idPaciente)
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinica> update(@PathVariable String id, @RequestBody HistoriaClinica h) {
        return ResponseEntity.ok(service.update(id, h));
    }

    @PostMapping("/{id}/antecedentes")
    public ResponseEntity<HistoriaClinica> agregarAntecedente(
            @PathVariable String id,
            @RequestBody AntecedenteMedico antecedente) {
        return ResponseEntity.ok(service.agregarAntecedente(id, antecedente));
    }

    @DeleteMapping("/{id}/antecedentes/{indice}")
    public ResponseEntity<HistoriaClinica> eliminarAntecedente(
            @PathVariable String id,
            @PathVariable int indice) {
        return ResponseEntity.ok(service.eliminarAntecedente(id, indice));
    }

    @GetMapping("/{id}/antecedentes")
    public ResponseEntity<List<AntecedenteMedico>> getAntecedentes(@PathVariable String id) {
        return ResponseEntity.ok(service.getAntecedentes(id));
    }
}