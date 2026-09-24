package com.demo.datosmedicos.controller;

import com.demo.datosmedicos.entity.RegistroMedico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros")
public class RegistroMedicoController {

    @PostMapping
    public ResponseEntity crearRegistro(@RequestParam Long pacienteId) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/{id}")
    public ResponseEntity obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity obtenerTodo() {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity actualizarEstado(@PathVariable Long id,
                                           @RequestParam RegistroMedico.EstadoRegistro estado) {

        return ResponseEntity.ok("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarRegistro(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}
