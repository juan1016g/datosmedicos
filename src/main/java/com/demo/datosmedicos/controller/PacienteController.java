package com.demo.datosmedicos.controller;

import com.demo.datosmedicos.entity.Paciente;
import com.demo.datosmedicos.repository.PacienteRepository;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "API para la gestión de pacientes")
public class PacienteController {

//    private final PacienteRepository pacienteRepository;

    @PostMapping
    @Operation(summary = "Crear un nuevo paciente", description = "Guarda un paciente utilizando su nombre y documento de identidad.")
    @ApiResponse(responseCode = "200", description = "Paciente creado exitosamente")
    @ApiResponse(responseCode = "409", description = "El documento de identidad ya está registrado")
    public ResponseEntity<?> crearPaciente(
            @Parameter(description = "Nombre completo del paciente") @RequestParam String nombre,
            @Parameter(description = "Documento de identidad único") @RequestParam String documentoIdentidad) {

        return ResponseEntity.ok("");
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pacientes", description = "Retorna una lista completa de los pacientes registrados.")
    public ResponseEntity<List<Paciente>> obtenerTodos() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = "Retorna la información de un paciente específico si existe.")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar información del paciente", description = "Modifica el nombre y documento de un paciente existente.")
    @ApiResponse(responseCode = "409", description = "El documento de identidad ya está registrado por otro paciente")
    public ResponseEntity<?> actualizarPaciente(
            @Parameter(description = "ID del paciente a modificar") @PathVariable Long id,
            @Parameter(description = "Nuevo nombre del paciente") @RequestParam String nombre,
            @Parameter(description = "Nuevo documento de identidad") @RequestParam String documentoIdentidad) {

        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{id}/nombre")
    @Operation(summary = "Actualizar solo el nombre del paciente", description = "Modifica únicamente el nombre de un paciente existente, sin afectar el resto de sus datos.")
    @ApiResponse(responseCode = "200", description = "Nombre actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    public ResponseEntity<Paciente> actualizarNombre(
            @Parameter(description = "ID del paciente a modificar") @PathVariable Long id,
            @Parameter(description = "Nuevo nombre del paciente") @RequestParam String nombre) {

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paciente", description = "Elimina permanentemente un paciente por su ID.")
    public ResponseEntity<Void> eliminarPaciente(
            @Parameter(description = "ID del paciente a eliminar") @PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}