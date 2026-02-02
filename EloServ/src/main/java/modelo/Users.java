	package modelo;
	
	import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
	import java.util.List;
	import java.util.Set;
	
	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
	
	public class Users implements java.io.Serializable {
	
		private static final long serialVersionUID = 1L;
		private Integer id;
		private Tipos tipos;
		private String email;
		private String username;
		private String password;
		private String nombre;
		private String apellidos;
		private String dni;
		private String direccion;
		private String telefono1;
		private String telefono2;
		private String argazkiaUrl;
		private Timestamp createdAt;
		private Timestamp updatedAt;
		private Set<Matriculaciones> matriculacioneses = new HashSet<Matriculaciones>(0);
		private Set<Reuniones> reunionesesForAlumnoId = new HashSet<Reuniones>(0);
		private Set<Horarios> horarioses = new HashSet<Horarios>(0);
		private Set<Reuniones> reunionesesForProfesorId = new HashSet<Reuniones>(0);
	
		public Users() {
		}
	
		public Users(Tipos tipos, String email, String username, String password) {
			this.tipos = tipos;
			this.email = email;
			this.username = username;
			this.password = password;
		}
	
		public Users(Tipos tipos, String email, String username, String password, String nombre, String apellidos,
				String dni, String direccion, String telefono1, String telefono2, String argazkiaUrl, Timestamp createdAt,
				Timestamp updatedAt, Set<Matriculaciones> matriculacioneses, Set<Reuniones> reunionesesForAlumnoId, Set<Horarios> horarioses,
				Set<Reuniones> reunionesesForProfesorId) {
			this.tipos = tipos;
			this.email = email;
			this.username = username;
			this.password = password;
			this.nombre = nombre;
			this.apellidos = apellidos;
			this.dni = dni;
			this.direccion = direccion;
			this.telefono1 = telefono1;
			this.telefono2 = telefono2;
			this.argazkiaUrl = argazkiaUrl;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.matriculacioneses = matriculacioneses;
			this.reunionesesForAlumnoId = reunionesesForAlumnoId;
			this.horarioses = horarioses;
			this.reunionesesForProfesorId = reunionesesForProfesorId;
		}
	
		public Integer getId() {
			return this.id;
		}
	
		public void setId(Integer id) {
			this.id = id;
		}
	
		public Tipos getTipos() {
			return this.tipos;
		}
	
		public void setTipos(Tipos tipos) {
			this.tipos = tipos;
		}
	
		public String getEmail() {
			return this.email;
		}
	
		public void setEmail(String email) {
			this.email = email;
		}
	
		public String getUsername() {
			return this.username;
		}
	
		public void setUsername(String username) {
			this.username = username;
		}
	
		public String getPassword() {
			return this.password;
		}
	
		public void setPassword(String password) {
			this.password = password;
		}
	
		public String getNombre() {
			return this.nombre;
		}
	
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	
		public String getApellidos() {
			return this.apellidos;
		}
	
		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}
	
		public String getDni() {
			return this.dni;
		}
	
		public void setDni(String dni) {
			this.dni = dni;
		}
	
		public String getDireccion() {
			return this.direccion;
		}
	
		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}
	
		public String getTelefono1() {
			return this.telefono1;
		}
	
		public void setTelefono1(String telefono1) {
			this.telefono1 = telefono1;
		}
	
		public String getTelefono2() {
			return this.telefono2;
		}
	
		public void setTelefono2(String telefono2) {
			this.telefono2 = telefono2;
		}
	
		public String getArgazkiaUrl() {
			return this.argazkiaUrl;
		}
	
		public void setArgazkiaUrl(String argazkiaUrl) {
			this.argazkiaUrl = argazkiaUrl;
		}
	
		public Timestamp getCreatedAt() {
			return this.createdAt;
		}
	
		public void setCreatedAt(Timestamp createdAt) {
			this.createdAt = createdAt;
		}
	
		public Timestamp getUpdatedAt() {
			return this.updatedAt;
		}
	
		public void setUpdatedAt(Timestamp updatedAt) {
			this.updatedAt = updatedAt;
		}
	
		public Set<Matriculaciones> getMatriculacioneses() {
			return this.matriculacioneses;
		}
	
		public void setMatriculacioneses(Set<Matriculaciones> matriculacioneses) {
			this.matriculacioneses = matriculacioneses;
		}
	
		public Set<Reuniones> getReunionesesForAlumnoId() {
			return this.reunionesesForAlumnoId;
		}
	
		public void setReunionesesForAlumnoId(Set<Reuniones> reunionesesForAlumnoId) {
			this.reunionesesForAlumnoId = reunionesesForAlumnoId;
		}
	
		public Set<Horarios> getHorarioses() {
			return this.horarioses;
		}
	
		public void setHorarioses(Set<Horarios> horarioses) {
			this.horarioses = horarioses;
		}
	
		public Set<Reuniones> getReunionesesForProfesorId() {
			return this.reunionesesForProfesorId;
		}
	
		public void setReunionesesForProfesorId(Set<Reuniones> reunionesesForProfesorId) {
			this.reunionesesForProfesorId = reunionesesForProfesorId;
		}
		
		
		@Override
		public String toString() {
			return "Users [id=" + id + ", tipos=" + tipos + ", email=" + email + ", username=" + username + ", password="
					+ password + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", direccion="
					+ direccion + ", telefono1=" + telefono1 + ", telefono2=" + telefono2 + ", argazkiaUrl=" + argazkiaUrl
					+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", matriculacioneses=" + matriculacioneses
					+ ", reunionesesForAlumnoId=" + reunionesesForAlumnoId + ", horarioses=" + horarioses
					+ ", reunionesesForProfesorId=" + reunionesesForProfesorId + "]";
		}
		
		public int login(String usuario, String contrasena) {
			Session session = HibernateUtil.getSessionFactory().openSession();
		    String hql = "FROM Users u WHERE u.username = :usuario " +
		                 "AND u.password = :contrasena " +
		                 "AND u.tipos.name = 'profesor'";
		    Query<Users> query = session.createQuery(hql, Users.class);
		    query.setParameter("usuario", usuario);
		    query.setParameter("contrasena", contrasena);
		    Users usuarioComprobado = query.uniqueResult();
		    session.close();
	
		    if (usuarioComprobado != null) {
		        return usuarioComprobado.getId();
		    } else {
		        return 0; 
		    }
	    }
		
		public String[] getDatosUsuarioById(int idUsuario) {
	
		    String[] datos = new String[7];
	
		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		    Session session = sessionFactory.openSession();
	
		    Users u = session.find(Users.class, idUsuario);
	
		    if (u != null) {
		        datos[0] = u.getNombre();
		        datos[1] = u.getApellidos();
		        datos[2] = u.getEmail();
		        datos[3] = u.getTelefono1();		    
		        datos[4] = u.getTelefono2();
		        datos[5] = u.getDireccion();
		        datos[6] = u.getUsername();

		    }
	
		    session.close();
		    return datos;
		}
	
		
		
		public String[][] getHorarioById(int idUsuario) {
		    String[][] horarioModelo = {
		        { "Hora 1", "", "", "", "", "" },
		        { "Hora 2", "", "", "", "", "" },
		        { "Hora 3", "", "", "", "", "" },
		        { "Hora 4", "", "", "", "", "" },
		        { "Hora 5", "", "", "", "", "" },
		        { "Hora 6", "", "", "", "", "" }
		    };

		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		    try (Session session = sessionFactory.openSession()) {
		        Users usuarioRef = session.getReference(Users.class, idUsuario);

		        // 1. Obtener Horarios (Clases)
		        String hqlHorario = "FROM Horarios h WHERE h.users = :userObj";
		        List<Horarios> listaClases = session.createQuery(hqlHorario, Horarios.class)
		                                            .setParameter("userObj", usuarioRef).getResultList();

		     // 2. Obtener Reuniones (DENTRO DE getHorarioById)
		     // Añadimos el filtro para que traiga pendientes Y aceptadas
		     String hqlReuniones = "FROM Reuniones r WHERE r.usersByProfesorId = :userObj " +
		                           "AND (r.estado = 'pendiente' OR r.estado = 'aceptada')";

		     List<Reuniones> listaReuniones = session.createQuery(hqlReuniones, Reuniones.class)
		                                             .setParameter("userObj", usuarioRef).getResultList();

		        // Mapear Clases al modelo
		        for (Horarios h : listaClases) {
		            int fila = h.getHora() - 1;
		            int col = conseguirDia(h.getDia());
		            if (fila >= 0 && fila < 6 && col > 0) {
		            	if(h.getAula()!=null) {
		            		horarioModelo[fila][col] = h.getModulos().getNombre() + "\n" + h.getAula();
		            	}else{
		            		horarioModelo[fila][col] = h.getModulos().getNombre();
		            	};
		            }
		        }

		        // Superponer Reuniones y añadir etiquetas de estado
		        for (Reuniones r : listaReuniones) {
		            // Aquí necesitas una lógica para saber qué hora y día es la reunión
		            // basándote en el campo 'fecha' de la tabla Reuniones
		            int fila = extraerHoraDeFecha(r.getFecha()); 
		            int col = extraerDiaDeFecha(r.getFecha());

		            if (fila >= 0 && col > 0) {
		                String contenidoActual = horarioModelo[fila][col];
		                String etiquetaReunion = "REUNIÓN: " + r.getTitulo() + " [" + r.getEstado() + "]";
		                
		                if (contenidoActual.isEmpty()) {
		                    horarioModelo[fila][col] = etiquetaReunion;
		                } else {
		                    // Si ya había clase, se crea el "Conflicto" (Gris en tu imagen)
		                    horarioModelo[fila][col] = contenidoActual + " / " + etiquetaReunion;
		                }
		            }
		        }
		    } catch (Exception e) { e.printStackTrace(); }
		    return horarioModelo;
		}
		


		
		
		private int conseguirDia(String diaBD) {
		    if (diaBD == null) return 0;
		    diaBD = diaBD.trim().toUpperCase();

		    switch (diaBD) {

		        case "LUNES":
		            return 1;
		        case "MARTES":
		            return 2;
		        case "MIERCOLES":
		        case "MIÉRCOLES":
		            return 3;
		            
		        case "JUEVES":
		            return 4;
		            
		        case "VIERNES":
		            return 5;
		        default:
		            System.out.println("Dia no reconocido: " + diaBD);
		            return 0;
		    }
		}
	
		public List<String> getOtrosProfes(int idUsuario) {
		    List<String> profesores = new ArrayList<>();
		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		    try (Session session = sessionFactory.openSession()) {
		        Users usuarioRef = session.getReference(Users.class, idUsuario);
		        
		        String hql = "FROM Users u WHERE u <> :userObj AND u.tipos.name = 'profesor'";

		        Query<Users> query = session.createQuery(hql, Users.class);
		        query.setParameter("userObj", usuarioRef);

		        List<Users> filas = query.getResultList();

		        for (Users usuario : filas) {
		            profesores.add(usuario.getId() + ";" + usuario.getNombre());
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return profesores;
		}
		
		public Object[][] getAlumnosDelProfesor(int profeId) {
		    SessionFactory sf = HibernateUtil.getSessionFactory();
		    List<Users> alumnos;

		    try (Session session = sf.openSession()) {
		        Users profeRef = session.getReference(Users.class, profeId);

		        String hql = "SELECT DISTINCT mat.users FROM Matriculaciones mat " +
		                     "WHERE mat.users.tipos.name = 'alumno' " +
		                     "AND mat.ciclos.id IN (" +
		                     "  SELECT h.modulos.ciclos.id FROM Horarios h WHERE h.users = :profeObj" +
		                     ")";

		        Query<Users> q = session.createQuery(hql, Users.class);
		        q.setParameter("profeObj", profeRef); // Pasamos el objeto, no el ID
		        alumnos = q.getResultList();
		    }
		    Object[][] datos = new Object[alumnos.size()][7];
		    for (int i = 0; i < alumnos.size(); i++) {
		        Users u = alumnos.get(i);
		        datos[i][0] = u.getNombre();
		        datos[i][1] = u.getApellidos();
		        datos[i][2] = u.getEmail();
		        datos[i][3] = u.getTelefono1();
		        datos[i][4] = u.getTelefono2();
		        datos[i][5] = u.getDireccion();
		        datos[i][6] = u.getUsername();
		    }

		    return datos;
		}

		private int extraerDiaDeFecha(java.sql.Timestamp fecha) {
		    if (fecha == null) return 0;
		    java.util.Calendar cal = java.util.Calendar.getInstance();
		    cal.setTime(fecha);
		    
		    // Calendar.DAY_OF_WEEK: Domingo=1, Lunes=2, Martes=3...
		    int diaSemana = cal.get(java.util.Calendar.DAY_OF_WEEK);
		    
		    switch (diaSemana) {
		        case java.util.Calendar.MONDAY:    return 1;
		        case java.util.Calendar.TUESDAY:   return 2;
		        case java.util.Calendar.WEDNESDAY: return 3;
		        case java.util.Calendar.THURSDAY:  return 4;
		        case java.util.Calendar.FRIDAY:    return 5;
		        default: return 0; // Fin de semana u otros
		    }
		}

		private int extraerHoraDeFecha(java.sql.Timestamp fecha) {
		    if (fecha == null) return -1;
		    java.util.Calendar cal = java.util.Calendar.getInstance();
		    cal.setTime(fecha);
		    
		    int hora24 = cal.get(java.util.Calendar.HOUR_OF_DAY);
		    
		    // Mapeo según los tramos de tu centro (ejemplo estándar):
		    if (hora24 >= 8 && hora24 < 9)   return 0; // Hora 1
		    if (hora24 >= 9 && hora24 < 10)  return 1; // Hora 2
		    if (hora24 >= 10 && hora24 < 11) return 2; // Hora 3
		    if (hora24 >= 11 && hora24 < 12) return 3; // Hora 4
		    if (hora24 >= 12 && hora24 < 13) return 4; // Hora 5
		    if (hora24 >= 13 && hora24 < 14) return 5; // Hora 6
		    
		    return -1;
		}
		
		// Método para la tabla de gestión inferior
		public Object[][] getReunionesPendientes(int idProfesor) {
		    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
		        Users profesorRef = session.getReference(Users.class, idProfesor);

		        String hql = "FROM Reuniones r WHERE r.usersByProfesorId = :profeObj " +
		                     "AND (r.estado = 'pendiente' OR r.estado = 'conflicto')";
		        
		        List<Reuniones> lista = session.createQuery(hql, Reuniones.class)
		                                       .setParameter("profeObj", profesorRef)
		                                       .getResultList();

		        Object[][] datos = new Object[lista.size()][7];
		        for (int i = 0; i < lista.size(); i++) {
		            Reuniones r = lista.get(i);
		            
		            // TRATAMIENTO DE idCentro COMO STRING
		            String idCentroStr = r.getIdCentro();
		            int idC = 0;
		            
		            try {
		                if (idCentroStr != null && !idCentroStr.trim().isEmpty()) {
		                    idC = Integer.parseInt(idCentroStr.trim());
		                }
		            } catch (NumberFormatException e) {
		                System.err.println("Error: idCentro no es un número válido: " + idCentroStr);
		            }

		            String[] infoCentro = JSONManager.obtenerInfoCentro(idC);

		            datos[i][0] = r.getIdReunion();
		            datos[i][1] = r.getTitulo() != null ? r.getTitulo() : "Sin título";
		            datos[i][2] = infoCentro[0]; // Nombre
		            datos[i][3] = infoCentro[1]; // Municipio
		            datos[i][4] = r.getAula() != null ? r.getAula() : "N/A";
		            datos[i][5] = r.getFecha().toString();
		            datos[i][6] = r.getEstado();
		        }
		        return datos;
		    }
		}
		// Método para actualizar el estado
		public boolean actualizarEstadoReunion(int idReunion, String nuevoEstado) {
		    org.hibernate.Transaction tx = null;
		    try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
		        tx = session.beginTransaction();
		        
		        // En Hibernate 7.0+, usamos .find() en lugar de .get()
		        Reuniones r = session.find(Reuniones.class, idReunion);
		        
		        if (r != null) {
		            r.setEstado(nuevoEstado);
		            
		            // Usamos merge para asegurar que los cambios se guarden
		            session.merge(r); 
		            
		            tx.commit();
		            return true;
		        }
		    } catch (Exception e) {
		        if (tx != null) tx.rollback();
		        e.printStackTrace();
		    }
		    return false;
		}
	}
