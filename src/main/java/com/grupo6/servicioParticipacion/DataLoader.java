package com.grupo6.servicioParticipacion;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grupo6.servicioParticipacion.model.EstadoParticipacion;
import com.grupo6.servicioParticipacion.model.Evento;
import com.grupo6.servicioParticipacion.model.Participacion;
import com.grupo6.servicioParticipacion.model.Usuario;
import com.grupo6.servicioParticipacion.repository.ParticipacionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final ParticipacionRepository participacionRepository;
    @SuppressWarnings("deprecation")
    private final Faker faker = new Faker(new java.util.Locale("es"));
    private final Random random = new Random();

    @PersistenceContext
    private EntityManager em; // Inyectamos el EntityManager para operaciones directas
    // EntityManager permite realizar operaciones de persistencia directamente

    public DataLoader(ParticipacionRepository participacionRepository) {
        this.participacionRepository = participacionRepository;
    }

    @SuppressWarnings("deprecation")
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Verificamos si ya existen participaciones
        if (participacionRepository.count() > 0) {
            System.out.println("Ya hay datos. No se insertan fakes.");
            return;
        }

        // Crear estados
        List<String> estados = List.of("Pendiente", "Confirmado", "Rechazado");
        for (String nombre : estados) {
            EstadoParticipacion ep = new EstadoParticipacion();
            ep.setNombre_estado(nombre);
            em.persist(ep);
        }

        for (int i = 0; i < 10; i++) {
            Usuario u = new Usuario();
            u.setNombreUsuario(faker.name().username() + random.nextInt(999));
            u.setContrasena(faker.internet().password());
            u.setCorreo(faker.internet().emailAddress());
            u.setFechaNacimiento(faker.date().birthday());
            em.persist(u);

            Evento ev = new Evento();
            ev.setTitulo(faker.book().title());
            ev.setDescripcion(faker.lorem().paragraph());
            ev.setFechaInicio(faker.date().future(10, TimeUnit.DAYS));
            ev.setFechaFin(faker.date().future(20, TimeUnit.DAYS));
            ev.setUbicacion(faker.address().fullAddress());
            ev.setFechaCreacion(faker.date().past(60, TimeUnit.DAYS));
            ev.setFechaActualizacion(faker.date().past(5, TimeUnit.DAYS));
            em.persist(ev);

            EstadoParticipacion estadoRandom = em
                .createQuery("FROM EstadoParticipacion", EstadoParticipacion.class)
                .getResultList()
                .get(random.nextInt(estados.size()));

            Participacion p = new Participacion();
            p.setUsuario(u);
            p.setEvento(ev);
            p.setEstadoParticipacion(estadoRandom);
            p.setFechaParticipacion(faker.date().past(15, TimeUnit.DAYS));
            em.persist(p);
        }

        System.out.println("Datos de prueba insertados correctamente.");
    }
}

