package com.demo.datosmedicos.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "registros_medicos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE registros_medicos SET eliminado = true WHERE id = ? AND version = ?")
@SQLRestriction("eliminado = false")
public class RegistroMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRegistro estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Builder.Default
    @Column(nullable = false)
    private boolean eliminado = false;

    @Version
    private Long version;

    public enum EstadoRegistro {
        ACTIVO, ARCHIVADO, CANCELADO
    }
}