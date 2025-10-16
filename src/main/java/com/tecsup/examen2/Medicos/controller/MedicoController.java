package com.tecsup.examen2.Medicos.controller;

import com.tecsup.examen2.Medicos.model.Medico;
import com.tecsup.examen2.Medicos.service.MedicoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        return ResponseEntity.ok(medicoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtener(@PathVariable String id) {
        return ResponseEntity.ok(medicoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado")));
    }

    @PostMapping
    public ResponseEntity<Medico> crear(@RequestBody Medico medico) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.create(medico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(@PathVariable String id, @RequestBody Medico medico) {
        return ResponseEntity.ok(medicoService.update(id, medico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        medicoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/{termino}")
    public ResponseEntity<List<Medico>> buscar(@PathVariable String termino) {
        return ResponseEntity.ok(medicoService.buscar(termino));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Medico>> porEstado(@PathVariable String estado) {
        return ResponseEntity.ok(medicoService.findByEstado(estado));
    }

    @PostMapping("/{idMedico}/especialidades/{idEspecialidad}")
    public ResponseEntity<Medico> agregarEspecialidad(
            @PathVariable String idMedico,
            @PathVariable String idEspecialidad) {
        return ResponseEntity.ok(medicoService.agregarEspecialidad(idMedico, idEspecialidad));
    }

    @DeleteMapping("/{idMedico}/especialidades/{idEspecialidad}")
    public ResponseEntity<Medico> eliminarEspecialidad(
            @PathVariable String idMedico,
            @PathVariable String idEspecialidad) {
        return ResponseEntity.ok(medicoService.eliminarEspecialidad(idMedico, idEspecialidad));
    }

    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<List<Medico>> porEspecialidad(@PathVariable String idEspecialidad) {
        return ResponseEntity.ok(medicoService.findByEspecialidad(idEspecialidad));
    }
}
