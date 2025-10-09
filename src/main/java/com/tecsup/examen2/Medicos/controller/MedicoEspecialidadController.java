package com.tecsup.examen2.Medicos.controller;

import com.tecsup.examen2.Medicos.service.MedicoEspecialidadService;
import com.tecsup.examen2.Medicos.model.MedicoEspecialidad;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/api/medico-especialidades")
@CrossOrigin(origins = "*")
public class MedicoEspecialidadController {

    private final MedicoEspecialidadService service;

    public MedicoEspecialidadController(MedicoEspecialidadService service) {
        this.service = service;
    }

    @PostMapping("/asignar")
    public ResponseEntity<MedicoEspecialidad> asignar(@RequestBody Map<String, Long> body) {
        Long idMedico = body.get("idMedico");
        Long idEspecialidad = body.get("idEspecialidad");
        return ResponseEntity.ok(service.asignarEspecialidad(idMedico, idEspecialidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}