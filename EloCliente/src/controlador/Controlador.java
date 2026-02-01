package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import vista.Principal;
import vista.Principal.enumAcciones;


public class Controlador implements ActionListener, MouseListener {

    private vista.Principal vistaPrincipal;
    private Socket cliente;
    
    // Solo usamos Object Streams para evitar conflictos de protocolo
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private int id = 0;

    public Controlador(vista.Principal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.conectarConServidor(); // Conectamos al iniciar
        this.inicializarControlador();
    }

    private void conectarConServidor() {
        try {
            cliente = new Socket("localhost", 4500);
            // El orden importante para q no salga Connection Reset
            oos = new ObjectOutputStream(cliente.getOutputStream());
            oos.flush(); 
            ois = new ObjectInputStream(cliente.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor.");
        }
    }

    private void inicializarControlador() {
        // Para ocultar el id antes del ;
        vistaPrincipal.getPanelLista().getListaProfesor().setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
            Component c = defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof String texto && texto.contains(";")) {
                ((javax.swing.JLabel) c).setText(texto.split(";")[1]);
            }
            return c;
        });

        // Configuración de botones y listeners
        configurarBotones();
    }

    private void configurarBotones() {
        vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
        vistaPrincipal.getPanelLogin().getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN.toString());

        vistaPrincipal.getPanelHorario().getBtnVolver().addActionListener(this);
        vistaPrincipal.getPanelHorario().getBtnVolver().setActionCommand(Principal.enumAcciones.VOLVER.toString());

        vistaPrincipal.getPanelMenu().getBtnDesconectar().addActionListener(this);
        vistaPrincipal.getPanelMenu().getBtnDesconectar().setActionCommand(Principal.enumAcciones.DESCONECTAR.toString());

        vistaPrincipal.getPanelPerfil().getBtnVolver().addActionListener(this);
        vistaPrincipal.getPanelPerfil().getBtnVolver().setActionCommand(Principal.enumAcciones.VOLVER.toString());

        vistaPrincipal.getPanelMenu().getBtnPerfil().addActionListener(this);
        vistaPrincipal.getPanelMenu().getBtnPerfil().setActionCommand(Principal.enumAcciones.CARGAR_PANEL_PERFIL.toString());

        vistaPrincipal.getPanelLista().getBtnSeleccionar().addActionListener(this);
        vistaPrincipal.getPanelLista().getBtnSeleccionar().setActionCommand(Principal.enumAcciones.SELECCIONAR_PROFESOR.toString());

        vistaPrincipal.getPanelLista().getBtnVolver().addActionListener(this);
        vistaPrincipal.getPanelLista().getBtnVolver().setActionCommand(Principal.enumAcciones.VOLVER.toString());

        vistaPrincipal.getPanelMenu().getBtnAlumnosLista().addActionListener(this);
        vistaPrincipal.getPanelMenu().getBtnAlumnosLista().setActionCommand(Principal.enumAcciones.VER_ALUMNOS_PROFESOR.toString());

        vistaPrincipal.getPanelAlumnos().getBtnVolver().addActionListener(this);
        vistaPrincipal.getPanelAlumnos().getBtnVolver().setActionCommand(Principal.enumAcciones.VOLVER.toString());
        
        vistaPrincipal.getPanelReuniones().getBtnVolver().addActionListener(this);
        vistaPrincipal.getPanelReuniones().getBtnVolver().setActionCommand(Principal.enumAcciones.VOLVER.toString());

        vistaPrincipal.getPanelReuniones().getBtnAceptar().addActionListener(e -> {
            enviarActualizacion("aceptada");
        });

        vistaPrincipal.getPanelReuniones().getBtnRechazar().addActionListener(e -> {
            enviarActualizacion("denegada");
        });
        
        vistaPrincipal.getPanelMenu().getLblFotoAlumno().addMouseListener(this);
        vistaPrincipal.getPanelMenu().getLblFotoReuniones().addMouseListener(this);
        vistaPrincipal.getPanelMenu().getLblFotoHorario().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

        switch (accion) {
            case LOGIN:
                this.mConfirmarLogin();
                break;
            case CARGAR_PANEL_PERFIL:
                mSolicitarDatosUsuario();
                this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_PERFIL);
                break;
            case DESCONECTAR:
                mDesconectar();
                break;
            case SELECCIONAR_PROFESOR:
                seleccionarProfesor();
                break;
            case VER_ALUMNOS_PROFESOR:
                mAbrirListaAlumnos();
                break;
            case VOLVER:
            	vistaPrincipal.getPanelReuniones().setVisible(false);
                vistaPrincipal.getPanelHorario().setVisible(false);
                this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_MENU);
                break;
            default:
                break;
        }
    }

    private void mConfirmarLogin() {
        try {
            oos.writeInt(1);
            oos.flush();
            oos.writeUTF(this.vistaPrincipal.getPanelLogin().getTextFieldUser().getText());
            oos.writeUTF(new String(this.vistaPrincipal.getPanelLogin().getTextFieldPass().getPassword()));
            oos.flush();
            
            id = ois.readInt();
            if (id != 0) {
                this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_MENU);
            } else {
                JOptionPane.showMessageDialog(null, "Datos incorrectos.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mAbrirHorario() {
        try {
            oos.writeInt(2);
            oos.writeInt(id);
            oos.flush();

            String[][] horario = (String[][]) ois.readObject();
            cargarHorario(horario, this.vistaPrincipal.getPanelHorario().getTablaHorario());
            this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_HORARIO);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void mAbrirListaProfesores() {
        try {
            oos.writeInt(5);
            oos.writeInt(id);
            oos.flush();

            Object recibido = ois.readObject();
            DefaultListModel<String> modelo = new DefaultListModel<>();

            if (recibido instanceof java.util.List<?>) {
                java.util.List<?> lista = (java.util.List<?>) recibido;
                for (Object obj : lista) {
                    if (obj instanceof String) modelo.addElement((String) obj);
                }
            }

            vistaPrincipal.getPanelLista().getListaProfesor().setModel(modelo);
            vistaPrincipal.mVisualizarPaneles(enumAcciones.VER_LISTA_PROFESORES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void seleccionarProfesor() {
        String seleccionado = vistaPrincipal.getPanelLista().getListaProfesor().getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un profesor");
            return;
        }

        int idProfesor = Integer.parseInt(seleccionado.split(";")[0]);
        try {
            oos.writeInt(6);
            oos.writeInt(idProfesor);
            oos.flush();

            String[][] horario = (String[][]) ois.readObject();
            vistaPrincipal.getPanelHorario().getLblTitulo().setText("Horario del profesor");
            cargarHorario(horario, vistaPrincipal.getPanelHorario().getTablaHorario());
            vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_HORARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mAbrirListaAlumnos() {
        try {
            oos.writeInt(7);
            oos.writeInt(id);
            oos.flush();

            Object[][] alumnos = (Object[][]) ois.readObject();
            DefaultTableModel modelo = new DefaultTableModel(alumnos, 
                new String[]{"Nombre", "Apellidos", "Email", "Teléfono 1", "Teléfono 2", "Dirección", "Usuario"});

            vistaPrincipal.getPanelAlumnos().getTabla().setModel(modelo);
            vistaPrincipal.mVisualizarPaneles(enumAcciones.VER_ALUMNOS_PROFESOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mSolicitarDatosUsuario() {
        try {
            oos.writeInt(3);
            oos.writeInt(id);
            oos.flush();

            String[] datos = (String[]) ois.readObject();
            vistaPrincipal.getPanelPerfil().getLblNombre().setText(datos[0]);
            vistaPrincipal.getPanelPerfil().getLblApellidos().setText(datos[1]);
            vistaPrincipal.getPanelPerfil().getLblEmail().setText(datos[2]);
            vistaPrincipal.getPanelPerfil().getLblTelefono().setText(datos[3]);
            vistaPrincipal.getPanelPerfil().getLblTelefono2().setText(datos[4]);
            vistaPrincipal.getPanelPerfil().getLblDireccion().setText(datos[5]);
            vistaPrincipal.getPanelPerfil().getLblUsername().setText(datos[6]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mDesconectar() {
        try {
            oos.writeInt(4);
            oos.flush();
            ois.close();
            oos.close();
            cliente.close();
            this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarHorario(String[][] horario, JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel(horario,
                new String[] { "Hora/Día", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" }) {
            private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla.setModel(modelo);
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JTextArea textArea = new JTextArea(value == null ? "" : value.toString());
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setOpaque(true);

                if (value instanceof String cellValue) {
                    if (cellValue.contains("-R")) textArea.setBackground(Color.RED);
                    else if (cellValue.contains("-C")) textArea.setBackground(Color.GREEN);
                    else if (cellValue.contains("-P")) textArea.setBackground(Color.GRAY);
                    else if (cellValue.contains("-E")) textArea.setBackground(Color.ORANGE);
                    else textArea.setBackground(table.getBackground());
                }

                if (isSelected) {
                    textArea.setBackground(table.getSelectionBackground());
                    textArea.setForeground(table.getSelectionForeground());
                }
                return textArea;
            }
        };

        for (int i = 1; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }
        tabla.setRowHeight(75);
    }
    private void mAbrirReuniones() {
        try {
            // 1. Pedir Matriz para Horario
            oos.writeInt(2); oos.writeInt(id); oos.flush();
            String[][] matrizHorario = (String[][]) ois.readObject();

            // 2. Pedir Datos para Tabla Gestión (con JSON)
            oos.writeInt(9); oos.writeInt(id); oos.flush();
            Object[][] datosGestion = (Object[][]) ois.readObject();

            SwingUtilities.invokeLater(() -> {
                // Actualizar tabla superior
                cargarHorario(matrizHorario, vistaPrincipal.getPanelReuniones().getTablaHorario());
                configurarDisenoGrafico(vistaPrincipal.getPanelReuniones().getTablaHorario());

                // Actualizar tabla inferior
                String[] cab = {"ID", "Título", "Centro", "Municipio", "Aula", "Fecha", "Estado"};
                vistaPrincipal.getPanelReuniones().getTablaGestion().setModel(new DefaultTableModel(datosGestion, cab));

                vistaPrincipal.mVisualizarPaneles(enumAcciones.VER_REUNIONES);
            });
            vistaPrincipal.getPanelReuniones().getTablaGestion().setRowHeight(30);
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    private void enviarActualizacion(String nuevoEstado) {
        JTable t = vistaPrincipal.getPanelReuniones().getTablaGestion();
        int fila = t.getSelectedRow();
        if (fila == -1) return;

        int idReunion = (int) t.getValueAt(fila, 0);
        try {
            oos.writeInt(10); // Código servidor para actualizar
            oos.writeInt(idReunion);
            oos.writeUTF(nuevoEstado);
            oos.flush();

            if (ois.readBoolean()) {
                // REFRESCO AUTOMÁTICO
                mAbrirReuniones(); 
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    private void configurarDisenoGrafico(JTable tabla) {
        DefaultTableCellRenderer renderizadorColores = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setOpaque(true); 
                label.setHorizontalAlignment(SwingConstants.CENTER);

                if (value != null) {
                    String texto = value.toString().toLowerCase();
                    // Colores según tu imagen
                    if (texto.contains("/")) label.setBackground(Color.LIGHT_GRAY);
                    else if (texto.contains("pendiente")) label.setBackground(new Color(255, 204, 51));
                    else if (texto.contains("aceptado")) label.setBackground(new Color(144, 238, 144));
                    else if (texto.contains("denegado")) label.setBackground(new Color(255, 102, 102));
                    else label.setBackground(Color.WHITE);
                }
                
                if (isSelected) label.setBackground(table.getSelectionBackground());
                return label;
            }
        };

        // Aplicar a todas las columnas excepto la 0 (que son las horas)
        for (int i = 1; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderizadorColores);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if (source == vistaPrincipal.getPanelMenu().getLblFotoHorario()) mAbrirHorario();
        else if (source == vistaPrincipal.getPanelMenu().getLblFotoAlumno()) mAbrirListaProfesores();
        else if (source == vistaPrincipal.getPanelMenu().getLblFotoReuniones()) {
            mAbrirReuniones(); // <--- Esta es la llave que abre todo
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}