package vista;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;



public class PanelMenu extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel lblFotoHorario, lblFotoAlumno, lblFotoReuniones;
    private JButton btnDesconectar, btnPerfil, btnAlumnosLista;
    
    public PanelMenu() {
        setBackground(new Color(255, 255, 255));
        setBounds(288, 11, 688, 541);
        setLayout(null);

        JLabel lblMenu = new JLabel("Menu");
        lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 28));
        lblMenu.setBounds(215, 37, 244, 41);
        add(lblMenu);

        btnDesconectar = new JButton("Desconectar");
        btnDesconectar.setFont(new Font("Arial", Font.PLAIN, 20));
        btnDesconectar.setBounds(495, 40, 162, 41);
        add(btnDesconectar);

        lblFotoHorario = new JLabel();
        lblFotoHorario.setBounds(33, 285, 204, 116);
        add(lblFotoHorario);
        												
        lblFotoHorario.setIcon(new ImageIcon(new ImageIcon("img/horario.png").getImage()
                .getScaledInstance(lblFotoHorario.getWidth(), lblFotoHorario.getHeight(), Image.SCALE_SMOOTH)));

        lblFotoAlumno = new JLabel();
        lblFotoAlumno.setBounds(247, 285, 204, 116);
        add(lblFotoAlumno);
        												
        lblFotoAlumno.setIcon(new ImageIcon(new ImageIcon("img/perfil.png").getImage()
                .getScaledInstance(lblFotoAlumno.getWidth(), lblFotoAlumno.getHeight(), Image.SCALE_SMOOTH)));

        lblFotoReuniones = new JLabel();
        lblFotoReuniones.setBounds(469, 285, 204, 116);
        add(lblFotoReuniones);
        												
        lblFotoReuniones.setIcon(new ImageIcon(new ImageIcon("img/reunion.png").getImage()
                .getScaledInstance(lblFotoReuniones.getWidth(), lblFotoReuniones.getHeight(), Image.SCALE_SMOOTH)));

        JLabel lblHorario = new JLabel("Horario");
        lblHorario.setFont(new Font("Arial", Font.PLAIN, 16));
        lblHorario.setHorizontalAlignment(SwingConstants.CENTER);
        lblHorario.setBounds(33, 444, 204, 28);
        add(lblHorario);

        JLabel lblOtros = new JLabel("Otros horarios");
        lblOtros.setHorizontalAlignment(SwingConstants.CENTER);
        lblOtros.setFont(new Font("Arial", Font.PLAIN, 16));
        lblOtros.setBounds(252, 444, 204, 28);
        add(lblOtros);

        JLabel lblReuniones = new JLabel("Reuniones");
        lblReuniones.setHorizontalAlignment(SwingConstants.CENTER);
        lblReuniones.setFont(new Font("Arial", Font.PLAIN, 16));
        lblReuniones.setBounds(469, 444, 204, 28);
        add(lblReuniones);
        
        btnPerfil = new JButton("Perfil");
        btnPerfil.setFont(new Font("Arial", Font.PLAIN, 12));
        btnPerfil.setBounds(33, 37, 162, 41);
        add(btnPerfil);
        
        btnAlumnosLista = new JButton("Alumnos");       
        btnAlumnosLista.setBounds(283, 106, 114, 35);
        add(btnAlumnosLista);

    }

    public JLabel getLblFotoHorario() {
        return lblFotoHorario;
    }

    public void setLblFotoHorario(JLabel lblFotoHorario) {
        this.lblFotoHorario = lblFotoHorario;
    }

    public JLabel getLblFotoAlumno() {
        return lblFotoAlumno;
    }

    public void setLblFotoAlumno(JLabel lblFotoAlumno) {
        this.lblFotoAlumno = lblFotoAlumno;
    }

    public JLabel getLblFotoReuniones() {
        return lblFotoReuniones;
    }

    public void setLblFotoReuniones(JLabel lblFotoReuniones) {
        this.lblFotoReuniones = lblFotoReuniones;
    }

    public JButton getBtnDesconectar() {
        return btnDesconectar;
    }
    
    public JButton getBtnPerfil() {
        return btnPerfil;
    }
    
    public JButton setBtnPerfil() {
        return btnPerfil;
    }
    public JButton getBtnAlumnosLista() {
    		return btnAlumnosLista;
    }
    public JButton setBtnAlumnosLista() {
		return btnAlumnosLista;
}
   

    public void setBtnDesconectar(JButton btnDesconectar) {
        this.btnDesconectar = btnDesconectar;
    }
}