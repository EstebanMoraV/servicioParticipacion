package com.grupo6.servicioParticipacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "estado_participacion")
public class EstadoParticipacion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id_estado;

    @Column(nullable = false, length = 50)
    private String nombre_estado;

}
