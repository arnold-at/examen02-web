package com.tecsup.examen2.hospitalizacion.controller;

import org.springframework.web.bind.annotation.*;
import com.tecsup.examen2.hospitalizacion.service.HospitalizacionService;
import com.tecsup.examen2.hospitalizacion.model.Hospitalizacion;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hospitalizaciones")
@CrossOrigin(origins = "*")
public class HospitalizacionController {
    private final HospitalizacionService service;
    public HospitalizacionController(HospitalizacionService service) { this.service = service; }

    @GetMapping
    public List<Hospitalizacion> list() { return service.findAll(); }

    @PostMapping("/internar")
    public Hospitalizacion internar(@RequestBody Map<String, Object> body) {
        Long idPaciente = Long.valueOf(body.get("idPaciente").toString());
        Long idHabitacion = Long.valueOf(body.get("idHabitacion").toString());
        String diagnostico = (String) body.get("diagnostico");
        return service.internar(idPaciente, idHabitacion, diagnostico);
    }

    @PostMapping("/{id}/alta")
    public Hospitalizacion alta(@PathVariable Long id) {
        return service.alta(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
