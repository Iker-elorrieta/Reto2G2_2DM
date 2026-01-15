package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;


import javax.swing.JTextArea;

public class HiloRecibir  extends Thread {
    private ObjectInputStream entrada;
    private JTextArea textArea;

    public HiloRecibir(ObjectInputStream entrada, JTextArea textArea) {
        this.entrada = entrada;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {
            String mensaje;
            while ((mensaje = (String) entrada.readObject()) != null) {
                textArea.append(mensaje + "\n");
            }
        } catch (IOException | ClassNotFoundException e) {
            textArea.append("Desconectado del servidor.\n");
            e.printStackTrace();
        }
    }
}