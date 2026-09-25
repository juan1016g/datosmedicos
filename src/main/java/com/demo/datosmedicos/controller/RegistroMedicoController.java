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

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obtener registros por paciente", description = "Retorna los registros médicos asociados a un paciente específico, con paginación.")
    public ResponseEntity<Page<RegistroMedico>> obtenerPorPaciente(
            @Parameter(description = "ID del paciente") @PathVariable Long pacienteId,
            @Parameter(description = "Número de página (comienza en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "fechaHora") String sortBy,
            @Parameter(description = "Dirección del orden (ASC o DESC)") @RequestParam(defaultValue = "DESC") Sort.Direction direction) {

        var pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<RegistroMedico> registros = registroRepository.findByPacienteId(pacienteId, pageable);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener registros por estado", description = "Retorna los registros médicos filtrados por estado, con paginación, realizando un JOIN FETCH para incluir los datos del paciente.")
    public ResponseEntity<Page<RegistroMedico>> obtenerPorEstado(
            @Parameter(description = "Estado del registro (ACTIVO, ARCHIVADO, CANCELADO)") @PathVariable RegistroMedico.EstadoRegistro estado,
            @Parameter(description = "Número de página (comienza en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "fechaHora") String sortBy,
            @Parameter(description = "Dirección del orden (ASC o DESC)") @RequestParam(defaultValue = "DESC") Sort.Direction direction) {

        var pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<RegistroMedico> registros = registroRepository.findByEstadoConPaciente(estado, pageable);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/paginados")
    @Operation(summary = "Obtener registros paginados", description = "Retorna los registros médicos utilizando paginación y ordenamiento JPA.")
    public ResponseEntity<Page<RegistroMedico>> obtenerPaginados(
            @Parameter(description = "Número de página (comienza en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "fechaHora") String sortBy,
            @Parameter(description = "Dirección del orden (ASC o DESC)") @RequestParam(defaultValue = "DESC") Sort.Direction direction) {

        var pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<RegistroMedico> paginaRegistros = registroRepository.findAll(pageable);

        return ResponseEntity.ok(paginaRegistros);
    }

    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar registros por fechas", description = "Retorna los registros médicos creados dentro de un rango de fechas específico (por día completo), con paginación.")
    public ResponseEntity<?> filtrarPorFechas(
            @Parameter(description = "Fecha de inicio (yyyy-MM-dd)") @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @Parameter(description = "Fecha de fin (yyyy-MM-dd)") @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @Parameter(description = "Número de página (comienza en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "fechaHora") String sortBy,
            @Parameter(description = "Dirección del orden (ASC o DESC)") @RequestParam(defaultValue = "DESC") Sort.Direction direction) {

        LocalDate fechaMinima = LocalDate.of(2000, 1, 1);
        LocalDate fechaMaxima = LocalDate.now().plusYears(1);

        if (inicio.isAfter(fin)) return ResponseEntity.badRequest().body("La fecha de inicio no puede ser mayor que la fecha de fin.");

        if (inicio.isBefore(fechaMinima) || fin.isBefore(fechaMinima)) return ResponseEntity.badRequest().body("Las fechas no pueden ser anteriores a " + fechaMinima + ".");

        if (inicio.isAfter(fechaMaxima) || fin.isAfter(fechaMaxima)) return ResponseEntity.badRequest().body("Las fechas no pueden ser posteriores a " + fechaMaxima + ".");

        LocalDateTime fechaInicio = inicio.atStartOfDay();
        LocalDateTime fechaFin = fin.atTime(LocalTime.MAX);

        var pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<RegistroMedico> registros = registroRepository.findByFechaHoraBetween(fechaInicio, fechaFin, pageable);
        return ResponseEntity.ok(registros);
    }

}