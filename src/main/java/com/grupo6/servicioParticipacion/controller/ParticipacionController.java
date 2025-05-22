package com.grupo6.servicioParticipacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo6.servicioParticipacion.model.Participacion;
import com.grupo6.servicioParticipacion.service.ParticipacionService;

@RestController
@RequestMapping("/api/v1/participacion")
public class ParticipacionController {

    @Autowired
    private ParticipacionService participacionService;

    @GetMapping
    public ResponseEntity<List<Participacion>> getAllParticipaciones() {
        List<Participacion> participaciones = participacionService.getAllParticipaciones();
        return ResponseEntity.ok(participaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participacion> getParticipacionById(@PathVariable Integer id) {
        Participacion participacion = participacionService.getParticipacionById(id);
        if (participacion != null) {
            return ResponseEntity.ok(participacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Participacion> saveParticipacion(@RequestBody Participacion participacion) {
        Participacion savedParticipacion = participacionService.saveParticipacion(participacion);
        return ResponseEntity.ok(savedParticipacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participacion> updateParticipacion(@PathVariable Integer id,
            @RequestBody Participacion participacion) {
        Participacion existingParticipacion = participacionService.getParticipacionById(id);
        if (existingParticipacion != null) {
            existingParticipacion.setIdParticipacion(id);
            existingParticipacion.setUsuario(participacion.getUsuario());
            existingParticipacion.setEstadoParticipacion(participacion.getEstadoParticipacion());                                                                                               
            existingParticipacion.setEvento(participacion.getEvento());
            existingParticipacion.setFechaParticipacion(participacion.getFechaParticipacion());
            
            Participacion updatedParticipacion = participacionService.saveParticipacion(participacion);
            return ResponseEntity.ok(updatedParticipacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipacion(@PathVariable Integer id){
        try {
            participacionService.deleteParticipacion(id);
            return ResponseEntity.ok("Participación eliminada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la participación");
        }
    }                                                               

}
