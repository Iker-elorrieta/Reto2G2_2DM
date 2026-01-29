package com.EloServ.EloServ;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;

import modelo.Reuniones;
import modelo.Users;

@RestController
@RequestMapping("/reuniones")
@CrossOrigin
public class ReunionController {

	private final String Id_reunion = "id_reunion";
	private final String Estado = "estado";
	private final String Estado_eus = "estado_eus";
	private final String Id_centro = "id_centro";
	private final String Titulo = "titulo";
	private final String Asunto = "asunto";
	private final String Aula = "aula";
	private final String Fecha = "fecha";
	private final String Created_at = "created_at";
	private final String Updated_at = "updated_at";
	
	private final String Profesor_id = "profesor_id";
	private final String Nombre_profesor = "nombre_profesor";
	private final String Apellidos_profesor = "apellidos_profesor";
	
	private final String Alumno_id = "alumno_id";
	private final String Nombre_alumno = "nombre_alumno";
	private final String Apellidos_alumno = "apellidos_alumno";

	private final String Status = "status";
	private final String Error = "error";
	private final String Message = "message";
	private final String Success = "success";

	private final String Uid_Param = "uid";

	private final SessionFactory sessionFactory;

	public ReunionController() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	private Map<String, Object> mapReunion(Reuniones r) {
		Map<String, Object> fila = new HashMap<>();
		fila.put(Id_reunion, r.getIdReunion());
		fila.put(Estado, r.getEstado());
		fila.put(Estado_eus, r.getEstadoEus());
		fila.put(Id_centro, r.getIdCentro());
		fila.put(Titulo, r.getTitulo());
		fila.put(Asunto, r.getAsunto());
		fila.put(Aula, r.getAula());
		fila.put(Fecha, r.getFecha());
		fila.put(Created_at, r.getCreatedAt());
		fila.put(Updated_at, r.getUpdatedAt());

		if (r.getUsersByProfesorId() != null) {
			fila.put(Profesor_id, r.getUsersByProfesorId().getId());
			fila.put(Nombre_profesor, r.getUsersByProfesorId().getNombre());
			fila.put(Apellidos_profesor, r.getUsersByProfesorId().getApellidos());
		}

		if (r.getUsersByAlumnoId() != null) {
			fila.put(Alumno_id, r.getUsersByAlumnoId().getId());
			fila.put(Nombre_alumno, r.getUsersByAlumnoId().getNombre());
			fila.put(Apellidos_alumno, r.getUsersByAlumnoId().getApellidos());
		}
		return fila;
	}

	@GetMapping
	public List<Map<String, Object>> getReuniones() {
		try (Session session = sessionFactory.openSession()) {
			List<Reuniones> lista = session.createQuery("select r from Reuniones r "
					+ "left join fetch r.usersByProfesorId p " + "left join fetch r.usersByAlumnoId a", Reuniones.class)
					.list();

			return lista.stream().map(this::mapReunion).collect(Collectors.toList());
		}
	}

	@GetMapping("/usuario/{userId}")
	public List<Map<String, Object>> getReunionesByUsuario(@PathVariable("userId") int userId) {
		try (Session session = sessionFactory.openSession()) {
			List<Reuniones> lista = session.createQuery(
					"select r from Reuniones r " + "left join fetch r.usersByProfesorId p "
							+ "left join fetch r.usersByAlumnoId a " + "where p.id = :" + Uid_Param + " or a.id = :" + Uid_Param,
					Reuniones.class).setParameter(Uid_Param, userId).list();

			return lista.stream().map(this::mapReunion).collect(Collectors.toList());
		}
	}

	@PostMapping
	public Map<String, Object> createReunion(@RequestBody Map<String, Object> body) {
		try (Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			try {
				int profesorId = body.get(Profesor_id) != null ? ((Number) body.get(Profesor_id)).intValue() : 0;
				int alumnoId = body.get(Alumno_id) != null ? ((Number) body.get(Alumno_id)).intValue() : 0;

				if (profesorId == 0 || alumnoId == 0) {
					throw new RuntimeException("ID de profesor o alumno no válido");
				}

				Users profesor = session.find(Users.class, profesorId);
				Users alumno = session.find(Users.class, alumnoId);

				if (profesor == null || alumno == null) {
					throw new RuntimeException("El profesor o el alumno no existen en la base de datos");
				}

				Reuniones r = new Reuniones();
				r.setEstado(body.get(Estado) != null ? (String) body.get(Estado) : "pendiente");
				r.setEstadoEus((String) body.get(Estado_eus));
				r.setUsersByProfesorId(profesor);
				r.setUsersByAlumnoId(alumno);
				r.setIdCentro(String.valueOf(body.get(Id_centro)));
				r.setTitulo((String) body.get(Titulo));
				r.setAsunto((String) body.get(Asunto));
				r.setAula((String) body.get(Aula));

				String fechaStr = (String) body.get(Fecha);
				if (fechaStr != null && !fechaStr.isEmpty()) {
					String isoFecha = fechaStr.replace(" ", "T");
					if (isoFecha.length() == 10) isoFecha += "T00:00:00";
					else if (isoFecha.length() == 16) isoFecha += ":00";
					r.setFecha(java.sql.Timestamp.valueOf(java.time.LocalDateTime.parse(isoFecha.substring(0, 19))));
				}

				java.sql.Timestamp ahora = new java.sql.Timestamp(System.currentTimeMillis());
				r.setCreatedAt(ahora);
				r.setUpdatedAt(ahora);

				session.persist(r);
				tx.commit();

				Map<String, Object> respuesta = new HashMap<>(body);
				respuesta.put(Id_reunion, r.getIdReunion());
				return respuesta;
			} catch (Exception e) {
				if (tx != null) tx.rollback();
				e.printStackTrace();
				throw e;
			}
		}
	}

	@PutMapping("/{id}")
	public Map<String, String> updateReunion(@PathVariable int id, @RequestBody Map<String, Object> body) {
	    try (Session session = sessionFactory.openSession()) {
	        Transaction tx = session.beginTransaction();
	        try {
	            Reuniones r = session.find(Reuniones.class, id);
	            Map<String, String> response = new HashMap<>();
	            if (r == null) {
	                response.put(Error, "Reunión no encontrada");
	                return response;
	            }

	            int profesorId = ((Number) body.get(Profesor_id)).intValue();
	            int alumnoId = ((Number) body.get(Alumno_id)).intValue();
	            Users profesor = session.find(Users.class, profesorId);
	            Users alumno = session.find(Users.class, alumnoId);

	            r.setEstado((String) body.get(Estado));
	            r.setEstadoEus((String) body.get(Estado_eus));
	            r.setUsersByProfesorId(profesor);
	            r.setUsersByAlumnoId(alumno);
	            r.setIdCentro(String.valueOf(body.get(Id_centro)));
	            r.setTitulo((String) body.get(Titulo));
	            r.setAsunto((String) body.get(Asunto));
	            r.setAula((String) body.get(Aula));

	            String fechaStr = (String) body.get(Fecha);
	            if (fechaStr != null && !fechaStr.isEmpty()) {
	                String limpia = fechaStr.replace("T", " ");
	                if (limpia.length() == 10) limpia += " 00:00:00";
	                if (limpia.length() > 19) limpia = limpia.substring(0, 19);
	                r.setFecha(java.sql.Timestamp.valueOf(limpia));
	            }

	            r.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
	            session.merge(r);
	            tx.commit();
	            response.put(Message, "Reunión actualizada");
	            return response;
	        } catch (Exception e) {
	            if (tx != null) tx.rollback();
	            throw e;
	        }
	    }
	}

	@DeleteMapping("/{id}")
	public Map<String, String> deleteReunion(@PathVariable int id) {
	    try (Session session = sessionFactory.openSession()) {
	        Transaction tx = session.beginTransaction();
	        try {
	            Reuniones r = session.find(Reuniones.class, id);
	            Map<String, String> response = new HashMap<>();

	            if (r == null) {
	                response.put(Status, Error);
	                response.put(Message, "Reunión no encontrada");
	                return response;
	            }

	            session.remove(r);
	            tx.commit();

	            response.put(Status, Success);
	            response.put(Message, "Reunión eliminada");
	            return response;
	        } catch (Exception e) {
	            if (tx != null) tx.rollback();
	            throw e;
	        }
	    }
	}
}