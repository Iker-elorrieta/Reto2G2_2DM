package com.EloServ.EloServ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/horarios")
@CrossOrigin
public class HorarioController {

    private Connection connection;

    public HorarioController() {
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
    public List<Map<String, Object>> getHorarios() {
        List<Map<String, Object>> lista = new ArrayList<>();

        String query = """
            SELECT h.*, m.nombre AS nombre_modulo, u.nombre AS nombre_profe
            FROM horarios h
            JOIN modulos m ON h.modulo_id = m.id
            JOIN users u ON h.profe_id = u.id
        """;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id"));
                fila.put("dia", rs.getString("dia"));
                fila.put("hora", rs.getString("hora"));
                fila.put("modulo_id", rs.getInt("modulo_id"));
                fila.put("profe_id", rs.getInt("profe_id"));
                fila.put("nombre_modulo", rs.getString("nombre_modulo"));
                fila.put("nombre_profe", rs.getString("nombre_profe"));
                lista.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
