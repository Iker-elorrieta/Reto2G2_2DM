package com.EloServ.EloServ;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.Session;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import modelo.HibernateUtil;
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
	private final String ProfesorId = "profesorId";
	

    public HorarioController() {
    }

    @GetMapping
    public List<Map<String, Object>> getHorariosFiltrados(
        @RequestParam(Ciclo) int cicloId,
        @RequestParam(Curso) int curso
    ) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            return session.createQuery(
                    "select h from Horarios h " +
                    "join fetch h.modulos m " +
                    "join fetch m.ciclos c " +
                    "where c.id = :cicloId and m.curso = :curso",
                    Horarios.class
                )
                .setParameter(CicloId, cicloId)
                .setParameter(Curso, curso)
                .getResultStream()
                .map(h -> {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put(Id, h.getId());
                    fila.put(Dia, h.getDia().toUpperCase());
                    fila.put(Hora, h.getHora());
                    fila.put(Aula, h.getAula());
                    fila.put(Nombre_modulo, h.getModulos().getNombre());
                    fila.put(Ciclo_id, h.getModulos().getCiclos().getId());
                    fila.put(Curso, h.getModulos().getCurso());
                    return fila;
                })
                .toList();
        }
    }
    @GetMapping("/profesor/{profesorId}")
    public List<Map<String, Object>> getHorariosProfesor(@PathVariable(ProfesorId) String profesorId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Map<String, Object>> respuesta = new java.util.ArrayList<>();

        try {
            List<Horarios> lista = session.createQuery(
                    "select h from Horarios h " +
                    "join fetch h.modulos m " +
                    "where h.users.id = :profesorId", 
                    Horarios.class
                )
                .setParameter(ProfesorId, Integer.parseInt(profesorId))
                .list();

            for (Horarios h : lista) {
                Map<String, Object> fila = new HashMap<>();
                fila.put(Id, h.getId());
                fila.put(Dia, h.getDia());
                fila.put(Hora, h.getHora());
                fila.put(Aula, h.getAula());
                
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