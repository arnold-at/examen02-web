package com.tecsup.examen2.consultas.controller;

import org.springframework.web.bind.annotation.*;
import com.tecsup.examen2.consultas.model.DetalleReceta;
import com.tecsup.examen2.consultas.service.DetalleRecetaService;
import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@CrossOrigin(origins = "*")
public class DetalleRecetaController {

    private final DetalleRecetaService service;

    public DetalleRecetaController(DetalleRecetaService service) {
        this.service = service;
    }

    @GetMapping("/receta/{idReceta}")
    public List<DetalleReceta> listarPorReceta(@PathVariable Long idReceta) {
        return service.findByReceta(idReceta);
    }

    @PostMapping
    public DetalleReceta crear(@RequestBody DetalleReceta detalle) {
        return service.save(detalle);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.delete(id);
    }
}
