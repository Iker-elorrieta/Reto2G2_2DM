package com.EloServ.EloServ;

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
@RequestMapping("/ciclos")
@CrossOrigin
public class CicloController {

    private Connection connection;

    public CicloController() {
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
    public List<Map<String, Object>> getCiclos() {
        List<Map<String, Object>> lista = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ciclos");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id"));
                fila.put("nombre", rs.getString("nombre"));
                lista.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @PostMapping
    public Map<String, Object> createCiclo(@RequestBody Map<String, Object> body) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO ciclos (nombre) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, (String) body.get("nombre"));
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
