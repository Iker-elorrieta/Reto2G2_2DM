package vista;

import javax.swing.*;
import java.awt.*;

public class PanelReuniones extends JPanel {
    private static final long serialVersionUID = 1L;
	private JTable tablaHorario, tablaGestion;
    private JButton btnAceptar, btnRechazar, btnVolver;

    public PanelReuniones() {
        setLayout(null);
        setBounds(0, 0, 750, 600);

        JLabel t1 = new JLabel("HORARIO VISUAL DE REUNIONES");
        t1.setBounds(20, 10, 300, 25);
        add(t1);

        tablaHorario = new JTable();
        JScrollPane sp1 = new JScrollPane(tablaHorario);
        sp1.setBounds(20, 40, 700, 200);
        add(sp1);

        JLabel t2 = new JLabel("GESTIÓN DE REUNIONES PENDIENTES (Datos JSON)");
        t2.setBounds(20, 255, 400, 25);
        add(t2);

        tablaGestion = new JTable();
        JScrollPane sp2 = new JScrollPane(tablaGestion);
        sp2.setBounds(20, 285, 700, 150);
        add(sp2);

        btnAceptar = new JButton("ACEPTAR");
        btnAceptar.setBounds(200, 450, 120, 35);
        btnAceptar.setBackground(new Color(144, 238, 144));
        add(btnAceptar);

        btnRechazar = new JButton("RECHAZAR");
        btnRechazar.setBounds(340, 450, 120, 35);
        btnRechazar.setBackground(new Color(255, 102, 102));
        add(btnRechazar);

        btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(290, 510, 100, 30);
        add(btnVolver);
    }

    // Getters para el controlador
    public JTable getTablaHorario() { return tablaHorario; }
    public JTable getTablaGestion() { return tablaGestion; }
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnRechazar() { return btnRechazar; }
    public JButton getBtnVolver() { return btnVolver; }
}