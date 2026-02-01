package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class HiloServidor extends Thread {

    Socket conexionCli;

    public HiloServidor(Socket conexionCli) {
        this.conexionCli = conexionCli;
    }

    @Override
    public void run() {
        int opcion = 0;
        boolean terminar = false;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            oos = new ObjectOutputStream(conexionCli.getOutputStream());
            oos.flush(); 

            ois = new ObjectInputStream(conexionCli.getInputStream());

            while (!terminar) {
                try {
                    opcion = ois.readInt(); 
                } catch (SocketException | java.io.EOFException e) {
                    System.out.println("Cliente desconectado.");
                    terminar = true;
                    break;
                }

                switch (opcion) {
                    case 1:
                        login(ois, oos);
                        break;
                    case 2:
                        verHorario(ois, oos);
                        break;
                    case 3:
                        verDatosUsuario(ois, oos);
                        break;
                    case 5:
                        enviarListaProfesores(ois, oos);
                        break;
                    case 6:
                        enviarHorarioProfesor(ois, oos);
                        break;
                    case 7:
                        enviarAlumnosProfesor(ois, oos);
                        break;
                    case 8: 
                    	enviarReunionesUsuario(ois, oos);
                    	break;
                    case 9: // NUEVO: Para la tabla de gestión con JSON
                        int idProf = ois.readInt();
                        // Usamos el método que creamos en Users.java o LogicaDatos
                        Object[][] datosGestion = new Users().getReunionesPendientes(idProf);
                        oos.writeObject(datosGestion);
                        oos.flush();
                        break;

                    case 10: // NUEVO: Para actualizar estado (Aceptar/Rechazar)
                        int idReu = ois.readInt();
                        String nuevoEstado = ois.readUTF();
                        boolean ok = new Users().actualizarEstadoReunion(idReu, nuevoEstado);
                        oos.writeBoolean(ok);
                        oos.flush();
                        break;
                    case 4:
                        terminar = true;
                        break;
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) ois.close();
                if (oos != null) oos.close();
                if (conexionCli != null) conexionCli.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void login(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            String usuario = ois.readUTF();
            String password = ois.readUTF();
            int usuarioComprobado = new Users().login(usuario, password);
            oos.writeInt(usuarioComprobado);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verHorario(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            int idUsuario = ois.readInt();
            System.out.println("Recibido idUsuario: " + idUsuario);

            String[][] horario = new Users().getHorarioById(idUsuario);

            oos.writeObject(horario);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verDatosUsuario(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            int idUsuario = ois.readInt();
            String[] datos = new Users().getDatosUsuarioById(idUsuario);
            oos.writeObject(datos);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarListaProfesores(ObjectInputStream ois, ObjectOutputStream oos)
            throws IOException {
        int idUsuario = ois.readInt();
        List<String> profes = new Users().getOtrosProfes(idUsuario);

        oos.writeObject(profes); 
        oos.flush();
    }

    private void enviarHorarioProfesor(ObjectInputStream ois, ObjectOutputStream oos)
            throws IOException {
        int idProfesor = ois.readInt();
        String[][] horario = new Users().getHorarioById(idProfesor);
        oos.writeObject(horario);
        oos.flush();
    }

    private void enviarAlumnosProfesor(ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        int idProfesor = ois.readInt();
        Object[][] alumnos = new Users().getAlumnosDelProfesor(idProfesor);
        oos.writeObject(alumnos);
        oos.flush();
    }
    
    private void enviarReunionesUsuario(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            int idUsuario = ois.readInt();
            Reuniones modeloReuniones = new Reuniones();
            List<Reuniones> listaOriginal = modeloReuniones.getReunionesDelUsuario(idUsuario);

            // CREAMOS UNA LISTA NUEVA "LIMPIA"
            List<Reuniones> listaLimpia = new ArrayList<>();

            for (Reuniones r : listaOriginal) {
                // Creamos un objeto nuevo para romper el vínculo con Hibernate
                Reuniones limpia = new Reuniones();
                
                // Copiamos los datos básicos uno a uno
                limpia.setTitulo(r.getTitulo());
                limpia.setAsunto(r.getAsunto());
                limpia.setAula(r.getAula());
                limpia.setFecha(r.getFecha());
                limpia.setEstado(r.getEstado());
                
                // Si necesitas el nombre del profesor, saca el String, no el objeto Users entero
                // porque el objeto Users también puede ser un proxy de Hibernate.
                
                listaLimpia.add(limpia);
            }

            // Enviamos la lista que no tiene rastros de Hibernate
            oos.writeObject(listaLimpia);
            oos.flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}