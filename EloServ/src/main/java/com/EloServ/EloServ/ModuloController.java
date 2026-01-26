package com.EloServ.EloServ;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;

import modelo.Modulos;
import modelo.Ciclos;

@RestController
@RequestMapping("/modulos")
@CrossOrigin
public class ModuloController {

    private final String Id = "id";
    private final String Nombre = "nombre";
    private final String Ciclo_id = "ciclo_id";
    private final String Nombre_ciclo = "nombre_ciclo";
    private final String Curso = "curso";
    private final String IdBusqueda_Param = "idBusqueda";

    private SessionFactory sessionFactory;

    public ModuloController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @GetMapping
    public List<Map<String, Object>> getModulos() {
        Session session = sessionFactory.openSession();

        List<Modulos> lista = session.createQuery(
            "select m from Modulos m " +
            "join fetch m.ciclos c",
            Modulos.class
        ).list();

        session.close();

        return lista.stream().map(m -> {
            Map<String, Object> fila = new HashMap<>();
            fila.put(Id, m.getId());
            fila.put(Nombre, m.getNombre());
            fila.put(Ciclo_id, m.getCiclos().getId());
            fila.put(Nombre_ciclo, m.getCiclos().getNombre());
            fila.put(Curso, m.getCurso());
            return fila;
        }).toList();
    }

    @PostMapping
    public Map<String, Object> createModulo(@RequestBody Map<String, Object> body) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            try {
                Number cicloIdRaw = (Number) body.get(Ciclo_id);
                if (cicloIdRaw == null) {
                    throw new IllegalArgumentException("El campo '" + Ciclo_id + "' es obligatorio");
                }
                int cicloId = cicloIdRaw.intValue();
                Ciclos ciclo = session.find(Ciclos.class, cicloId);

                if (ciclo == null) {
                    throw new RuntimeException("No se encontró el Ciclo con ID: " + cicloId);
                }

                Modulos modulo = new Modulos();
                modulo.setNombre((String) body.get(Nombre));
                modulo.setCiclos(ciclo);
                session.persist(modulo);

                tx.commit();

                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put(Id, modulo.getId());
                respuesta.put(Nombre, modulo.getNombre());
                respuesta.put(Ciclo_id, ciclo.getId());

                return respuesta;
                
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
    }
    
    @GetMapping("/ciclo/{cicloId}")
    public List<Map<String, Object>> getModulosPorCiclo(@PathVariable("cicloId") Integer cicloId) {
        Session session = sessionFactory.openSession();

        List<Modulos> lista = session.createQuery(
            "select m from Modulos m " +
            "join fetch m.ciclos c " +
            "where c.id = :" + IdBusqueda_Param, 
            Modulos.class
        )
        .setParameter(IdBusqueda_Param, cicloId)
        .list();

        session.close();

        return lista.stream().map(m -> {
            Map<String, Object> fila = new HashMap<>();
            fila.put(Id, m.getId());
            fila.put(Nombre, m.getNombre());
            fila.put(Curso, m.getCurso());
            fila.put(Ciclo_id, m.getCiclos().getId());
            fila.put(Nombre_ciclo, m.getCiclos().getNombre());
            return fila;
        }).toList();
    }
}