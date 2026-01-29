package com.EloServ.EloServ;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import modelo.Users;
import modelo.HibernateUtil;
import modelo.Tipos;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final String Id = "id";
    private final String Email = "email";
    private final String Username = "username";
    private final String Password = "password";
    private final String Nombre = "nombre";
    private final String Apellidos = "apellidos";
    private final String Dni = "dni";
    private final String Direccion = "direccion";
    private final String Telefono1 = "telefono1";
    private final String Telefono2 = "telefono2";
    private final String Argazkia_url = "argazkia_url";
    private final String Created_at = "created_at";
    private final String Updated_at = "updated_at";

    private final String Tipo_id = "tipo_id";
    private final String Tipo_nombre = "tipo_nombre";

    private final String Error = "error";
    private final String Message = "message";
    private final String Login_status = "login_status";
    private final String Success = "success";

 

    public UserController() {
        
    }

    private Map<String, Object> mapUser(Users u) {
        Map<String, Object> fila = new HashMap<>();
        fila.put(Id, u.getId());
        fila.put(Email, u.getEmail());
        fila.put(Username, u.getUsername());
        fila.put(Password, u.getPassword());
        fila.put(Nombre, u.getNombre());
        fila.put(Apellidos, u.getApellidos());
        fila.put(Dni, u.getDni());
        fila.put(Direccion, u.getDireccion());
        fila.put(Telefono1, u.getTelefono1());
        fila.put(Telefono2, u.getTelefono2());
        fila.put(Argazkia_url, u.getArgazkiaUrl());
        fila.put(Created_at, u.getCreatedAt());
        fila.put(Updated_at, u.getUpdatedAt());

        if (u.getTipos() != null) {
            fila.put(Tipo_id, u.getTipos().getId());
            fila.put(Tipo_nombre, u.getTipos().getName());
        }
        return fila;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Users> lista = session.createQuery(
                "select u from Users u left join fetch u.tipos t",
                Users.class
            ).list();

            List<Map<String, Object>> respuesta = lista.stream()
                .map(this::mapUser)
                .collect(Collectors.toList());

            return ResponseEntity.ok(respuesta);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable("id") int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Users u = session.createQuery(
                "select u from Users u left join fetch u.tipos where u.id = :id",
                Users.class
            )
            .setParameter(Id, id)
            .uniqueResult();

            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(mapUser(u));
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addUsers(@RequestBody Map<String, Object> body) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                java.sql.Timestamp ahora = new java.sql.Timestamp(System.currentTimeMillis());

                Users u = new Users();
                u.setEmail((String) body.get(Email));
                u.setUsername((String) body.get(Username));
                u.setPassword((String) body.get(Password));
                u.setNombre((String) body.get(Nombre));
                u.setApellidos((String) body.get(Apellidos));
                u.setDni((String) body.get(Dni));
                u.setDireccion((String) body.get(Direccion));
                u.setTelefono1((String) body.get(Telefono1));
                u.setTelefono2((String) body.get(Telefono2));
                u.setArgazkiaUrl((String) body.get(Argazkia_url));
                u.setCreatedAt(ahora);
                u.setUpdatedAt(ahora);

                if (body.get(Tipo_id) != null) {
                    int tipoId = ((Number) body.get(Tipo_id)).intValue();
                    Tipos tipo = session.find(Tipos.class, tipoId);
                    u.setTipos(tipo);
                }

                session.persist(u);
                tx.commit();

                body.put(Id, u.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(body);
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable("id") int id, @RequestBody Map<String, Object> body) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Users u = session.find(Users.class, id);

                if (u == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(Error, "Usuario no encontrado"));
                }

                if (body.containsKey(Username)) u.setUsername((String) body.get(Username));
                if (body.containsKey(Email)) u.setEmail((String) body.get(Email));
                if (body.containsKey(Password)) u.setPassword((String) body.get(Password));

                if (body.containsKey(Tipo_id) && body.get(Tipo_id) != null) {
                    int tipoId = ((Number) body.get(Tipo_id)).intValue();
                    Tipos tipo = session.find(Tipos.class, tipoId);
                    if (tipo != null) u.setTipos(tipo);
                }

                u.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

                session.merge(u);
                tx.commit();

                return ResponseEntity.ok(Map.of(Message, "Actualizado"));
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(Error, "Error interno del servidor"));
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Users u = session.find(Users.class, id);

                if (u == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(Error, "Usuario no encontrado"));
                }

                session.remove(u);
                tx.commit();
                
                return ResponseEntity.ok(Map.of(Message, "Eliminado"));
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(Error, "Error interno al eliminar"));
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String username = credentials.get(Username);
            String password = credentials.get(Password);

            Users u = session.createQuery(
                "select u from Users u where u.username = :user and u.password = :pass",
                Users.class
            )
            .setParameter("user", username)
            .setParameter("pass", password)
            .uniqueResult();

            Map<String, Object> respuesta = new HashMap<>();
            if (u != null) {
                respuesta.put(Id, u.getId());
                respuesta.put(Username, u.getUsername());
                respuesta.put(Tipo_id, u.getTipos() != null ? u.getTipos().getId() : null);
                respuesta.put(Login_status, Success);
                return ResponseEntity.ok(respuesta);
            }

            respuesta.put(Login_status, Error);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }
    }
}