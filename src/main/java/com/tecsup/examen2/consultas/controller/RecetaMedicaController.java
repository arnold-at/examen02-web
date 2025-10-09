package com.tecsup.examen2.consultas.controller;

import org.springframework.web.bind.annotation.*;
import com.tecsup.examen2.consultas.service.RecetaMedicaService;
import com.tecsup.examen2.consultas.model.RecetaMedica;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class RecetaMedicaController {
    private final RecetaMedicaService service;
    public RecetaMedicaController(RecetaMedicaService service) { this.service = service; }

    @PostMapping
    public RecetaMedica crear(@RequestBody RecetaMedica r) { return service.save(r); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.delete(id); }
}
