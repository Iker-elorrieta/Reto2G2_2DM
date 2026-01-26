package controlador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import modelo.HiloServidor;

@SpringBootApplication(scanBasePackages = {"com.EloServ.EloServ"})
public class Servidor {

    public static void main(String[] args) {
        SpringApplication.run(Servidor.class, args);
        System.out.println("--- API Spring Boot arrancada correctamente ---");

        Thread hiloSocket = new Thread(() -> {
            try (ServerSocket servidor = new ServerSocket(4500)) {
                System.out.println("--- Servidor Socket encendido en el puerto 4500 ---");

                while (true) {
                    Socket conexionCli = servidor.accept();
                    System.out.println("Cliente conectado al Socket");

                    HiloServidor hiloServidor = new HiloServidor(conexionCli);
                    hiloServidor.start();
                }
            } catch (IOException e) {
                System.err.println("Error en el servidor de sockets: " + e.getMessage());
                e.printStackTrace();
            }
        });

        hiloSocket.start();
    }
}