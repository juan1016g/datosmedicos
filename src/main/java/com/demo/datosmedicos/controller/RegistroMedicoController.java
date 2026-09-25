package com.demo.datosmedicos.controller;

import com.demo.datosmedicos.entity.RegistroMedico;
import com.demo.datosmedicos.repository.RegistroMedicoRepository;
import com.demo.datosmedicos.repository.PacienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
@Tag(name = "Registros Médicos", description = "API para la administración de registros médicos")
public class RegistroMedicoController {

//    private final RegistroMedicoRepository registroRepository;
//    private final PacienteRepository pacienteRepository;

    @PostMapping
    @Operation(summary = "Crear registro médico", description = "Crea un registro para un paciente existente. El estado inicial siempre será ACTIVO.")
    public ResponseEntity<RegistroMedico> crearRegistro(
            @Parameter(description = "ID del paciente") @RequestParam Long pacienteId,
            @Parameter(description = "Fecha y hora en formato ISO-8601 (Ej: 2023-10-25T10:30:00)") @RequestParam String fechaHora) {

        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener registro por ID", description = "Retorna un registro médico específico, ignorando los que están eliminados lógicamente.")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<RegistroMedico> obtenerPorId(
            @Parameter(description = "ID del registro") @PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los Registros Medicos", description = "Retorna una lista completa de los Registro Medicos.")
    public ResponseEntity<List<RegistroMedico>> obtenerTodos() {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado del registro", description = "Modifica el estado de un registro médico (ACTIVO, ARCHIVADO, CANCELADO).")
    public ResponseEntity<RegistroMedico> actualizarEstado(
            @Parameter(description = "ID del registro a actualizar") @PathVariable Long id,
            @Parameter(description = "Nuevo estado del registro") @RequestParam RegistroMedico.EstadoRegistro estado) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro médico", description = "Realiza una eliminación lógica (soft delete) del registro médico marcándolo como eliminado.")
    public ResponseEntity<Void> eliminarRegistro(
            @Parameter(description = "ID del registro a eliminar") @PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obtener registros por paciente", description = "Retorna los registros médicos asociados a un paciente específico, con paginación.")
    public ResponseEntity<Page<RegistroMedico>> obtenerPorPaciente
    (
            @Parameter(description = "ID del paciente") @PathVariable Long pacienteId
//           ,@Parameter(description = "Número de página (comienza en 0)") @RequestParam(defaultValue = "0") int page,
//            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
//            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "fechaHora") String sortBy,
//            @Parameter(description = "Dirección del orden (ASC o DESC)") @RequestParam(defaultValue = "DESC") Sort.Direction direction
    )
    {

        return ResponseEntity.ok(null);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener registros por estado", description = "Retorna los registros médicos filtrados por estado, con paginación, realizando un JOIN FETCH para incluir los datos del paciente.")
    public ResponseEntity<Page<RegistroMedico>> obtenerPorEstado
        (
            @Parameter(description = "Estado del registro (ACTIVO, ARCHIVADO, CANCELADO)") @PathVariable RegistroMedico.EstadoRegistro estado
//           ,@Parameter(description = "Número de página (comienza en 0)") @RequestParam(defaultValue = "0") int page,
//            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
//            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "fechaHora") String sortBy,
//            @Parameter(description = "Dirección del orden (ASC o DESC)") @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {

        return ResponseEntity.ok(null);
    }

    @GetMapping("/paginados")
    @Operation(summary = "Obtener registros paginados", description = "Retorna los registros médicos utilizando paginación y ordenamiento JPA.")
    public ResponseEntity<Page<RegistroMedico>> obtenerPaginados
    (
            @Parameter(description = "Número de página (comienza en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "fechaHora") String sortBy,
            @Parameter(description = "Dirección del orden (ASC o DESC)") @RequestParam(defaultValue = "DESC") Sort.Direction direction
    )
    {

        return ResponseEntity.ok(null);
    }

    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar registros por fechas", description = "Retorna los registros médicos creados dentro de un rango de fechas específico (por día completo), con paginación.")
    public ResponseEntity<?> filtrarPorFechas
    (
            @Parameter(description = "Fecha de inicio (yyyy-MM-dd)") @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @Parameter(description = "Fecha de fin (yyyy-MM-dd)") @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
//           ,@Parameter(description = "Número de página (comienza en 0)") @RequestParam(defaultValue = "0") int page,
//            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
//            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "fechaHora") String sortBy,
//            @Parameter(description = "Dirección del orden (ASC o DESC)") @RequestParam(defaultValue = "DESC") Sort.Direction direction
    )
    {

        return ResponseEntity.ok(null);
    }

}