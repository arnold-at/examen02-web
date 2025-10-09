package com.tecsup.examen2.Medicos.controller;

import com.tecsup.examen2.Medicos.model.Medico;
import com.tecsup.examen2.Medicos.service.MedicoService;
import org.springframework.web.bind.annotation.*;

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
    public List<Medico> listar() {
        return medicoService.listarTodos();
    }

    @PostMapping
    public Medico crear(@RequestBody Medico medico) {
        return medicoService.guardar(medico);
    }

    @GetMapping("/{id}")
    public Medico obtener(@PathVariable Long id) {
        return medicoService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Medico actualizar(@PathVariable Long id, @RequestBody Medico medico) {
        return medicoService.guardar(medico);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        medicoService.eliminar(id);
    }
}
