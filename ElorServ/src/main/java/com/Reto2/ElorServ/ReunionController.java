package com.Reto2.ElorServ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/reuniones")
@CrossOrigin
public class ReunionController {

    private Connection connection;

    public ReunionController() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/eduelorrieta",
                "root",
                ""
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public List<Map<String, Object>> getReuniones() {
        List<Map<String, Object>> lista = new ArrayList<>();

        String query = """
            SELECT r.*,
                   u1.nombre AS nombre_profesor, u1.apellidos AS apellidos_profesor,
                   u2.nombre AS nombre_alumno, u2.apellidos AS apellidos_alumno
            FROM reuniones r
            LEFT JOIN users u1 ON r.profesor_id = u1.id
            LEFT JOIN users u2 ON r.alumno_id = u2.id
        """;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id_reunion", rs.getInt("id_reunion"));
                fila.put("profesor_id", rs.getInt("profesor_id"));
                fila.put("alumno_id", rs.getInt("alumno_id"));
                fila.put("fecha", rs.getString("fecha"));
                fila.put("motivo", rs.getString("motivo"));

                fila.put("nombre_profesor", rs.getString("nombre_profesor"));
                fila.put("apellidos_profesor", rs.getString("apellidos_profesor"));
                fila.put("nombre_alumno", rs.getString("nombre_alumno"));
                fila.put("apellidos_alumno", rs.getString("apellidos_alumno"));

                lista.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @PostMapping
    public Map<String, Object> createReunion(@RequestBody Map<String, Object> body) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO reuniones (profesor_id, alumno_id, fecha, motivo) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            stmt.setInt(1, (int) body.get("profesor_id"));
            stmt.setInt(2, (int) body.get("alumno_id"));
            stmt.setString(3, (String) body.get("fecha"));
            stmt.setString(4, (String) body.get("motivo"));

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) respuesta.put("id_reunion", keys.getInt(1));

            respuesta.putAll(body);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return respuesta;
    }

    @PutMapping("/{id}")
    public String updateReunion(@PathVariable int id, @RequestBody Map<String, Object> body) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "UPDATE reuniones SET profesor_id=?, alumno_id=?, fecha=?, motivo=? WHERE id_reunion=?"
            );

            stmt.setInt(1, (int) body.get("profesor_id"));
            stmt.setInt(2, (int) body.get("alumno_id"));
            stmt.setString(3, (String) body.get("fecha"));
            stmt.setString(4, (String) body.get("motivo"));
            stmt.setInt(5, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error actualizando reunión";
        }

        return "Reunión actualizada";
    }

    @DeleteMapping("/{id}")
    public String deleteReunion(@PathVariable int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM reuniones WHERE id_reunion = ?"
            );
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error eliminando reunión";
        }

        return "Reunión eliminada";
    }
}
