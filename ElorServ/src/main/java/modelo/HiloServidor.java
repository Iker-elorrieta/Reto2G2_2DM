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
		// TODO Auto-generated constructor stub
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
			// AQUI RELLENAMOS EL RESTO DE METODOS,INCREMENTAD EL NUMERO DEL CASE Y EL VALOR A ENVIAR BASADO EN EL NUMERO DE OPCIONES
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//de aqui se llama al metodo que el servidor gestiona con los datos recibido de los campos recogidos desde controlador
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

}