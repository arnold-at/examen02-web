package com.tecsup.examen2.consultas.controller;

import org.springframework.web.bind.annotation.*;
import com.tecsup.examen2.consultas.service.DiagnosticoService;
import com.tecsup.examen2.consultas.model.Diagnostico;
import java.util.List;

@RestController
@RequestMapping("/api/diagnosticos")
@CrossOrigin(origins = "*")
public class DiagnosticoController {
    private final DiagnosticoService service;
    public DiagnosticoController(DiagnosticoService service) { this.service = service; }

    @GetMapping("/consulta/{idConsulta}")
    public List<Diagnostico> listarPorConsulta(@PathVariable Long idConsulta) { return service.findByConsulta(idConsulta); }

    @PostMapping
    public Diagnostico crear(@RequestBody Diagnostico d) { return service.save(d); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.delete(id); }
}
