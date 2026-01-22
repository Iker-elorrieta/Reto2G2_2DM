package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {

    private static final long serialVersionUID = 1L;

    private PanelLogin panelLogin;
    private PanelMenu panelMenu;
    private PanelHorario panelHorario;
    private PanelLista panelLista;
    private PanelPerfil panelPerfil;

    private JPanel panelContenedor;

    public static enum enumAcciones {
        CARGAR_PANEL_LOGIN, 
        CARGAR_PANEL_MENU, 
        LOGIN, 
        DESCONECTAR, 
        VOLVER, 
        CARGAR_PANEL_HORARIO, 
        CARGAR_PANEL_LISTA,
        SELECCIONAR_PROFESOR,
        CARGAR_PANEL_PERFIL,
        VER_LISTA_PROFESORES
   
    }

    public Principal() {
        mCrearPanelContenedor();
        mCrearVLogin();
        mCrearPanelMenu();
        mCrearPanelHorario(); 
        mCrearPanelLista();
        mCrearPanelPerfil();

        mVisualizarPaneles(enumAcciones.CARGAR_PANEL_LOGIN);
    }

    public void mVisualizarPaneles(enumAcciones panel) {

        panelLogin.setVisible(false);
        panelMenu.setVisible(false);
        panelHorario.setVisible(false);
        panelLista.setVisible(false);
        panelPerfil.setVisible(false);

        switch (panel) {
            case CARGAR_PANEL_LOGIN:
                panelLogin.setVisible(true);
                break;

            case CARGAR_PANEL_MENU:
                panelMenu.setVisible(true);
                break;

            case CARGAR_PANEL_HORARIO:
                panelHorario.setVisible(true);
                break;

            case CARGAR_PANEL_LISTA:
            	System.out.println("Cargando panel lista");
                panelLista.setVisible(true);
                break;
         
            case VER_LISTA_PROFESORES: 
              	System.out.println("reacciono");
                panelLista.setVisible(true);
                break;
             case CARGAR_PANEL_PERFIL:
            	 panelPerfil.setVisible(true);
				break;

            default:
                break;
        }
    }

    private void mCrearPanelContenedor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 707, 584);

        panelContenedor = new JPanel();
        panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelContenedor);
        panelContenedor.setLayout(null);
    }
    

    private void mCrearVLogin() {
        panelLogin = new PanelLogin();
        panelLogin.setLocation(0, 11);
        panelContenedor.add(panelLogin);
        panelLogin.setVisible(false);
    }

    private void mCrearPanelMenu() {
        panelMenu = new PanelMenu();
        panelMenu.setLocation(0, 11);
        panelContenedor.add(panelMenu);
        panelMenu.setVisible(false);
    }

    private void mCrearPanelHorario() {

		panelHorario = new PanelHorario();
		panelHorario.setLocation(0, 11);
		panelContenedor.add(panelHorario);
		panelContenedor.setBounds(panelHorario.getBounds());
		panelHorario.setVisible(false);
	}

    private void mCrearPanelLista() {
        panelLista = new PanelLista();
        panelLista.setLocation(0, 11);
        panelContenedor.add(panelLista);
        panelLista.setVisible(false);
    }
    private void mCrearPanelPerfil() {
        panelPerfil = new PanelPerfil();
        panelPerfil.setLocation(0, 11);
        panelContenedor.add(panelPerfil);
        panelPerfil.setVisible(false);
    }


    // Getters y setters

    public JPanel getPanelContenedor() {
        return panelContenedor;
    }
    public PanelPerfil getPanelPerfil() {
        return panelPerfil;
    }

    public PanelLogin getPanelLogin() {
        return panelLogin;
    }

    public PanelMenu getPanelMenu() {
        return panelMenu;
    }

    public PanelHorario getPanelHorario() {
        return panelHorario;
    }

    public PanelLista getPanelLista() {
        return panelLista;
    }
}
