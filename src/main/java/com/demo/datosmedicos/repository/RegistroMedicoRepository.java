package com.demo.datosmedicos.repository;

import com.demo.datosmedicos.entity.RegistroMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroMedicoRepository extends JpaRepository<RegistroMedico, Long> {
    List findByPacienteId(Long pacienteId);

    @Query("SELECT r FROM RegistroMedico r JOIN FETCH r.paciente WHERE r.estado = :estado")
    List findByEstadoConPaciente(@Param("estado") RegistroMedico.EstadoRegistro estado);
}