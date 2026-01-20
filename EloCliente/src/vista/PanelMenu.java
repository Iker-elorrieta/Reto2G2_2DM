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
    private JButton btnDesconectar;
    
    public PanelMenu() {
        setBackground(new Color(255, 255, 255));
        setBounds(288, 11, 688, 541);
        setLayout(null);

        JLabel lblMenu = new JLabel("Menu");
        lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 28));
        lblMenu.setBounds(135, 51, 244, 41);
        add(lblMenu);

        btnDesconectar = new JButton("Desconectar");
        btnDesconectar.setFont(new Font("Arial", Font.PLAIN, 20));
        btnDesconectar.setBounds(469, 40, 162, 41);
        add(btnDesconectar);

        lblFotoHorario = new JLabel();
        lblFotoHorario.setBounds(33, 190, 204, 116);
        add(lblFotoHorario);
        												//FALTA IMAGEN
        lblFotoHorario.setIcon(new ImageIcon(new ImageIcon("archivos/horario.png").getImage()
                .getScaledInstance(lblFotoHorario.getWidth(), lblFotoHorario.getHeight(), Image.SCALE_SMOOTH)));

        lblFotoAlumno = new JLabel();
        lblFotoAlumno.setBounds(252, 190, 204, 116);
        add(lblFotoAlumno);
        												//FALTA IMAGEN
        lblFotoAlumno.setIcon(new ImageIcon(new ImageIcon("archivos/alumno.png").getImage()
                .getScaledInstance(lblFotoAlumno.getWidth(), lblFotoAlumno.getHeight(), Image.SCALE_SMOOTH)));

        lblFotoReuniones = new JLabel();
        lblFotoReuniones.setBounds(474, 190, 204, 116);
        add(lblFotoReuniones);
        												//FALTA IMAGEN
        lblFotoReuniones.setIcon(new ImageIcon(new ImageIcon("archivos/reuniones.png").getImage()
                .getScaledInstance(lblFotoReuniones.getWidth(), lblFotoReuniones.getHeight(), Image.SCALE_SMOOTH)));

        JLabel lblHorario = new JLabel("Horario");
        lblHorario.setFont(new Font("Arial", Font.PLAIN, 16));
        lblHorario.setHorizontalAlignment(SwingConstants.CENTER);
        lblHorario.setBounds(43, 327, 204, 28);
        add(lblHorario);

        JLabel lblOtros = new JLabel("Otros horarios");
        lblOtros.setHorizontalAlignment(SwingConstants.CENTER);
        lblOtros.setFont(new Font("Arial", Font.PLAIN, 16));
        lblOtros.setBounds(252, 327, 204, 28);
        add(lblOtros);

        JLabel lblReuniones = new JLabel("Reuniones");
        lblReuniones.setHorizontalAlignment(SwingConstants.CENTER);
        lblReuniones.setFont(new Font("Arial", Font.PLAIN, 16));
        lblReuniones.setBounds(474, 327, 204, 28);
        add(lblReuniones);

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

    public void setBtnDesconectar(JButton btnDesconectar) {
        this.btnDesconectar = btnDesconectar;
    }
    
  
}