package vista;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;

public class PanelPerfil extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel lblTitulo;

    private JLabel lblNombreTitulo;
    private JLabel lblApellidosTitulo;
    private JLabel lblEmailTitulo;
    private JLabel lblTelefonoTitulo;

    private JLabel lblNombre;
    private JLabel lblApellidos;
    private JLabel lblEmail;
    private JLabel lblTelefono;
    private JButton btnVolver;

    public PanelPerfil() {

        setLayout(null);
        setBounds(0, 0, 707, 584);

      
        lblTitulo = new JLabel("PERFIL DEL USUARIO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 20, 707, 30);
        add(lblTitulo);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 18));
        btnVolver.setBounds(533, 495, 164, 35);
        add(btnVolver);
        
        lblNombreTitulo = new JLabel("Nombre:");
        lblNombreTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombreTitulo.setBounds(150, 150, 100, 25);
        add(lblNombreTitulo);

        lblNombre = new JLabel();
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNombre.setBorder(new LineBorder(Color.GRAY));
        lblNombre.setBounds(260, 150, 300, 25);
        add(lblNombre);

 
        lblApellidosTitulo = new JLabel("Apellidos:");
        lblApellidosTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblApellidosTitulo.setBounds(150, 200, 100, 25);
        add(lblApellidosTitulo);

        lblApellidos = new JLabel();
        lblApellidos.setFont(new Font("Arial", Font.PLAIN, 14));
        lblApellidos.setBorder(new LineBorder(Color.GRAY));
        lblApellidos.setBounds(260, 200, 300, 25);
        add(lblApellidos);

        
        lblEmailTitulo = new JLabel("Email:");
        lblEmailTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblEmailTitulo.setBounds(150, 250, 100, 25);
        add(lblEmailTitulo);

        lblEmail = new JLabel();
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEmail.setBorder(new LineBorder(Color.GRAY));
        lblEmail.setBounds(260, 250, 300, 25);
        add(lblEmail);

       
        lblTelefonoTitulo = new JLabel("Teléfono:");
        lblTelefonoTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTelefonoTitulo.setBounds(150, 300, 100, 25);
        add(lblTelefonoTitulo);

        lblTelefono = new JLabel();
        lblTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTelefono.setBorder(new LineBorder(Color.GRAY));
        lblTelefono.setBounds(260, 300, 300, 25);
        add(lblTelefono);
        
        
    }

    // Getters para el controlador
    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JLabel getLblApellidos() {
        return lblApellidos;
    }

    public JLabel getLblEmail() {
        return lblEmail;
    }

    public JLabel getLblTelefono() {
        return lblTelefono;
    }
    public JButton getBtnVolver() {
        return btnVolver;
    }

    public void setBtnVolver(JButton btnVolver) {
        this.btnVolver = btnVolver;
    }

}
