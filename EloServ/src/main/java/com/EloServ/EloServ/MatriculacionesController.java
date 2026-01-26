package com.EloServ.EloServ;

import java.util.Map;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelo.Matriculaciones;

@RestController
@RequestMapping("/matriculaciones")
@CrossOrigin
public class MatriculacionesController {

    private final String Id = "id";
    private final String Alum_id = "alum_id";
    private final String Ciclo_id = "ciclo_id";
    private final String Curso = "curso";
    private final String Fecha = "fecha";
    private final String Nombre_alumno = "nombre_alumno";
    private final String Nombre_ciclo = "nombre_ciclo";
    private final String UsuarioId_Param = "usuarioId";

    private SessionFactory sessionFactory;

    public MatriculacionesController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @GetMapping("/usuario/{id}")
    public Map<String, Object> getMatriculacionPorUsuario(@PathVariable("id") Long id) {
        Session session = sessionFactory.openSession();

        // Usamos uniqueResult() para obtener solo un objeto
        Matriculaciones m = session.createQuery(
            "select m from Matriculaciones m " +
            "join fetch m.users u " +
            "join fetch m.ciclos c " +
            "where u.id = :usuarioId", 
            Matriculaciones.class
        )
        .setParameter(UsuarioId_Param, id)
        .setMaxResults(1)
        .uniqueResult();

        session.close();

        if (m == null) return null;

        Map<String, Object> fila = new HashMap<>();
        fila.put(Id, m.getId());
        fila.put(Alum_id, m.getUsers().getId());
        fila.put(Ciclo_id, m.getCiclos().getId());
        fila.put(Curso, m.getCurso());
        fila.put(Fecha, m.getFecha());
        fila.put(Nombre_alumno, m.getUsers().getNombre());
        fila.put(Nombre_ciclo, m.getCiclos().getNombre());
        
        return fila;
    }
}