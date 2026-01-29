package com.EloServ.EloServ;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import modelo.Horarios;

@RestController
@RequestMapping("/horarios")
@CrossOrigin
public class HorarioController {

	private final String Ciclo = "ciclo";
	private final String Curso = "curso";
	private final String Id = "id";
	private final String Dia = "dia";
	private final String Hora = "hora";
	private final String Aula = "aula";
	private final String Nombre_modulo = "nombre_modulo";
	private final String Ciclo_id = "ciclo_id";
	private final String CicloId = "cicloId";
	
    private SessionFactory sessionFactory;

    public HorarioController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @GetMapping
    public List<Map<String, Object>> getHorariosFiltrados(
        @RequestParam(Ciclo) int cicloId,
        @RequestParam(Curso) int curso
    ) {
        Session session = sessionFactory.openSession();

        // Consulta filtrada directamente en la BD
        List<Horarios> lista = session.createQuery(
        	    "select h from Horarios h " +
        	    "join fetch h.modulos m " +
        	    "join fetch m.ciclos c " +
        	    "where c.id = :cicloId and m.curso = :curso",
        	    Horarios.class
        	)
        	.setParameter(CicloId, cicloId)
        	.setParameter(Curso, curso)
        	.list();


        session.close();

        return lista.stream().map(h -> {
            Map<String, Object> fila = new HashMap<>();
            fila.put(Id, h.getId());
            fila.put(Dia, h.getDia().toUpperCase());
            fila.put(Hora, h.getHora());
            fila.put(Aula, h.getAula());
            fila.put(Nombre_modulo, h.getModulos().getNombre());
            fila.put(Ciclo_id, h.getModulos().getCiclos().getId());
            fila.put("curso", h.getModulos().getCurso());
            return fila;
        }).toList();
    }
    @GetMapping("/profesor/{profesorId}")
    public List<Map<String, Object>> getHorariosProfesor(@PathVariable("profesorId") String profesorId) {
        Session session = sessionFactory.openSession();
        List<Map<String, Object>> respuesta = new java.util.ArrayList<>();

        try {
            // Corregido: h.users.id coincide con el atributo 'users' de tu clase Horarios
            List<Horarios> lista = session.createQuery(
                    "select h from Horarios h " +
                    "join fetch h.modulos m " +
                    "where h.users.id = :profesorId", 
                    Horarios.class
                )
                .setParameter("profesorId", Integer.parseInt(profesorId))
                .list();

            for (Horarios h : lista) {
                Map<String, Object> fila = new HashMap<>();
                fila.put(Id, h.getId());
                fila.put(Dia, h.getDia());
                fila.put(Hora, h.getHora());
                fila.put(Aula, h.getAula());
                
                // Accedemos al nombre del módulo a través de la relación
                if (h.getModulos() != null) {
                    fila.put(Nombre_modulo, h.getModulos().getNombre());
                }
                
                respuesta.add(fila);
            }
        } catch (Exception e) {
            System.err.println("Error en la consulta de horarios: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }

        return respuesta;
    }
}