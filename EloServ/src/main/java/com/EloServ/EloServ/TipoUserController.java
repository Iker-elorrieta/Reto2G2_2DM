
package com.EloServ.EloServ;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;

import modelo.Tipos;

@RestController
@RequestMapping("/tipos-user")
@CrossOrigin
public class TipoUserController {

    private final String Id = "id";
    private final String Nombre = "nombre";
    private final String Nombre_eus = "nombre_eus";

    private SessionFactory sessionFactory;

    public TipoUserController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @GetMapping
    public List<Map<String, Object>> getTiposUser() {
        Session session = sessionFactory.openSession();

        List<Tipos> lista = session.createQuery(
            "from Tipos",
            Tipos.class
        ).list();

        session.close();

        return lista.stream().map(t -> {
            Map<String, Object> fila = new HashMap<>();
            fila.put(Id, t.getId());
            fila.put(Nombre, t.getName());
            fila.put(Nombre_eus, t.getNameEu());
            return fila;
        }).toList();
    }
}
