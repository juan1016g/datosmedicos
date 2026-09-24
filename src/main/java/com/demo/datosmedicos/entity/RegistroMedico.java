package com.demo.datosmedicos.entity;

import java.time.LocalDateTime;

public class RegistroMedico {

    private Long id;
    private LocalDateTime fechaHora;
    private EstadoRegistro estado;
    private Paciente paciente;
    private boolean eliminado = false;
    private Long version;

    public enum EstadoRegistro {
        ACTIVO, ARCHIVADO, CANCELADO
    }
}
