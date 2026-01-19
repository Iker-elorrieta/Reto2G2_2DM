package controlador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.EloServ.EloServ.EloServApplication;

import modelo.HiloServidor;

public class Servidor {

    public static void main(String[] args) {

        new Thread(() -> EloServApplication.main(new String[] {})).start();
        
        System.out.println("Spring Boot arrancado");

        try (ServerSocket servidor = new ServerSocket(4500)) {
            System.out.println("Servidor socket encendido en el puerto 4500");

            while (true) {
                Socket conexionCli = servidor.accept();
                System.out.println("Cliente conectado");
                HiloServidor hiloServidor = new HiloServidor(conexionCli);
                hiloServidor.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
