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
@RequestMapping("/matriculaciones")
@CrossOrigin
public class MatriculacionesController {

    private Connection connection;

    public MatriculacionesController() {
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
    public List<Map<String, Object>> getMatriculaciones() {
        List<Map<String, Object>> lista = new ArrayList<>();

        String query = """
            SELECT m.*, u.nombre AS nombre_alumno, c.nombre AS nombre_ciclo
            FROM matriculaciones m
            JOIN users u ON m.alum_id = u.id
            JOIN ciclos c ON m.ciclo_id = c.id
        """;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id"));
                fila.put("alum_id", rs.getInt("alum_id"));
                fila.put("ciclo_id", rs.getInt("ciclo_id"));
                fila.put("fecha", rs.getString("fecha"));
                fila.put("nombre_alumno", rs.getString("nombre_alumno"));
                fila.put("nombre_ciclo", rs.getString("nombre_ciclo"));
                lista.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}

