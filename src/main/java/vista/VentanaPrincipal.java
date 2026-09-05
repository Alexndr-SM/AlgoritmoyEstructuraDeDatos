package vista;

import dao.CitaMedicaDAO;
import modelo.CitaMedica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnRegistrar;
    private JButton btnEditar;
    private JButton btnCancelar;
    private JButton btnRefrescar;

    private CitaMedicaDAO citaDAO;
    private CitaMedica citaSeleccionada;

    public VentanaPrincipal() {
        setTitle("MediCitas - Sistema de Citas Medicas");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        citaDAO = new CitaMedicaDAO();

        initMenuBar();
        initComponents();
        cargarCitas();
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener(e -> System.exit(0));
        menuArchivo.add(salir);
        menuBar.add(menuArchivo);

        JMenu menuCitas = new JMenu("Citas");
        JMenuItem nuevaCita = new JMenuItem("Nueva Cita");
        nuevaCita.addActionListener(e -> abrirRegistrarCita());
        menuCitas.add(nuevaCita);
        menuBar.add(menuCitas);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem acercaDe = new JMenuItem("Acerca de");
        acercaDe.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "MediCitas - Sistema de Citas Medicas\n" +
                        "Hospital de la Solidaridad de Ica\n" +
                        "Version 1.0\n\n" +
                        "Desarrollado por:\n" +
                        "- Coello Flore Milagro Guadalupe\n" +
                        "- Sotelo Marcos Piero Alexander\n" +
                        "- Pachas Canales Mateo\n" +
                        "- Ramirez Hualpa Carlo Raul\n" +
                        "- Santiago Bevilacqua Juan Diego"));
        menuAyuda.add(acercaDe);
        menuBar.add(menuAyuda);

        setJMenuBar(menuBar);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JLabel lblTitulo = new JLabel("Gestion de Citas Medicas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelSuperior.add(lblTitulo, BorderLayout.WEST);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtBuscar = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarCitas());

        JLabel lblBuscar = new JLabel("Buscar por DNI:");
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 12));

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelSuperior.add(panelBusqueda, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Paciente", "DNI", "Medico", "Fecha", "Hora", "Estado", "Prioridad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCitas = new JTable(modeloTabla) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    String estado = (String) getValueAt(row, 6);
                    if ("Cancelada".equals(estado)) {
                        c.setBackground(new Color(255, 200, 200));
                    } else if ("Atendida".equals(estado)) {
                        c.setBackground(new Color(200, 255, 200));
                    } else {
                        c.setBackground(new Color(255, 255, 200));
                    }
                } else {
                    c.setBackground(getSelectionBackground());
                }

                return c;
            }
        };

        tablaCitas.setRowHeight(25);
        tablaCitas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaCitas.getTableHeader().setBackground(new Color(200, 220, 240));
        tablaCitas.getTableHeader().setReorderingAllowed(false);

        // Anchos de columna
        tablaCitas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaCitas.getColumnModel().getColumn(1).setPreferredWidth(180);
        tablaCitas.getColumnModel().getColumn(2).setPreferredWidth(80);
        tablaCitas.getColumnModel().getColumn(3).setPreferredWidth(180);
        tablaCitas.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablaCitas.getColumnModel().getColumn(5).setPreferredWidth(60);
        tablaCitas.getColumnModel().getColumn(6).setPreferredWidth(80);
        tablaCitas.getColumnModel().getColumn(7).setPreferredWidth(80);

        sorter = new TableRowSorter<>(modeloTabla);
        tablaCitas.setRowSorter(sorter);

        // Seleccion de fila
        tablaCitas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarCita();
            }
        });

        // Doble click para editar
        tablaCitas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirEditarCita();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Citas"));
        add(scrollPane, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        btnRegistrar = new JButton("Registrar Cita");
        btnRegistrar.setBackground(new Color(46, 125, 50));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.addActionListener(e -> abrirRegistrarCita());

        btnEditar = new JButton("Editar Cita");
        btnEditar.setBackground(new Color(25, 118, 210));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(e -> abrirEditarCita());
        btnEditar.setEnabled(false);

        btnCancelar = new JButton("Cancelar Cita");
        btnCancelar.setBackground(new Color(211, 47, 47));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> cancelarCita());
        btnCancelar.setEnabled(false);

        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(120, 120, 120));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarCitas());

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnRefrescar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarCitas() {
        modeloTabla.setRowCount(0);
        List<CitaMedica> citas = citaDAO.obtenerTodasCitas();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (CitaMedica c : citas) {
            Object[] fila = {
                    c.getIdCita(),
                    c.getPaciente().getNombres() + " " + c.getPaciente().getApellidos(),
                    c.getPaciente().getDni(),
                    c.getMedico().getNombres() + " " + c.getMedico().getApellidos(),
                    sdf.format(c.getFecha()),
                    c.getHora(),
                    c.getEstado(),
                    c.getPrioridad()
            };
            modeloTabla.addRow(fila);
        }

        txtBuscar.setText("");
        citaSeleccionada = null;
        btnEditar.setEnabled(false);
        btnCancelar.setEnabled(false);
    }

    private void buscarCitas() {
        String dni = txtBuscar.getText().trim();
        if (dni.isEmpty()) {
            cargarCitas();
            return;
        }

        modeloTabla.setRowCount(0);
        List<CitaMedica> citas = citaDAO.buscarCitasPorDniPaciente(dni);

        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron citas para el DNI: " + dni);
            cargarCitas();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (CitaMedica c : citas) {
            Object[] fila = {
                    c.getIdCita(),
                    c.getPaciente().getNombres() + " " + c.getPaciente().getApellidos(),
                    c.getPaciente().getDni(),
                    c.getMedico().getNombres() + " " + c.getMedico().getApellidos(),
                    sdf.format(c.getFecha()),
                    c.getHora(),
                    c.getEstado(),
                    c.getPrioridad()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarCita() {
        int fila = tablaCitas.getSelectedRow();
        if (fila >= 0) {
            try {
                int filaModelo = tablaCitas.convertRowIndexToModel(fila);
                int idCita = (int) modeloTabla.getValueAt(filaModelo, 0);
                citaSeleccionada = citaDAO.buscarCitaPorId(idCita);

                if (citaSeleccionada != null) {
                    btnEditar.setEnabled(true);
                    btnCancelar.setEnabled(true);

                    if ("Cancelada".equals(citaSeleccionada.getEstado()) || "Atendida".equals(citaSeleccionada.getEstado())) {
                        btnEditar.setEnabled(false);
                        btnCancelar.setEnabled(false);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al seleccionar cita: " + e.getMessage());
            }
        }
    }

    private void abrirRegistrarCita() {
        RegistrarCitaDialog dialog = new RegistrarCitaDialog(this);
        dialog.setVisible(true);
        if (dialog.isCitaRegistrada()) {
            cargarCitas();
        }
    }

    private void abrirEditarCita() {
        if (citaSeleccionada != null) {
            EditarCitaDialog dialog = new EditarCitaDialog(this, citaSeleccionada);
            dialog.setVisible(true);
            if (dialog.isCitaActualizada()) {
                cargarCitas();
            }
        }
    }

    private void cancelarCita() {
        if (citaSeleccionada == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Esta seguro de cancelar la cita #" + citaSeleccionada.getIdCita() + "?\n" +
                        "Paciente: " + citaSeleccionada.getPaciente().getNombres() + " " + citaSeleccionada.getPaciente().getApellidos() + "\n" +
                        "Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(citaSeleccionada.getFecha()) + " " + citaSeleccionada.getHora(),
                "Confirmar Cancelacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (citaDAO.cancelarCita(citaSeleccionada.getIdCita())) {
                JOptionPane.showMessageDialog(this, "Cita cancelada exitosamente.");
                cargarCitas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al cancelar la cita.");
            }
        }
    }

    private void mostrarPacientes() {
        JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo...\nVer pacientes proximamente.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new VentanaPrincipal().setVisible(true);
        });
    }
}