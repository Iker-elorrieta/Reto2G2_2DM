package vista;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Font;

public class PanelLista extends JPanel {

    private static final long serialVersionUID = 1L;
    private JList<String> lista;
    private JButton btnVolver, btnSeleccionar;

    /**
     * Create the panel.
     */
    public PanelLista() {
        setBackground(new Color(255, 255, 255));
        setBounds(288, 11, 688, 541);
        setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(77, 78, 274, 355);
        add(scrollPane);

        lista = new JList<String>();
        scrollPane.setViewportView(lista);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 18));
        btnVolver.setBounds(513, 30, 129, 37);
        add(btnVolver);

        btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setFont(new Font("Arial", Font.PLAIN, 18));
        btnSeleccionar.setBounds(402, 154, 240, 37);
        add(btnSeleccionar);
    }

    public JList<String> getListaProfesor() {
        return lista;
    }

    public void setListaProfesor(JList<String> lista) {
        this.lista = lista;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public void setBtnVolver(JButton btnVolver) {
        this.btnVolver = btnVolver;
    }

    public JButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public void setBtnSeleccionar(JButton btnSeleccionar) {
        this.btnSeleccionar = btnSeleccionar;
    }

}