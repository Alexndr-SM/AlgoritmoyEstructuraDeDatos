package dao;

import modelo.CitaMedica;
import modelo.Paciente;
import modelo.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaMedicaDAO {

    public boolean registrarCita(CitaMedica cita) {
        String sql = "INSERT INTO CitaMedica (idPaciente, idMedico, fecha, hora, estado, prioridad, observaciones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, cita.getPaciente().getIdPaciente());
            pstmt.setInt(2, cita.getMedico().getIdMedico());
            pstmt.setDate(3, new java.sql.Date(cita.getFecha().getTime()));
            pstmt.setString(4, cita.getHora());
            pstmt.setString(5, cita.getEstado());
            pstmt.setString(6, cita.getPrioridad());
            pstmt.setString(7, cita.getObservaciones());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cita.setIdCita(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar cita: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<CitaMedica> obtenerTodasCitas() {
        List<CitaMedica> citas = new ArrayList<>();
        String sql = "SELECT c.*, p.nombres as p_nombres, p.apellidos as p_apellidos, p.dni as p_dni, " +
                "m.nombres as m_nombres, m.apellidos as m_apellidos " +
                "FROM CitaMedica c " +
                "JOIN Paciente p ON c.idPaciente = p.idPaciente " +
                "JOIN Medico m ON c.idMedico = m.idMedico " +
                "ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CitaMedica cita = mapResultSetToCita(rs);
                citas.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener citas: " + e.getMessage());
            e.printStackTrace();
        }
        return citas;
    }

    public CitaMedica buscarCitaPorId(int idCita) {
        String sql = "SELECT c.*, p.nombres as p_nombres, p.apellidos as p_apellidos, p.dni as p_dni, " +
                "m.nombres as m_nombres, m.apellidos as m_apellidos " +
                "FROM CitaMedica c " +
                "JOIN Paciente p ON c.idPaciente = p.idPaciente " +
                "JOIN Medico m ON c.idMedico = m.idMedico " +
                "WHERE c.idCita = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCita);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCita(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cita: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<CitaMedica> buscarCitasPorDniPaciente(String dni) {
        List<CitaMedica> citas = new ArrayList<>();
        String sql = "SELECT c.*, p.nombres as p_nombres, p.apellidos as p_apellidos, p.dni as p_dni, " +
                "m.nombres as m_nombres, m.apellidos as m_apellidos " +
                "FROM CitaMedica c " +
                "JOIN Paciente p ON c.idPaciente = p.idPaciente " +
                "JOIN Medico m ON c.idMedico = m.idMedico " +
                "WHERE p.dni = ? " +
                "ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    citas.add(mapResultSetToCita(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar citas por DNI: " + e.getMessage());
            e.printStackTrace();
        }
        return citas;
    }

    public boolean actualizarCita(CitaMedica cita) {
        String sql = "UPDATE CitaMedica SET idPaciente = ?, idMedico = ?, fecha = ?, hora = ?, " +
                "estado = ?, prioridad = ?, observaciones = ? WHERE idCita = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cita.getPaciente().getIdPaciente());
            pstmt.setInt(2, cita.getMedico().getIdMedico());
            pstmt.setDate(3, new java.sql.Date(cita.getFecha().getTime()));
            pstmt.setString(4, cita.getHora());
            pstmt.setString(5, cita.getEstado());
            pstmt.setString(6, cita.getPrioridad());
            pstmt.setString(7, cita.getObservaciones());
            pstmt.setInt(8, cita.getIdCita());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cita: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean cancelarCita(int idCita) {
        String sql = "DELETE FROM CitaMedica WHERE idCita = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCita);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al cancelar cita: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private CitaMedica mapResultSetToCita(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(rs.getInt("idPaciente"));
        paciente.setNombres(rs.getString("p_nombres"));
        paciente.setApellidos(rs.getString("p_apellidos"));
        paciente.setDni(rs.getString("p_dni"));

        Medico medico = new Medico();
        medico.setIdMedico(rs.getInt("idMedico"));
        medico.setNombres(rs.getString("m_nombres"));
        medico.setApellidos(rs.getString("m_apellidos"));

        CitaMedica cita = new CitaMedica();
        cita.setIdCita(rs.getInt("idCita"));
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFecha(rs.getDate("fecha"));
        cita.setHora(rs.getString("hora"));
        cita.setEstado(rs.getString("estado"));
        cita.setPrioridad(rs.getString("prioridad"));
        cita.setObservaciones(rs.getString("observaciones"));

        return cita;
    }
}