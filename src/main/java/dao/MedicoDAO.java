package dao;

import modelo.Medico;
import modelo.Especialidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public List<Medico> obtenerTodosMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT m.*, e.idEspecialidad, e.nombre as esp_nombre, e.descripcion as esp_descripcion " +
                "FROM Medico m " +
                "JOIN Especialidad e ON m.idEspecialidad = e.idEspecialidad " +
                "ORDER BY m.apellidos, m.nombres";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Especialidad esp = new Especialidad();
                esp.setIdEspecialidad(rs.getInt("idEspecialidad"));
                esp.setNombre(rs.getString("esp_nombre"));
                esp.setDescripcion(rs.getString("esp_descripcion"));

                Medico m = new Medico();
                m.setIdMedico(rs.getInt("idMedico"));
                m.setNombres(rs.getString("nombres"));
                m.setApellidos(rs.getString("apellidos"));
                m.setDni(rs.getString("dni"));
                m.setEspecialidad(esp);
                m.setTelefono(rs.getString("telefono"));
                m.setCorreo(rs.getString("correo"));
                medicos.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener médicos: " + e.getMessage());
        }
        return medicos;
    }

    public Medico buscarMedicoPorId(int idMedico) {
        String sql = "SELECT m.*, e.idEspecialidad, e.nombre as esp_nombre, e.descripcion as esp_descripcion " +
                "FROM Medico m " +
                "JOIN Especialidad e ON m.idEspecialidad = e.idEspecialidad " +
                "WHERE m.idMedico = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idMedico);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Especialidad esp = new Especialidad();
                    esp.setIdEspecialidad(rs.getInt("idEspecialidad"));
                    esp.setNombre(rs.getString("esp_nombre"));
                    esp.setDescripcion(rs.getString("esp_descripcion"));

                    Medico m = new Medico();
                    m.setIdMedico(rs.getInt("idMedico"));
                    m.setNombres(rs.getString("nombres"));
                    m.setApellidos(rs.getString("apellidos"));
                    m.setDni(rs.getString("dni"));
                    m.setEspecialidad(esp);
                    m.setTelefono(rs.getString("telefono"));
                    m.setCorreo(rs.getString("correo"));
                    return m;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar médico: " + e.getMessage());
        }
        return null;
    }
}