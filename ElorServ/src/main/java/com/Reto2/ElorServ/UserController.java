package com.Reto2.ElorServ;

import java.sql.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private Connection connection;

    public UserController() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/eduelorrieta",
                "root",
                ""
            );
            System.out.println("Conexión exitosa a MySQL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================================
    // GET /users
    // ============================================
    @GetMapping
    public List<Map<String, Object>> getUsers() {
        List<Map<String, Object>> lista = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id"));
                fila.put("email", rs.getString("email"));
                fila.put("username", rs.getString("username"));
                fila.put("password", rs.getString("password"));
                fila.put("nombre", rs.getString("nombre"));
                fila.put("apellidos", rs.getString("apellidos"));
                fila.put("dni", rs.getString("dni"));
                fila.put("direccion", rs.getString("direccion"));
                fila.put("telefono1", rs.getString("telefono1"));
                fila.put("telefono2", rs.getString("telefono2"));
                fila.put("tipo_id", rs.getInt("tipo_id"));
                fila.put("argazkia_url", rs.getString("argazkia_url"));
                fila.put("created_at", rs.getString("created_at"));
                fila.put("updated_at", rs.getString("updated_at"));

                lista.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ============================================
    // GET /users/{id}
    // ============================================
    @GetMapping("/{id}")
    public Map<String, Object> getUserById(@PathVariable int id) {
        Map<String, Object> fila = new HashMap<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                fila.put("id", rs.getInt("id"));
                fila.put("email", rs.getString("email"));
                fila.put("username", rs.getString("username"));
                fila.put("password", rs.getString("password"));
                fila.put("nombre", rs.getString("nombre"));
                fila.put("apellidos", rs.getString("apellidos"));
                fila.put("dni", rs.getString("dni"));
                fila.put("direccion", rs.getString("direccion"));
                fila.put("telefono1", rs.getString("telefono1"));
                fila.put("telefono2", rs.getString("telefono2"));
                fila.put("tipo_id", rs.getInt("tipo_id"));
                fila.put("argazkia_url", rs.getString("argazkia_url"));
                fila.put("created_at", rs.getString("created_at"));
                fila.put("updated_at", rs.getString("updated_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fila;
    }

    // ============================================
    // POST /users
    // ============================================
    @PostMapping
    public Map<String, Object> createUser(@RequestBody Map<String, Object> body) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO users (email, username, password, nombre, apellidos, dni, direccion, telefono1, telefono2, tipo_id, argazkia_url) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, (String) body.get("email"));
            stmt.setString(2, (String) body.get("username"));
            stmt.setString(3, (String) body.get("password"));
            stmt.setString(4, (String) body.get("nombre"));
            stmt.setString(5, (String) body.get("apellidos"));
            stmt.setString(6, (String) body.get("dni"));
            stmt.setString(7, (String) body.get("direccion"));
            stmt.setString(8, (String) body.get("telefono1"));
            stmt.setString(9, (String) body.get("telefono2"));
            stmt.setInt(10, (int) body.get("tipo_id"));
            stmt.setString(11, (String) body.get("argazkia_url"));

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) respuesta.put("id", keys.getInt(1));

            respuesta.putAll(body);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return respuesta;
    }

    // ============================================
    // PUT /users/{id}
    // ============================================
    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody Map<String, Object> body) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "UPDATE users SET email=?, username=?, password=?, nombre=?, apellidos=?, dni=?, direccion=?, telefono1=?, telefono2=?, tipo_id=?, argazkia_url=? WHERE id=?"
            );

            stmt.setString(1, (String) body.get("email"));
            stmt.setString(2, (String) body.get("username"));
            stmt.setString(3, (String) body.get("password"));
            stmt.setString(4, (String) body.get("nombre"));
            stmt.setString(5, (String) body.get("apellidos"));
            stmt.setString(6, (String) body.get("dni"));
            stmt.setString(7, (String) body.get("direccion"));
            stmt.setString(8, (String) body.get("telefono1"));
            stmt.setString(9, (String) body.get("telefono2"));
            stmt.setInt(10, (int) body.get("tipo_id"));
            stmt.setString(11, (String) body.get("argazkia_url"));
            stmt.setInt(12, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error actualizando usuario";
        }

        return "Usuario actualizado";
    }

    // ============================================
    // DELETE /users/{id}
    // ============================================
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?"
            );
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error eliminando usuario";
        }

        return "Usuario eliminado";
    }
}
