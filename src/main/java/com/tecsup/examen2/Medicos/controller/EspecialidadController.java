package com.tecsup.examen2.Medicos.controller;

import com.tecsup.examen2.Medicos.model.Especialidad;
import com.tecsup.examen2.Medicos.service.EspecialidadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public List<Especialidad> listar() {
        return especialidadService.listarTodas();
    }

    @PostMapping
    public Especialidad crear(@RequestBody Especialidad especialidad) {
        return especialidadService.guardar(especialidad);
    }

    @GetMapping("/{id}")
    public Especialidad obtener(@PathVariable Long id) {
        return especialidadService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        especialidadService.eliminar(id);
    }
}
