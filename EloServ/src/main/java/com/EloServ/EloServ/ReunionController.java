package com.EloServ.EloServ;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;

import modelo.Reuniones;
import modelo.Users;

@RestController
@RequestMapping("/reuniones")
@CrossOrigin
public class ReunionController {

    private SessionFactory sessionFactory;

    public ReunionController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @GetMapping
    public List<Map<String, Object>> getReuniones() {
        Session session = sessionFactory.openSession();

        List<Reuniones> lista = session.createQuery(
            "select r from Reuniones r " +
            "left join fetch r.usersByProfesorId p " +
            "left join fetch r.usersByAlumnoId a",
            Reuniones.class
        ).list();

        session.close();

        return lista.stream().map(r -> {
            Map<String, Object> fila = new HashMap<>();

            fila.put("id_reunion", r.getIdReunion());
            fila.put("estado", r.getEstado());
            fila.put("estado_eus", r.getEstadoEus());
            fila.put("id_centro", r.getIdCentro());
            fila.put("titulo", r.getTitulo());
            fila.put("asunto", r.getAsunto());
            fila.put("aula", r.getAula());
            fila.put("fecha", r.getFecha());
            fila.put("created_at", r.getCreatedAt());
            fila.put("updated_at", r.getUpdatedAt());

            // Profesor
            if (r.getUsersByProfesorId() != null) {
                fila.put("profesor_id", r.getUsersByProfesorId().getId());
                fila.put("nombre_profesor", r.getUsersByProfesorId().getNombre());
                fila.put("apellidos_profesor", r.getUsersByProfesorId().getApellidos());
            }

            // Alumno
            if (r.getUsersByAlumnoId() != null) {
                fila.put("alumno_id", r.getUsersByAlumnoId().getId());
                fila.put("nombre_alumno", r.getUsersByAlumnoId().getNombre());
                fila.put("apellidos_alumno", r.getUsersByAlumnoId().getApellidos());
            }

            return fila;
        }).toList();
    }

    // ============================
    // CREATE
    // ============================
    @PostMapping
    public Map<String, Object> createReunion(@RequestBody Map<String, Object> body) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Users profesor = session.find(Users.class, (int) body.get("profesor_id"));
        Users alumno = session.find(Users.class, (int) body.get("alumno_id"));

        Reuniones r = new Reuniones();
        r.setEstado((String) body.get("estado"));
        r.setEstadoEus((String) body.get("estado_eus"));
        r.setUsersByProfesorId(profesor);
        r.setUsersByAlumnoId(alumno);
        r.setIdCentro(String.valueOf(body.get("id_centro")));
        r.setTitulo((String) body.get("titulo"));
        r.setAsunto((String) body.get("asunto"));
        r.setAula((String) body.get("aula"));
        r.setFecha(java.sql.Timestamp.valueOf((String) body.get("fecha")));

        session.persist(r);
        tx.commit();
        session.close();

        Map<String, Object> respuesta = new HashMap<>(body);
        respuesta.put("id_reunion", r.getIdReunion());

        return respuesta;
    }

    // ============================
    // UPDATE
    // ============================
    @PutMapping("/{id}")
    public String updateReunion(@PathVariable int id, @RequestBody Map<String, Object> body) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Reuniones r = session.find(Reuniones.class, id);
        if (r == null) {
            session.close();
            return "Reunión no encontrada";
        }

        Users profesor = session.find(Users.class, (int) body.get("profesor_id"));
        Users alumno = session.find(Users.class, (int) body.get("alumno_id"));

        r.setEstado((String) body.get("estado"));
        r.setEstadoEus((String) body.get("estado_eus"));
        r.setUsersByProfesorId(profesor);
        r.setUsersByAlumnoId(alumno);
        r.setIdCentro(String.valueOf(body.get("id_centro")));
        r.setTitulo((String) body.get("titulo"));
        r.setAsunto((String) body.get("asunto"));
        r.setAula((String) body.get("aula"));
        r.setFecha(java.sql.Timestamp.valueOf((String) body.get("fecha")));

        session.merge(r);
        tx.commit();
        session.close();

        return "Reunión actualizada";
    }

    // ============================
    // DELETE
    // ============================
    @DeleteMapping("/{id}")
    public String deleteReunion(@PathVariable int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Reuniones r = session.find(Reuniones.class, id);
        if (r == null) {
            session.close();
            return "Reunión no encontrada";
        }

        session.remove(r);
        tx.commit();
        session.close();

        return "Reunión eliminada";
    }
}
