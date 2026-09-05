package modelo;

import java.util.Date;

public class Turno {
    private int idTurno;
    private Paciente paciente;
    private Medico medico;
    private CitaMedica cita;
    private int numeroTurno;
    private Date fecha;
    private String horaAtencion;
    private String estado;
    private String prioridad;

    public Turno() {}

    public Turno(int idTurno, Paciente paciente, Medico medico, CitaMedica cita,
                 int numeroTurno, Date fecha, String horaAtencion,
                 String estado, String prioridad) {
        this.idTurno = idTurno;
        this.paciente = paciente;
        this.medico = medico;
        this.cita = cita;
        this.numeroTurno = numeroTurno;
        this.fecha = fecha;
        this.horaAtencion = horaAtencion;
        this.estado = estado;
        this.prioridad = prioridad;
    }

    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public CitaMedica getCita() { return cita; }
    public void setCita(CitaMedica cita) { this.cita = cita; }

    public int getNumeroTurno() { return numeroTurno; }
    public void setNumeroTurno(int numeroTurno) { this.numeroTurno = numeroTurno; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getHoraAtencion() { return horaAtencion; }
    public void setHoraAtencion(String horaAtencion) { this.horaAtencion = horaAtencion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    @Override
    public String toString() {
        return "Turno #" + numeroTurno + " - " + paciente.getNombres() + " (" + estado + ")";
    }
}