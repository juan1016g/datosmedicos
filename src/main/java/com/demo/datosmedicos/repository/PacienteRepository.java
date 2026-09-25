package com.demo.datosmedicos.repository;

import com.demo.datosmedicos.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDocumentoIdentidad(String documentoIdentidad);
    boolean existsByDocumentoIdentidad(String documentoIdentidad);
}