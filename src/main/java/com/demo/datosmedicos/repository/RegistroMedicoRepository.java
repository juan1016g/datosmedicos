package com.demo.datosmedicos.repository;

import com.demo.datosmedicos.entity.RegistroMedico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroMedicoRepository extends JpaRepository<RegistroMedico, Long> {

    List<RegistroMedico> findByPacienteId(Long pacienteId);
    Page<RegistroMedico> findByPacienteId(Long pacienteId, Pageable pageable);

    @Query("SELECT r FROM RegistroMedico r JOIN FETCH r.paciente WHERE r.estado = :estado")
    List<RegistroMedico> findByEstadoConPaciente(RegistroMedico.EstadoRegistro estado);

    @Query(value = "SELECT r FROM RegistroMedico r JOIN FETCH r.paciente WHERE r.estado = :estado",
            countQuery = "SELECT COUNT(r) FROM RegistroMedico r WHERE r.estado = :estado")
    Page<RegistroMedico> findByEstadoConPaciente(RegistroMedico.EstadoRegistro estado, Pageable pageable);

    List<RegistroMedico> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    Page<RegistroMedico> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin, Pageable pageable);
}