package com.EloServ.EloServ;

import java.util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import modelo.Users;
import modelo.Tipos;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private SessionFactory sessionFactory;

    public UserController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // ============================================
    // GET /users
    // ============================================
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getUsers() {
        Session session = sessionFactory.openSession();

        List<Users> lista = session.createQuery(
            "select u from Users u left join fetch u.tipos t",
            Users.class
        ).list();

        session.close();

        List<Map<String, Object>> respuesta = lista.stream().map(u -> {
            Map<String, Object> fila = new HashMap<>();
            fila.put("id", u.getId());
            fila.put("email", u.getEmail());
            fila.put("username", u.getUsername());
            fila.put("password", u.getPassword());
            fila.put("nombre", u.getNombre());
            fila.put("apellidos", u.getApellidos());
            fila.put("dni", u.getDni());
            fila.put("direccion", u.getDireccion());
            fila.put("telefono1", u.getTelefono1());
            fila.put("telefono2", u.getTelefono2());
            fila.put("argazkia_url", u.getArgazkiaUrl());
            fila.put("created_at", u.getCreatedAt());
            fila.put("updated_at", u.getUpdatedAt());

            if (u.getTipos() != null) {
                fila.put("tipo_id", u.getTipos().getId());
                fila.put("tipo_nombre", u.getTipos().getName());
            }

            return fila;
        }).toList();

        return ResponseEntity.ok(respuesta);
    }

    // ============================================
    // GET /users/{id}
    // ============================================
    @GetMapping("/{id}")
    public Map<String, Object> getUserById(@PathVariable("id") int id) {
        Session session = sessionFactory.openSession();

        Users u = session.get(Users.class, id);
        session.close();

        if (u == null) return Map.of();

        Map<String, Object> fila = new HashMap<>();
        fila.put("id", u.getId());
        fila.put("username", u.getUsername());
        fila.put("email", u.getEmail());
        fila.put("nombre", u.getNombre());
        fila.put("apellidos", u.getApellidos());

        return fila;
    }

    // ============================================
    // POST /users
    // ============================================
    @PostMapping
    public ResponseEntity<Map<String, Object>> addUsers(@RequestBody Map<String, Object> body) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        java.sql.Timestamp ahora = new java.sql.Timestamp(System.currentTimeMillis());

        Users u = new Users();

        u.setEmail((String) body.get("email"));
        u.setUsername((String) body.get("username"));
        u.setPassword((String) body.get("password"));
        u.setNombre((String) body.get("nombre"));
        u.setApellidos((String) body.get("apellidos"));
        u.setDni((String) body.get("dni"));
        u.setDireccion((String) body.get("direccion"));
        u.setTelefono1((String) body.get("telefono1"));
        u.setTelefono2((String) body.get("telefono2"));
        u.setArgazkiaUrl((String) body.get("argazkia_url"));

        u.setCreatedAt(ahora);
        u.setUpdatedAt(ahora);

        if (body.containsKey("tipo_id")) {
            Tipos tipo = session.get(Tipos.class, (int) body.get("tipo_id"));
            u.setTipos(tipo);
        }

        session.persist(u);
        tx.commit();
        session.close();

        body.put("id", u.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    // ============================================
    // PUT /users/{id}
    // ============================================
    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") int id, @RequestBody Map<String, Object> body) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Users u = session.get(Users.class, id);
        if (u == null) {
            session.close();
            return "Usuario no encontrado";
        }

        if (body.containsKey("username"))
            u.setUsername((String) body.get("username"));

        if (body.containsKey("email"))
            u.setEmail((String) body.get("email"));

        if (body.containsKey("password"))
            u.setPassword((String) body.get("password"));

        u.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        session.merge(u);
        tx.commit();
        session.close();

        return "Actualizado";
    }

    // ============================================
    // DELETE /users/{id}
    // ============================================
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Users u = session.get(Users.class, id);
        if (u == null) {
            session.close();
            return "Usuario no encontrado";
        }

        session.remove(u);
        tx.commit();
        session.close();

        return "Eliminado";
    }

    // ============================================
    // LOGIN
    // ============================================
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Session session = sessionFactory.openSession();

        String username = credentials.get("username");
        String password = credentials.get("password");

        Users u = session.createQuery(
            "select u from Users u where u.username = :user and u.password = :pass",
            Users.class
        )
        .setParameter("user", username)
        .setParameter("pass", password)
        .uniqueResult();

        session.close();

        Map<String, Object> respuesta = new HashMap<>();

        if (u != null) {
            respuesta.put("id", u.getId());
            respuesta.put("username", u.getUsername());
            respuesta.put("tipo_id", u.getTipos() != null ? u.getTipos().getId() : null);
            respuesta.put("login_status", "success");
            return ResponseEntity.ok(respuesta);
        }

        respuesta.put("login_status", "error");
        return ResponseEntity.status(401).body(respuesta);
    }
}
