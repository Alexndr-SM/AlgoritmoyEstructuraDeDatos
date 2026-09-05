package dao;

import modelo.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public List<Paciente> obtenerTodosPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM Paciente ORDER BY apellidos, nombres";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(rs.getInt("idPaciente"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setDni(rs.getString("dni"));
                p.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                p.setSexo(rs.getString("sexo"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
                p.setCorreo(rs.getString("correo"));
                pacientes.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pacientes: " + e.getMessage());
        }
        return pacientes;
    }

    public Paciente buscarPacientePorDni(String dni) {
        String sql = "SELECT * FROM Paciente WHERE dni = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente();
                    p.setIdPaciente(rs.getInt("idPaciente"));
                    p.setNombres(rs.getString("nombres"));
                    p.setApellidos(rs.getString("apellidos"));
                    p.setDni(rs.getString("dni"));
                    p.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                    p.setSexo(rs.getString("sexo"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setDireccion(rs.getString("direccion"));
                    p.setCorreo(rs.getString("correo"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar paciente: " + e.getMessage());
        }
        return null;
    }
}