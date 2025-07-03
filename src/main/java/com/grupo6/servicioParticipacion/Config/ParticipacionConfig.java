package com.grupo6.servicioParticipacion.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class ParticipacionConfig {

    @Bean // Un Bean es un objeto que se gestiona en el contexto de Spring para que pueda ser inyectado en otros componentes de la aplicación.
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Servicio de Participación")
                        .version("1.0.0")
                        .description("API para gestionar la participación en eventos y actividades."));
    }

}
