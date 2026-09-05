package modelo;

import java.util.Date;

public class CitaMedica {
    private int idCita;
    private Paciente paciente;
    private Medico medico;
    private Date fecha;
    private String hora;
    private String estado;
    private String prioridad;
    private String observaciones;

    public CitaMedica() {}

    public CitaMedica(int idCita, Paciente paciente, Medico medico, Date fecha,
                      String hora, String estado, String prioridad, String observaciones) {
        this.idCita = idCita;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.prioridad = prioridad;
        this.observaciones = observaciones;
    }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "Cita #" + idCita + " - " + paciente.getNombres() + " con " + medico.getNombres() + " (" + fecha + " " + hora + ")";
    }
}