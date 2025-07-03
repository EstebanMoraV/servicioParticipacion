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

import com.grupo6.servicioParticipacion.model.EstadoParticipacion;
import com.grupo6.servicioParticipacion.model.Evento;
import com.grupo6.servicioParticipacion.model.Participacion;
import com.grupo6.servicioParticipacion.model.Usuario;
import com.grupo6.servicioParticipacion.service.ParticipacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/participacion")
@Tag(name = "Participación", description = "Operaciones relacionadas con la participación en eventos y actividades")
public class ParticipacionController {

    @Autowired
    private ParticipacionService participacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las participaciones", description = "Devuelve una lista de todas las participaciones registradas")
    public ResponseEntity<List<Participacion>> getAllParticipaciones() {
        List<Participacion> participaciones = participacionService.getAllParticipaciones();
        return ResponseEntity.ok(participaciones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener participación por ID", description = "Devuelve la participación correspondiente al ID proporcionado")
    public ResponseEntity<Participacion> getParticipacionById(@PathVariable Integer id) {
        Participacion participacion = participacionService.getParticipacionById(id);
        if (participacion != null) {
            return ResponseEntity.ok(participacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener participaciones por usuario", description = "Devuelve una lista de participaciones asociadas al usuario con el ID proporcionado")
    public ResponseEntity<List<Participacion>> getParticipacionesByUsuario(@PathVariable Integer usuarioId) {
        List<Participacion> participaciones = participacionService.getParticipacionesByUsuario(new Usuario(usuarioId, null, null, null, null, null));
        if (participaciones != null && !participaciones.isEmpty()) {
            return ResponseEntity.ok(participaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/evento/{eventoId}")
    @Operation(summary = "Obtener participaciones por evento", description = "Devuelve una lista de participaciones asociadas al evento con el ID proporcionado")
    public ResponseEntity<List<Participacion>> getParticipacionesByEvento(@PathVariable Integer eventoId) {
        List<Participacion> participaciones = participacionService.getParticipacionesByEvento(new Evento(eventoId, null, null, null, null, null, null, null));
        if (participaciones != null && !participaciones.isEmpty()) {
            return ResponseEntity.ok(participaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/estado/{idEstado}")
    @Operation(summary = "Obtener participaciones por estado", description = "Devuelve una lista de participaciones asociadas al estado con el ID proporcionado")
    public ResponseEntity<List<Participacion>> getParticipacionesByEstado(@PathVariable Integer idEstado) {
        List<Participacion> participaciones = participacionService.getParticipacionesByEstado(new EstadoParticipacion(idEstado, null));
        if (participaciones != null && !participaciones.isEmpty()) {
            return ResponseEntity.ok(participaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    @PostMapping
    @Operation(summary = "Guardar nueva participación", description = "Crea una nueva participación en un evento o actividad")
    public ResponseEntity<Participacion> saveParticipacion(@RequestBody Participacion participacion) {
        Participacion savedParticipacion = participacionService.saveParticipacion(participacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParticipacion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar participación", description = "Actualiza los detalles de una participación existente")
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
    @Operation(summary = "Eliminar participación", description = "Elimina una participación existente por su ID")
    public ResponseEntity<Void> deleteParticipacion(@PathVariable Integer id){
        try {
            participacionService.deleteParticipacion(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
