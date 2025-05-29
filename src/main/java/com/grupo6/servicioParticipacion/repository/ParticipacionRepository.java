package com.grupo6.servicioParticipacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo6.servicioParticipacion.model.EstadoParticipacion;
import com.grupo6.servicioParticipacion.model.Evento;
import com.grupo6.servicioParticipacion.model.Participacion;
import com.grupo6.servicioParticipacion.model.Usuario;



@Repository
public interface ParticipacionRepository extends JpaRepository<Participacion, Integer> {
    List<Participacion> findByUsuario(Usuario usuario);
    List<Participacion> findByEvento(Evento evento);
    List<Participacion> findByEstadoParticipacion(EstadoParticipacion estadoParticipacion);
}
