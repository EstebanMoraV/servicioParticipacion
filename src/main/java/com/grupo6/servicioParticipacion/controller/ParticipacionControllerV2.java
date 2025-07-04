package com.grupo6.servicioParticipacion.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo6.servicioParticipacion.Assemblers.ParticipacionModelAssembler;
import com.grupo6.servicioParticipacion.model.Participacion;
import com.grupo6.servicioParticipacion.service.ParticipacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/participaciones")
@Tag(name = "Participaciones V2", description = "API para gestionar participaciones - HATEOAS")
public class ParticipacionControllerV2 {

        @Autowired
        private ParticipacionService participacionService; // Servicio para manejar la lógica de negocio de Participaciones

        @Autowired
        private ParticipacionModelAssembler participacionModelAssembler; // Ensambler para convertir Participacion a EntityModel

        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Obtener todas las participaciones", description = "Devuelve una lista de todas las participaciones con enlaces HATEOAS")
        public CollectionModel<EntityModel<Participacion>> getAllParticipaciones() {
                List<Participacion> participaciones = participacionService.getAllParticipaciones();
                List<EntityModel<Participacion>> participacionModels = participaciones.stream()
                                .map(participacionModelAssembler::toModel)
                                .collect(Collectors.toList());

                return CollectionModel.of(participacionModels,
                                linkTo(methodOn(ParticipacionControllerV2.class).getAllParticipaciones()).withSelfRel());
        }

        @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Obtener una participación por ID", description = "Devuelve una participación específica por su ID con enlaces HATEOAS")
        public ResponseEntity<EntityModel<Participacion>> getParticipacionById(@PathVariable Integer id) {
                Participacion participacion = participacionService.getParticipacionById(id);
                if (participacion != null) {
                        return ResponseEntity.ok(participacionModelAssembler.toModel(participacion));
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Crear una nueva participación", description = "Crea una nueva participación y devuelve el recurso creado con enlaces HATEOAS")
        public ResponseEntity<EntityModel<Participacion>> createParticipacion(@RequestBody Participacion participacion) {
                Participacion createdParticipacion = participacionService.saveParticipacion(participacion);
                return ResponseEntity.created(
                                linkTo(methodOn(ParticipacionControllerV2.class)
                                                .getParticipacionById(createdParticipacion.getIdParticipacion()))
                                                .toUri())
                                .body(participacionModelAssembler.toModel(createdParticipacion));
        }

        @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar una participación", description = "Actualiza una participación existente y devuelve el recurso actualizado con enlaces HATEOAS")
        public ResponseEntity<EntityModel<Participacion>> updateParticipacion(@PathVariable Integer id, @RequestBody Participacion participacion) {
                Participacion existingParticipacion = participacionService.getParticipacionById(id);
                if (existingParticipacion == null) {
                        return ResponseEntity.notFound().build();
                }
                existingParticipacion.setIdParticipacion(id);
                existingParticipacion.setUsuario(participacion.getUsuario());
                existingParticipacion.setEstadoParticipacion(participacion.getEstadoParticipacion());
                existingParticipacion.setEvento(participacion.getEvento());
                existingParticipacion.setFechaParticipacion(participacion.getFechaParticipacion());
                // Agrega aquí cualquier otro atributo que Participacion tenga y que deba actualizarse

                Participacion updatedParticipacion = participacionService.saveParticipacion(existingParticipacion);
                EntityModel<Participacion> participacionModel = participacionModelAssembler.toModel(updatedParticipacion);
                return ResponseEntity.ok(participacionModel);
        }

        @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Eliminar una participación", description = "Elimina una participación existente por su ID")
        public ResponseEntity<Void> deleteParticipacion(@PathVariable Integer id) {
                Participacion participacion = participacionService.getParticipacionById(id);
                if (participacion != null) {
                        participacionService.deleteParticipacion(id);
                        return ResponseEntity.noContent().build();
                } else {
                        return ResponseEntity.notFound().build();
                }
        }
}
