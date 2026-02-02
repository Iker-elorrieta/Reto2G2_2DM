package com.EloServ.EloServ;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.*;

import modelo.Ciclos;
import modelo.HibernateUtil;

@RestController
@RequestMapping("/ciclos")
@CrossOrigin
public class CicloController {

	private final String nombre = "nombre";
	private final String id = "id";
	

    public CicloController() {
    }

    @GetMapping
    public List<Ciclos> getCiclos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Ciclos> lista = session.createQuery("from Ciclos", Ciclos.class).list();
        session.close();
        return lista;
    }

    @PostMapping
    public Map<String, Object> createCiclo(@RequestBody Map<String, Object> body) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Ciclos ciclo = new Ciclos();
        ciclo.setNombre((String) body.get(nombre));

        session.persist(ciclo);

        tx.commit();
        session.close();

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put(id, ciclo.getId());
        respuesta.put(nombre, ciclo.getNombre());

        return respuesta;
    }
    
}