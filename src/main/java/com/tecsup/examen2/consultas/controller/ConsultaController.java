package com.tecsup.examen2.consultas.controller;

import org.springframework.web.bind.annotation.*;
import com.tecsup.examen2.consultas.service.ConsultaService;
import com.tecsup.examen2.consultas.model.Consulta;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {
    private final ConsultaService service;
    public ConsultaController(ConsultaService service) { this.service = service; }

    @GetMapping
    public List<Consulta> list() { return service.findAll(); }

    @GetMapping("/{id}")
    public Consulta get(@PathVariable Long id) { return service.findById(id).orElseThrow(() -> new RuntimeException("Consulta no encontrada")); }

    @PostMapping
    public Consulta create(@RequestBody Consulta c) { return service.create(c); }

    @PutMapping("/{id}")
    public Consulta update(@PathVariable Long id, @RequestBody Consulta c) { return service.update(id, c); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
