package modelo;

public class Horario {
    private int idHorario;
    private Medico medico;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String estado;

    public Horario() {}

    public Horario(int idHorario, Medico medico, String diaSemana,
                   String horaInicio, String horaFin, String estado) {
        this.idHorario = idHorario;
        this.medico = medico;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return medico.getNombres() + " - " + diaSemana + " (" + horaInicio + " - " + horaFin + ")";
    }
}