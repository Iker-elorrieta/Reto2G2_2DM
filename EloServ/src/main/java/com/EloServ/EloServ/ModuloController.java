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
            fila.put("id", m.getId());
            fila.put("nombre", m.getNombre());
            fila.put("ciclo_id", m.getCiclos().getId());
            fila.put("nombre_ciclo", m.getCiclos().getNombre());
            return fila;
        }).toList();
    }

    @PostMapping
    public Map<String, Object> createModulo(@RequestBody Map<String, Object> body) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Ciclos ciclo = session.find(Ciclos.class, (int) body.get("ciclo_id"));

        Modulos modulo = new Modulos();
        modulo.setNombre((String) body.get("nombre"));
        modulo.setCiclos(ciclo);

        session.persist(modulo);

        tx.commit();
        session.close();

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("id", modulo.getId());
        respuesta.put("nombre", modulo.getNombre());
        respuesta.put("ciclo_id", ciclo.getId());

        return respuesta;
    }
}
