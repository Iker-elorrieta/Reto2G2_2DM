package vista;

import java.awt.Color;

import javax.swing.JPanel;

import javax.swing.JButton;
import java.awt.Font;

public class PanelLista extends JPanel {

    private static final long serialVersionUID = 1L;
    private JButton btnVolver;

    /**
     * Create the panel.
     */
    public PanelLista() {
        setBackground(new Color(255, 255, 255));
        setBounds(288, 11, 688, 541);
        setLayout(null);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 18));
        btnVolver.setBounds(513, 30, 129, 37);
        add(btnVolver);
    }

    
}