package com.grupo6.servicioParticipacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo6.servicioParticipacion.model.EstadoParticipacion;
import com.grupo6.servicioParticipacion.model.Evento;
import com.grupo6.servicioParticipacion.model.Participacion;
import com.grupo6.servicioParticipacion.model.Usuario;
import com.grupo6.servicioParticipacion.repository.ParticipacionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ParticipacionService {

    @Autowired
    private ParticipacionRepository participacionRepository;

    public List<Participacion> getAllParticipaciones() {
        return participacionRepository.findAll();
    }

    public Participacion getParticipacionById(Integer id) {
        return participacionRepository.findById(id).orElse(null);
    }

    public List<Participacion> getParticipacionesByUsuario(Usuario usuarioId) {
        return participacionRepository.findByUsuario(usuarioId);
    }

    public List<Participacion> getParticipacionesByEvento(Evento eventoId) {
        return participacionRepository.findByEvento(eventoId);
    }

    public List<Participacion> getParticipacionesByEstado(EstadoParticipacion estado) {
        return participacionRepository.findByEstadoParticipacion(estado);
    }

    public Participacion saveParticipacion(Participacion participacion) {
        return participacionRepository.save(participacion);
    }

    public void deleteParticipacion(Integer id) {
        participacionRepository.deleteById(id);
    }

}
