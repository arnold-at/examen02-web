package com.tecsup.examen2.Pacientes.controller;

import org.springframework.web.bind.annotation.*;
import com.tecsup.examen2.Pacientes.service.AntecedenteMedicoService;
import com.tecsup.examen2.Pacientes.model.AntecedenteMedico;
import java.util.List;

@RestController
@RequestMapping("/api/antecedentes")
@CrossOrigin(origins = "*")
public class AntecedenteMedicoController {

    private final AntecedenteMedicoService service;

    public AntecedenteMedicoController(AntecedenteMedicoService service) {
        this.service = service;
    }

    @GetMapping("/historia/{idHistoria}")
    public List<AntecedenteMedico> listarPorHistoria(@PathVariable Long idHistoria) {
        return service.findByHistoria(idHistoria);
    }

    @PostMapping
    public AntecedenteMedico create(@RequestBody AntecedenteMedico antecedente) {
        return service.save(antecedente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
