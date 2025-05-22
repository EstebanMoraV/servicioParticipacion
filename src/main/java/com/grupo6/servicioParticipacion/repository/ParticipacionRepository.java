package com.grupo6.servicioParticipacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo6.servicioParticipacion.model.Participacion;

@Repository
public interface ParticipacionRepository extends JpaRepository<Participacion, Integer> {
    @SuppressWarnings("override")
    Optional<Participacion> findById(Integer idParticipacion);
}
