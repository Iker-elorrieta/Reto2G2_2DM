package vista;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class PanelLogin extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField textFieldUser;
    private JPasswordField pfPass;
    private JButton btnLogin;
    private JLabel lblFotoLogo;
    JLabel lblNewLabel;

    public PanelLogin() {
    	setBackground(new Color(192, 192, 192));
        setBounds(288, 11, 688, 541);
        setLayout(null);

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogin.setFont(new Font("Arial", Font.BOLD, 22));
        lblLogin.setBounds(243, 202, 200, 50);
        add(lblLogin);
        
        lblFotoLogo = new JLabel();
        lblFotoLogo.setBounds(222, 29, 238, 142);
        add(lblFotoLogo);
        lblFotoLogo.setIcon(new ImageIcon(new ImageIcon("archivos/logo.png").getImage()
                .getScaledInstance(lblFotoLogo.getWidth(), lblFotoLogo.getHeight(), Image.SCALE_SMOOTH)));

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setFont(new Font("Arial", Font.PLAIN, 16));
        lblUser.setBounds(214, 293, 80, 20);
        add(lblUser);

        textFieldUser = new JTextField();
        textFieldUser.setBounds(333, 290, 200, 30);
        textFieldUser.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(textFieldUser);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPass.setBounds(194, 349, 100, 20);
        add(lblPass);

        pfPass = new JPasswordField();
        pfPass.setBounds(333, 346, 200, 30);
        pfPass.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(pfPass);

        btnLogin = new JButton("Aceptar");
        btnLogin.setForeground(new Color(0, 0, 0));
        btnLogin.setBounds(269, 429, 160, 41);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(255, 255, 255));
        add(btnLogin);
    }

    public JLabel getLblNewLabel() {
        return lblNewLabel;
    }

    public void setLblNewLabel(JLabel lblNewLabel) {
        this.lblNewLabel = lblNewLabel;
    }


    public JTextField getTextFieldUser() {
        return textFieldUser;
    }

    public void setTextFieldUser(JTextField textFieldUser) {
        this.textFieldUser = textFieldUser;
    }

    public JPasswordField getTextFieldPass() {
        return pfPass;
    }

    public void setTextFieldPass(JPasswordField pfPass) {
        this.pfPass = pfPass;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }
}