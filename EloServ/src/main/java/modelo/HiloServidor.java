package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
	

public class HiloServidor extends Thread {

	Socket conexionCli;

	public HiloServidor(Socket conexionCli) {
		this.conexionCli = conexionCli;
	}

	public void run() {
		int opcion = 0;
		boolean terminar = false;

		
		try {
			DataOutputStream dos = new DataOutputStream(conexionCli.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(conexionCli.getOutputStream());
			DataInputStream dis = new DataInputStream(conexionCli.getInputStream());
			ObjectInputStream ois = new ObjectInputStream(conexionCli.getInputStream());

			oos.flush();
			while (!terminar) {

				opcion = dis.readInt();
				switch (opcion) {
				case 1:
					login(dis, dos);
					break;
				case 2:
					verHorario(dis, oos);
					break;
				case 3: 
					verDatosUsuario(dis, oos);

				case 4:
					terminar = true;
					break;
				default:

					break;
				}
			}
			ois.close();
			dis.close();
			oos.close();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login(DataInputStream dis, DataOutputStream dos) {
		// TODO Auto-generated method stub

		try {
			String usuario = dis.readUTF();
			String password = dis.readUTF();
			int usuarioComprobado = new Users().login(usuario, password);
			dos.writeInt(usuarioComprobado);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void verHorario(DataInputStream dis, ObjectOutputStream dos) { //
		try {
			int idUsuario = dis.readInt();
			String[][] horario = new Users().getHorarioById(idUsuario);
			dos.writeObject(horario);
			dos.flush();
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void verDatosUsuario(DataInputStream dis, ObjectOutputStream oos) {
	    try {
	        int idUsuario = dis.readInt();

	        String[] datos = new Users().getDatosUsuarioById(idUsuario);

	        oos.writeObject(datos);
	        oos.flush();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}