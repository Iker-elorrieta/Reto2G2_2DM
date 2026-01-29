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
		        { "1ra", "", "", "", "", "" },
		        { "2da", "", "", "", "", "" },
		        { "3ra", "", "", "", "", "" },
		        { "4ta", "", "", "", "", "" },
		        { "5ta", "", "", "", "", "" },
		        { "6ta", "", "", "", "", "" }
		    };

		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		    try (Session session = sessionFactory.openSession()) {
		        Users usuarioRef = session.getReference(Users.class, idUsuario);

		        String hql = "FROM Horarios h WHERE h.users = :userObj";
		        Query<Horarios> query = session.createQuery(hql, Horarios.class);
		        query.setParameter("userObj", usuarioRef);

		        List<Horarios> filas = query.getResultList();
		        
		        for (Horarios horario : filas) {
		            int hora = horario.getHora();
		            int dia = conseguirDia(horario.getDia());

		            if (hora < 1 || hora > 6 || dia < 1 || dia > 5) {
		                continue;
		            }

		            // Validamos el nombre del módulo (por seguridad)
		            String nombreModulo = (horario.getModulos() != null) ? horario.getModulos().getNombre() : "S/N";

		            // Controlamos si el aula es null o no
		            String aulaInfo = "";
		            if (horario.getAula() != null && !horario.getAula().trim().isEmpty()) {
		                aulaInfo = " [" + horario.getAula() + "]"; // Solo añadimos el formato si existe el dato aula
		            }

		            // Concatenamos ambos
		            horarioModelo[hora - 1][dia] = nombreModulo + aulaInfo;
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

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
	
		public ArrayList<String> getOtrosProfes(int idUsuario) {

		    ArrayList<String> profesores = new ArrayList<>();

		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		    try (Session session = sessionFactory.openSession()) {

		        String hql = "FROM Users u WHERE u.id <> :idUsuario AND u.tipos.name = 'profesor'";

		        Query<Users> query = session.createQuery(hql, Users.class);
		        query.setParameter("idUsuario", idUsuario);

		        List<Users> filas = query.getResultList();

		        for (Users usuario : filas) {
		            profesores.add(usuario.getId() + ";" + usuario.getNombre());
		        }
		    }

		    return profesores;
		}
		
		public Object[][] getAlumnosDelProfesor(int profeId) {

		    SessionFactory sf = HibernateUtil.getSessionFactory();
		    List<Users> alumnos;
		    
		    try (Session session = sf.openSession()) {

		        String hql =
		        		  "SELECT DISTINCT mat.users FROM Matriculaciones mat WHERE mat.users.tipos.name = 'alumno' " +
		        		  "AND mat.ciclos.id IN (SELECT h.modulos.ciclos.id FROM Horarios h WHERE h.users.id = :profeId)";

		        Query<Users> q = session.createQuery(hql, Users.class);
		        q.setParameter("profeId", profeId);
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


	}
