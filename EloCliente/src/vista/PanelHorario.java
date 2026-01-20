package vista;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class PanelHorario extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tablaHorario;
    private JLabel lblTitulo;
    private JButton btnVolver;
    private JButton btnPendientes;

    /**
     * Create the panel.
     */
    public PanelHorario() {
        setBackground(new Color(255, 255, 255));
        setBounds(288, 11, 688, 541);
        setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane.setBounds(64, 129, 565, 253);
        add(scrollPane);

        tablaHorario = new JTable();
        tablaHorario.setModel(new DefaultTableModel(
                new String[][] { { "08:00-09:00", "", "", "", "", ""},
                        { "09:00-10:00", "", "", "", "", ""}, { "10:00-11:00", "", "", "", "", ""},
                        { "11:00-12:00", "", "", "", "", ""}, { "12:00-13:00", "", "", "", "", ""}, },
                new String[] { "Hora/Día", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"}) {
            private static final long serialVersionUID = 1L;
            boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        tablaHorario.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaHorario.setRowHeight(50);

        
        scrollPane.setViewportView(tablaHorario);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 18));
        btnVolver.setBounds(490, 29, 164, 35);
        add(btnVolver);

        lblTitulo = new JLabel("");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setBounds(61, 29, 355, 35);
        add(lblTitulo);

        btnPendientes = new JButton("Ver Tareas Pendientes");
        btnPendientes.setVisible(false);
        btnPendientes.setFont(new Font("Arial", Font.PLAIN, 16));
        btnPendientes.setBounds(227, 446, 223, 35);
        add(btnPendientes);
    }

    public JTable getTablaHorario() {
        return tablaHorario;
    }

    public void setTablaHorario(JTable tablaHorario) {
        this.tablaHorario = tablaHorario;
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

    public JButton getBtnPendientes() {
        return btnPendientes;
    }

    public void setBtnPendientes(JButton btnPendientes) {
        this.btnPendientes = btnPendientes;
    }
}