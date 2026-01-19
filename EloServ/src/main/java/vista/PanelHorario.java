package vista;

import java.awt.Color;
import javax.swing.JPanel;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;


public class PanelHorario extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel lblTitulo;
    private JButton btnVolver;

    /**
     * Create the panel.
     */
    public PanelHorario() {
        setBackground(new Color(255, 255, 255));
        setBounds(288, 11, 688, 541);
        setLayout(null);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 18));
        btnVolver.setBounds(490, 29, 164, 35);
        add(btnVolver);

        lblTitulo = new JLabel("");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setBounds(61, 29, 355, 35);
        add(lblTitulo);
    }

   
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public void setBtnVolver(JButton btnVolver) {
        this.btnVolver = btnVolver;
    }

   
}