package com.EloServ.EloServ;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import modelo.Ciclos;

@RestController
@RequestMapping("/ciclos")
@CrossOrigin
public class CicloController {
	
    @PersistenceContext
    private EntityManager entityManager; // Spring gestiona esto por ti

    @GetMapping
    public List<Ciclos> getCiclos() {
        return entityManager.createQuery("from Ciclos", Ciclos.class).getResultList();
    }

    @PostMapping
    @Transactional // Deja que Spring maneje el begin/commit por ti
    public Map<String, Object> createCiclo(@RequestBody Map<String, Object> body) {
        Ciclos ciclo = new Ciclos();
        ciclo.setNombre((String) body.get("nombre"));

        entityManager.persist(ciclo);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("id", ciclo.getId());
        respuesta.put("nombre", ciclo.getNombre());
        return respuesta;
    }
}
    