package modelo;

public class Medico {
    private int idMedico;
    private String nombres;
    private String apellidos;
    private String dni;
    private Especialidad especialidad;
    private String telefono;
    private String correo;

    public Medico() {}

    public Medico(int idMedico, String nombres, String apellidos, String dni,
                  Especialidad especialidad, String telefono, String correo) {
        this.idMedico = idMedico;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    @Override
    public String toString() {
        return nombres + " " + apellidos + " - " + especialidad.getNombre();
    }
}