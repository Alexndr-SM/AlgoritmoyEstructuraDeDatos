package vista;

import modelo.CitaMedica;
import modelo.Paciente;
import modelo.Medico;
import dao.CitaMedicaDAO;
import dao.PacienteDAO;
import dao.MedicoDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RegistrarCitaDialog extends JDialog {
    private JComboBox<Paciente> cmbPaciente;
    private JComboBox<Medico> cmbMedico;
    private JTextField txtFecha;
    private JTextField txtHora;
    private JComboBox<String> cmbEstado;
    private JComboBox<String> cmbPrioridad;
    private JTextArea txtObservaciones;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private CitaMedicaDAO citaDAO;
    private boolean citaRegistrada;

    public RegistrarCitaDialog(JFrame parent) {
        super(parent, "Registrar Nueva Cita", true);
        this.citaDAO = new CitaMedicaDAO();
        this.citaRegistrada = false;
        initComponents();
        cargarDatos();
        setSize(550, 450);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Paciente:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        cmbPaciente = new JComboBox<>();
        panelCampos.add(cmbPaciente, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panelCampos.add(new JLabel("Medico:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        cmbMedico = new JComboBox<>();
        panelCampos.add(cmbMedico, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelCampos.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        txtFecha = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        panelCampos.add(txtFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelCampos.add(new JLabel("Hora (HH:MM):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2;
        txtHora = new JTextField("08:00");
        panelCampos.add(txtHora, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelCampos.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2;
        cmbEstado = new JComboBox<>(new String[]{"Programada", "Atendida", "Cancelada"});
        panelCampos.add(cmbEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelCampos.add(new JLabel("Prioridad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 2;
        cmbPrioridad = new JComboBox<>(new String[]{"Normal", "Urgente"});
        panelCampos.add(cmbPrioridad, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 1;
        panelCampos.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 2;
        txtObservaciones = new JTextArea(3, 20);
        txtObservaciones.setLineWrap(true);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        panelCampos.add(scrollObs, gbc);

        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Registrar Cita");
        btnGuardar.setBackground(new Color(46, 125, 50));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> registrarCita());

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        try {
            PacienteDAO pacienteDAO = new PacienteDAO();
            List<Paciente> pacientes = pacienteDAO.obtenerTodosPacientes();
            if (pacientes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay pacientes registrados.\nPor favor, registre pacientes primero.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                for (Paciente p : pacientes) {
                    cmbPaciente.addItem(p);
                }
            }

            MedicoDAO medicoDAO = new MedicoDAO();
            List<Medico> medicos = medicoDAO.obtenerTodosMedicos();
            if (medicos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay medicos registrados.\nPor favor, registre medicos primero.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                for (Medico m : medicos) {
                    cmbMedico.addItem(m);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void registrarCita() {
        try {
            Paciente paciente = (Paciente) cmbPaciente.getSelectedItem();
            Medico medico = (Medico) cmbMedico.getSelectedItem();

            if (paciente == null || medico == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un paciente y un medico.");
                return;
            }

            CitaMedica cita = new CitaMedica();
            cita.setPaciente(paciente);
            cita.setMedico(medico);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(txtFecha.getText().trim());
            cita.setFecha(fecha);

            cita.setHora(txtHora.getText().trim());
            cita.setEstado((String) cmbEstado.getSelectedItem());
            cita.setPrioridad((String) cmbPrioridad.getSelectedItem());
            cita.setObservaciones(txtObservaciones.getText().trim());

            if (citaDAO.registrarCita(cita)) {
                citaRegistrada = true;
                JOptionPane.showMessageDialog(this, "Cita registrada exitosamente. ID: " + cita.getIdCita());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la cita.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isCitaRegistrada() {
        return citaRegistrada;
    }
}