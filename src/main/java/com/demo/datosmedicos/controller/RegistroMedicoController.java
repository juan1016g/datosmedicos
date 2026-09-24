package com.demo.datosmedicos.controller;

import com.demo.datosmedicos.entity.Paciente;
import com.demo.datosmedicos.entity.RegistroMedico;
import com.demo.datosmedicos.repository.RegistroMedicoRepository;
import com.demo.datosmedicos.repository.PacienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
@Tag(name = "Registros Médicos", description = "API para la administración de registros médicos")
public class RegistroMedicoController {
    private final RegistroMedicoRepository registroRepository;
    private final PacienteRepository pacienteRepository;

    @PostMapping
    @Operation(summary = "Crear registro médico", description = "Crea un registro para un paciente existente. El estado inicial siempre será ACTIVO.")
    public ResponseEntity<RegistroMedico> crearRegistro(
            @Parameter(description = "ID del paciente") @RequestParam Long pacienteId,
            @Parameter(description = "Fecha y hora en formato ISO-8601 (Ej: 2023-10-25T10:30:00)") @RequestParam String fechaHora) {

        var paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        var registro = RegistroMedico.builder()
                .paciente(paciente)
                .fechaHora(LocalDateTime.parse(fechaHora))
                .estado(RegistroMedico.EstadoRegistro.ACTIVO)
                .build();
        return ResponseEntity.ok(registroRepository.save(registro));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener registro por ID", description = "Retorna un registro médico específico, ignorando los que están eliminados lógicamente.")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<RegistroMedico> obtenerPorId(
            @Parameter(description = "ID del registro") @PathVariable Long id) {
        return registroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Obtener todos los Registros Medicos", description = "Retorna una lista completa de los Registro Medicos.")
    public ResponseEntity<List<RegistroMedico>> obtenerTodos() {
        List<RegistroMedico> registroMedicos = registroRepository.findAll();
        return ResponseEntity.ok(registroMedicos);
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado del registro", description = "Modifica el estado de un registro médico (ACTIVO, ARCHIVADO, CANCELADO).")
    public ResponseEntity<RegistroMedico> actualizarEstado(
            @Parameter(description = "ID del registro a actualizar") @PathVariable Long id,
            @Parameter(description = "Nuevo estado del registro") @RequestParam RegistroMedico.EstadoRegistro estado) {
        var registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
        registro.setEstado(estado);
        return ResponseEntity.ok(registroRepository.save(registro));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro médico", description = "Realiza una eliminación lógica (soft delete) del registro médico marcándolo como eliminado.")
    public ResponseEntity<Void> eliminarRegistro(
            @Parameter(description = "ID del registro a eliminar") @PathVariable Long id) {
        registroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}