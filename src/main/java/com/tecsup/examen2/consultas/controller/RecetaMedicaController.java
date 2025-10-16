package com.tecsup.examen2.consultas.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.tecsup.examen2.consultas.service.RecetaMedicaService;
import com.tecsup.examen2.consultas.model.RecetaMedica;
import com.tecsup.examen2.consultas.model.DetalleReceta;
import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class RecetaMedicaController {

    private final RecetaMedicaService service;

    public RecetaMedicaController(RecetaMedicaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RecetaMedica>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaMedica> get(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada")));
    }

    @PostMapping
    public ResponseEntity<RecetaMedica> crear(@RequestBody RecetaMedica r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaMedica> update(@PathVariable String id, @RequestBody RecetaMedica r) {
        return ResponseEntity.ok(service.update(id, r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consulta/{idConsulta}")
    public ResponseEntity<RecetaMedica> porConsulta(@PathVariable String idConsulta) {
        return ResponseEntity.ok(service.findByConsulta(idConsulta)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada")));
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<RecetaMedica> agregarDetalle(
            @PathVariable String id,
            @RequestBody DetalleReceta detalle) {
        return ResponseEntity.ok(service.agregarDetalle(id, detalle));
    }

    @DeleteMapping("/{id}/detalles/{indice}")
    public ResponseEntity<RecetaMedica> eliminarDetalle(
            @PathVariable String id,
            @PathVariable int indice) {
        return ResponseEntity.ok(service.eliminarDetalle(id, indice));
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<List<DetalleReceta>> getDetalles(@PathVariable String id) {
        return ResponseEntity.ok(service.getDetalles(id));
    }
}