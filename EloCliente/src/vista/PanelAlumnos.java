package vista;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PanelAlumnos extends JPanel {


	private static final long serialVersionUID = 1L;
	private JTable tabla;
    private JButton btnVolver;

    public PanelAlumnos() {
        setLayout(null);
        setBounds(0, 0, 707, 584);

        JScrollPane scroll = new JScrollPane();
        scroll.setBounds(30, 50, 640, 400);
        add(scroll);

        tabla = new JTable();
        scroll.setViewportView(tabla);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(30, 470, 100, 30);
        add(btnVolver);
    }

    public JTable getTabla() {
        return tabla;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
}
