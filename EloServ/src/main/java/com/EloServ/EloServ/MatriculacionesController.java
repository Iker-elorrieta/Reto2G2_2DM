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

import modelo.Matriculaciones;

@RestController
@RequestMapping("/matriculaciones")
@CrossOrigin
public class MatriculacionesController {

    private SessionFactory sessionFactory;

    public MatriculacionesController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @GetMapping
    public List<Map<String, Object>> getMatriculaciones() {
        Session session = sessionFactory.openSession();

        List<Matriculaciones> lista = session.createQuery(
            "select m from Matriculaciones m " +
            "join fetch m.users u " +
            "join fetch m.ciclos c",
            Matriculaciones.class
        ).list();

        session.close();

        return lista.stream().map(m -> {
            Map<String, Object> fila = new HashMap<>();
            fila.put("id", m.getId());
            fila.put("alum_id", m.getUsers().getId());
            fila.put("ciclo_id", m.getCiclos().getId());
            fila.put("fecha", m.getFecha());
            fila.put("nombre_alumno", m.getUsers().getNombre());
            fila.put("nombre_ciclo", m.getCiclos().getNombre());
            return fila;
        }).toList();
    }
}
