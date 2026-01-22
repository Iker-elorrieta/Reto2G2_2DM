package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import vista.Principal;
import vista.Principal.enumAcciones;

public class Controlador implements ActionListener, MouseListener {

	private vista.Principal vistaPrincipal;
	private Socket cliente;
	private DataOutputStream dos;
	private DataInputStream dis;
	private ObjectInputStream ois;
	@SuppressWarnings("unused")
	private ObjectOutputStream oos;
	private int id = 0;

	/*
	 * *** CONSTRUCTORES ***
	 */
	public Controlador(vista.Principal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
		this.inicializarControlador();
	}

	private void inicializarControlador() {

	    try {
	        cliente = new Socket("localhost", 4500);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
	    vistaPrincipal.getPanelLogin().getBtnLogin()
	        .setActionCommand(Principal.enumAcciones.LOGIN.toString());

	    vistaPrincipal.getPanelHorario().getBtnVolver().addActionListener(this);
	    vistaPrincipal.getPanelHorario().getBtnVolver()
	        .setActionCommand(Principal.enumAcciones.VOLVER.toString());

	    vistaPrincipal.getPanelMenu().getBtnDesconectar().addActionListener(this);
	    vistaPrincipal.getPanelMenu().getBtnDesconectar()
	        .setActionCommand(Principal.enumAcciones.DESCONECTAR.toString());

	    vistaPrincipal.getPanelMenu().getLblFotoAlumno().addMouseListener(this);
	    vistaPrincipal.getPanelMenu().getLblFotoReuniones().addMouseListener(this);
	    vistaPrincipal.getPanelMenu().getLblFotoHorario().addMouseListener(this);

	    vistaPrincipal.getPanelPerfil().getBtnVolver().addActionListener(this);
	    vistaPrincipal.getPanelPerfil().getBtnVolver()
	        .setActionCommand(Principal.enumAcciones.VOLVER.toString());

	    vistaPrincipal.getPanelMenu().getBtnPerfil().addActionListener(this);
	    vistaPrincipal.getPanelMenu().getBtnPerfil()
	        .setActionCommand(Principal.enumAcciones.CARGAR_PANEL_PERFIL.toString());
	    
	    vistaPrincipal.getPanelLista().getBtnSeleccionar()
	    .addActionListener(this);

	vistaPrincipal.getPanelLista().getBtnSeleccionar()
	    .setActionCommand(
	        Principal.enumAcciones.SELECCIONAR_PROFESOR.toString());


	  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

		switch (accion) {
		case LOGIN:
			incializarServidor();
			try {
				dos = new DataOutputStream(cliente.getOutputStream());
				oos = new ObjectOutputStream(cliente.getOutputStream());
				dis = new DataInputStream(cliente.getInputStream());
				ois = new ObjectInputStream(cliente.getInputStream());
			} catch (IOException ea) {
				// TODO Auto-generated catch block
				ea.printStackTrace();
			}
			this.mConfirmarLogin(accion);
			break;
			
		case CARGAR_PANEL_PERFIL:
			mSolicitarDatosUsuario();
			this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_PERFIL);
			break;
			
		case DESCONECTAR:
			try {
				dos.writeInt(4);
				dos.flush();
				dis.close();
				dos.close();
				this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_LOGIN);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			
		case SELECCIONAR_PROFESOR:
			seleccionarProfesor();
			this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_HORARIO);
			break;
			
				
			
		case VOLVER:
			this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_MENU);
			;
			break;

		default:
			break;

		}
	}

	private void mConfirmarLogin(enumAcciones accion) {
		try {
			dos.writeInt(1);
			dos.flush();
			dos.writeUTF(this.vistaPrincipal.getPanelLogin().getTextFieldUser().getText());
			dos.flush();
			dos.writeUTF(new String(this.vistaPrincipal.getPanelLogin().getTextFieldPass().getPassword()));
			dos.flush();
			id = (int) dis.readInt();
			System.out.println("id:"+ id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (id != 0) {
			this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_MENU);
		} else {
			JOptionPane.showMessageDialog(null, "No existe ningun profesor con esos datos");
		}
	}
	private void mAbrirHorario() {
	    this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_HORARIO);

	    try {
	        
	    	dos.writeInt(2);   // opción ver horario
	    	dos.flush();

	    	dos.writeInt(id);  
	    	dos.flush();

	    	String[][] horario = (String[][]) ois.readObject();
	        
	    	System.out.println(Arrays.deepToString(horario));
	        cargarHorario(horario, this.vistaPrincipal.getPanelHorario().getTablaHorario());

	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	@SuppressWarnings("unchecked")
	private void mAbrirListaProfesores() {

	    try {
	        dos.writeInt(5);   
	        dos.writeInt(id);  
	        dos.flush();

	        ArrayList<String> profesores = (ArrayList<String>) ois.readObject();
	        System.out.println("Profesores recibidos: " + profesores);
	        DefaultListModel<String> modelo = new DefaultListModel<>();
	        for (String p : profesores) {
	            modelo.addElement(p);
	        }

	        vistaPrincipal.getPanelLista()
	            .getListaProfesor().setModel(modelo);

	        vistaPrincipal.mVisualizarPaneles(
	            enumAcciones.VER_LISTA_PROFESORES);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	private void seleccionarProfesor() {

	    String seleccionado =
	        vistaPrincipal.getPanelLista().getListaProfesor().getSelectedValue();

	    if (seleccionado == null) {
	        JOptionPane.showMessageDialog(null, "Selecciona un profesor");
	        return;
	    }

	    int idProfesor = Integer.parseInt(seleccionado.split(";")[0]);

	    try {
	        dos.writeInt(6);
	        dos.writeInt(idProfesor);
	        dos.flush();

	        String[][] horario = (String[][]) ois.readObject();

	        vistaPrincipal.getPanelHorario().getLblTitulo()
	            .setText("Horario del profesor");

	        cargarHorario(horario,
	            vistaPrincipal.getPanelHorario().getTablaHorario());

	        vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_HORARIO);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private void mSolicitarDatosUsuario() {
	    try {

	        dos.writeInt(3);
	        dos.flush();

	        dos.writeInt(id);
	        dos.flush();
			String[] datos = (String[]) ois.readObject();

			vistaPrincipal.getPanelPerfil().getLblNombre().setText(datos[0]);
			vistaPrincipal.getPanelPerfil().getLblApellidos().setText(datos[1]);
			vistaPrincipal.getPanelPerfil().getLblEmail().setText(datos[2]);
			vistaPrincipal.getPanelPerfil().getLblTelefono().setText(datos[3]);

	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	


private void cargarHorario(String[][] horario, JTable tabla) {
        
        DefaultTableModel modelo = new DefaultTableModel(horario,
                new String[] { "Hora/Día", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" }) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        

        tabla.setModel(modelo);

        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                
                JTextArea textArea = new JTextArea();
                textArea.setText(value == null ? "" : value.toString());
                textArea.setWrapStyleWord(true); 
                textArea.setLineWrap(true); 
                textArea.setOpaque(true); 
                
                if (value != null && value instanceof String) {
                    String cellValue = (String) value;

                    if (cellValue.contains("-R")) {
                        textArea.setBackground(Color.RED);
                        textArea.setForeground(Color.BLACK);
                    } else if (cellValue.contains("-C")) {
                        textArea.setBackground(Color.GREEN);
                        textArea.setForeground(Color.BLACK);
                    } else if (cellValue.contains("-P")) {
                        textArea.setBackground(Color.GRAY);
                        textArea.setForeground(Color.BLACK);
                    } else if (cellValue.contains("-E")) {
                        textArea.setBackground(Color.ORANGE);
                        textArea.setForeground(Color.BLACK);
                    } else {
                        textArea.setBackground(table.getBackground());
                        textArea.setForeground(table.getForeground());
                    }
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


	private void incializarServidor() {
		// TODO Auto-generated method stub
		try {
			cliente = new Socket("localhost", 4500);
			dos = new DataOutputStream(cliente.getOutputStream());
			dis = new DataInputStream(cliente.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void mouseClicked(MouseEvent e) {
	
	    Object source = e.getSource();

	    vistaPrincipal.getPanelHorario()
	        .getBtnPendientes().setVisible(false);

	    if (source == vistaPrincipal.getPanelMenu().getLblFotoHorario()) {
	        mAbrirHorario();

	    } else if (source == vistaPrincipal.getPanelMenu().getLblFotoAlumno()) {
	        mAbrirListaProfesores();
	    }
	}

	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
