package com.grupo6.servicioParticipacion;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo6.servicioParticipacion.controller.ParticipacionController;
import com.grupo6.servicioParticipacion.model.EstadoParticipacion;
import com.grupo6.servicioParticipacion.model.Evento;
import com.grupo6.servicioParticipacion.model.Participacion;
import com.grupo6.servicioParticipacion.model.Usuario;
import com.grupo6.servicioParticipacion.service.ParticipacionService;

@WebMvcTest(ParticipacionController.class)
public class ParticipacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipacionService participacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("✅ Debería listar todas las participaciones")
    void testGetAllParticipaciones() throws Exception {
        Participacion p = new Participacion();
        p.setIdParticipacion(1);
        p.setFechaParticipacion(new Date());
        p.setUsuario(new Usuario());
        p.setEvento(new Evento());
        p.setEstadoParticipacion(new EstadoParticipacion());

        when(participacionService.getAllParticipaciones()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/v1/participacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idParticipacion").value(1));
    }

    @Test
    @DisplayName("✅ Debería crear una participación")
    void testGuardarParticipacion() throws Exception {
        Participacion nueva = new Participacion();
        nueva.setUsuario(new Usuario());
        nueva.setEvento(new Evento());
        nueva.setEstadoParticipacion(new EstadoParticipacion());

        Participacion creada = new Participacion();
        creada.setIdParticipacion(1);
        creada.setFechaParticipacion(new Date());
        creada.setUsuario(nueva.getUsuario());
        creada.setEvento(nueva.getEvento());
        creada.setEstadoParticipacion(nueva.getEstadoParticipacion());

        when(participacionService.saveParticipacion(any(Participacion.class))).thenReturn(creada);

        mockMvc.perform(post("/api/v1/participacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idParticipacion").value(1));
    }

    @Test
    @DisplayName("✅ Debería eliminar una participación")
    void testEliminarParticipacion() throws Exception {
        Integer id = 1;
        doNothing().when(participacionService).deleteParticipacion(id);

        mockMvc.perform(delete("/api/v1/participacion/{id}", id))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Deberia actualizar una participación")
    void testActualizarParticipacion() throws Exception {
        Integer id = 1;
        Participacion actualizada = new Participacion();
        actualizada.setIdParticipacion(id);
        actualizada.setUsuario(new Usuario());
        actualizada.setEvento(new Evento());
        actualizada.setEstadoParticipacion(new EstadoParticipacion());

        when(participacionService.getParticipacionById(id)).thenReturn(actualizada);
        when(participacionService.saveParticipacion(any(Participacion.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/participacion/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idParticipacion").value(id));
    }

}
