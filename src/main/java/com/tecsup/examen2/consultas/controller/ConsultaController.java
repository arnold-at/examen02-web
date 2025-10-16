package com.tecsup.examen2.consultas.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.tecsup.examen2.consultas.service.ConsultaService;
import com.tecsup.examen2.consultas.model.Consulta;
import com.tecsup.examen2.consultas.model.Diagnostico;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> get(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada")));
    }

    @PostMapping
    public ResponseEntity<Consulta> create(@RequestBody Consulta c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> update(@PathVariable String id, @RequestBody Consulta c) {
        return ResponseEntity.ok(service.update(id, c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Consulta>> porPaciente(@PathVariable String idPaciente) {
        return ResponseEntity.ok(service.findByPaciente(idPaciente));
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<Consulta>> porMedico(@PathVariable String idMedico) {
        return ResponseEntity.ok(service.findByMedico(idMedico));
    }

    @GetMapping("/cita/{idCita}")
    public ResponseEntity<Consulta> porCita(@PathVariable String idCita) {
        return ResponseEntity.ok(service.findByCita(idCita)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada")));
    }

    @PostMapping("/{id}/diagnosticos")
    public ResponseEntity<Consulta> agregarDiagnostico(
            @PathVariable String id,
            @RequestBody Diagnostico diagnostico) {
        return ResponseEntity.ok(service.agregarDiagnostico(id, diagnostico));
    }

    @DeleteMapping("/{id}/diagnosticos/{indice}")
    public ResponseEntity<Consulta> eliminarDiagnostico(
            @PathVariable String id,
            @PathVariable int indice) {
        return ResponseEntity.ok(service.eliminarDiagnostico(id, indice));
    }

    @GetMapping("/{id}/diagnosticos")
    public ResponseEntity<List<Diagnostico>> getDiagnosticos(@PathVariable String id) {
        return ResponseEntity.ok(service.getDiagnosticos(id));
    }
}

