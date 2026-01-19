package com.EloServ.EloServ;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelo.Horarios;

@RestController
@RequestMapping("/horarios")
@CrossOrigin
public class HorarioController {

    private SessionFactory sessionFactory;

    public HorarioController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @GetMapping
    public List<Map<String, Object>> getHorarios() {
        Session session = sessionFactory.openSession();

        // HQL con JOIN FETCH para evitar lazy loading
        List<Horarios> lista = session.createQuery(
            "select h from Horarios h " +
            "join fetch h.modulos m " +
            "join fetch h.users u",
            Horarios.class
        ).list();

        session.close();

        // Convertimos a Map como tu API original
        return lista.stream().map(h -> {
            Map<String, Object> fila = new HashMap<>();
            fila.put("id", h.getId());
            fila.put("dia", h.getDia());
            fila.put("hora", h.getHora());
            fila.put("modulo_id", h.getModulos().getId());
            fila.put("profe_id", h.getUsers().getId());
            fila.put("nombre_modulo", h.getModulos().getNombre());
            fila.put("nombre_profe", h.getUsers().getNombre());
            return fila;
        }).toList();
    }
}
