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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modulos")
@CrossOrigin
public class ModuloController {

    private Connection connection;

    public ModuloController() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/eduelorrieta",
                "root",
                ""
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public List<Map<String, Object>> getModulos() {
        List<Map<String, Object>> lista = new ArrayList<>();

        String query = """
            SELECT m.*, c.nombre AS nombre_ciclo
            FROM modulos m
            JOIN ciclos c ON m.ciclo_id = c.id
        """;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id"));
                fila.put("nombre", rs.getString("nombre"));
                fila.put("ciclo_id", rs.getInt("ciclo_id"));
                fila.put("nombre_ciclo", rs.getString("nombre_ciclo"));
                lista.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @PostMapping
    public Map<String, Object> createModulo(@RequestBody Map<String, Object> body) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO modulos (nombre, ciclo_id) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, (String) body.get("nombre"));
            stmt.setInt(2, (int) body.get("ciclo_id"));
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) respuesta.put("id", keys.getInt(1));

            respuesta.putAll(body);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return respuesta;
    }
}
