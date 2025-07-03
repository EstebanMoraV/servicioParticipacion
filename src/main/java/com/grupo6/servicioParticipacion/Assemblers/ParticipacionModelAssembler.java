package com.grupo6.servicioParticipacion.Assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.grupo6.servicioParticipacion.controller.ParticipacionController;
import com.grupo6.servicioParticipacion.model.Participacion;


@Component
public class ParticipacionModelAssembler implements RepresentationModelAssembler<Participacion, EntityModel<Participacion>> {

    @Override
    public EntityModel<Participacion> toModel(Participacion participacion) {
        return EntityModel.of(participacion,
                linkTo(methodOn(ParticipacionController.class)
                        .getParticipacionById(participacion.getIdParticipacion()))
                        .withSelfRel(),
                linkTo(methodOn(ParticipacionController.class)
                        .getAllParticipaciones())
                        .withRel("participaciones"));
    }


    

}
