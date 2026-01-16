package com.Reto2.ElorServ;

import java.sql.*;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	private Connection connection;

	public UserController() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eduelorrieta", "root", "");
			System.out.println("Conexión exitosa a MySQL");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ============================================
	// GET /users
	// Devuelve todos los usuarios con todos sus campos
	// ============================================
	@GetMapping
	public ResponseEntity<List<Map<String, Object>>> getUsers() {
		List<Map<String, Object>> lista = new ArrayList<>();

		try {
			// Usamos * para traer todas las columnas de la tabla
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users");
			ResultSet rs = stmt.executeQuery();

			// Para obtener los nombres de las columnas dinámicamente
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				Map<String, Object> fila = new HashMap<>();

				// Este bucle recorre todas las columnas automáticamente
				// sin tener que escribir fila.put para cada una
				for (int i = 1; i <= columnCount; i++) {
					String nombreColumna = metaData.getColumnName(i);
					fila.put(nombreColumna, rs.getObject(i));
				}

				lista.add(fila);
			}
			return ResponseEntity.ok(lista);

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// ============================================
	// GET /users/{id} - CORREGIDO
	// ============================================
	@GetMapping("/{id}")
	public Map<String, Object> getUserById(@PathVariable("id") int id) {
		Map<String, Object> fila = new HashMap<>();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				fila.put("id", rs.getInt("id"));
				fila.put("username", rs.getString("username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fila;
	}

	// ============================================
	// PUT /users/{id} - CORREGIDO
	// ============================================
	@PutMapping("/{id}")
	public String updateUser(@PathVariable("id") int id, @RequestBody Map<String, Object> body) {
		try {
			PreparedStatement stmt = connection.prepareStatement("UPDATE users SET username=? WHERE id=?");
			stmt.setString(1, (String) body.get("username"));
			stmt.setInt(2, id);
			stmt.executeUpdate();
			return "Actualizado";
		} catch (SQLException e) {
			return "Error";
		}
	}

	// ============================================
	// DELETE /users/{id} - CORREGIDO
	// ============================================
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		try {
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return "Eliminado";
		} catch (SQLException e) {
			return "Error";
		}
	}

	// ============================================
	// LOGIN SIMPLIFICADO (TRUE/FALSE)
	// ============================================
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
		Map<String, Object> respuesta = new HashMap<>();
		String username = credentials.get("username");
		String password = credentials.get("password");

		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				respuesta.put("id", rs.getInt("id"));
				respuesta.put("username", rs.getString("username"));
				respuesta.put("tipo_id", rs.getInt("tipo_id"));
				respuesta.put("login_status", "success");
				return ResponseEntity.ok(respuesta);
			} else {
				respuesta.put("login_status", "error");
				return ResponseEntity.status(401).body(respuesta);
			}
		} catch (SQLException e) {
			return ResponseEntity.status(500).build();
		}
	}
}