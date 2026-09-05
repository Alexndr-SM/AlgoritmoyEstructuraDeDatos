package estructura;

public class MatrizDisponibilidad {
    private boolean[][] disponibilidad;
    private String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    private String[] horas = {"08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00"};

    public MatrizDisponibilidad() {
        this.disponibilidad = new boolean[dias.length][horas.length];
        for (int i = 0; i < dias.length; i++) {
            for (int j = 0; j < horas.length; j++) {
                disponibilidad[i][j] = true;
            }
        }
    }

    public boolean verificarDisponibilidad(String dia, String hora) {
        int f = buscarDia(dia);
        int c = buscarHora(hora);
        if (f >= 0 && c >= 0) {
            return disponibilidad[f][c];
        }
        return false;
    }

    public void reservarHorario(String dia, String hora) {
        int f = buscarDia(dia);
        int c = buscarHora(hora);
        if (f >= 0 && c >= 0) {
            disponibilidad[f][c] = false;
        }
    }

    public void liberarHorario(String dia, String hora) {
        int f = buscarDia(dia);
        int c = buscarHora(hora);
        if (f >= 0 && c >= 0) {
            disponibilidad[f][c] = true;
        }
    }

    public void mostrarDisponibilidad() {
        System.out.print("\t");
        for (String h : horas) System.out.print(h + "\t");
        System.out.println();
        for (int i = 0; i < dias.length; i++) {
            System.out.print(dias[i] + "\t");
            for (int j = 0; j < horas.length; j++) {
                System.out.print((disponibilidad[i][j] ? "✅" : "❌") + "\t");
            }
            System.out.println();
        }
    }

    private int buscarDia(String dia) {
        for (int i = 0; i < dias.length; i++) {
            if (dias[i].equals(dia)) return i;
        }
        return -1;
    }

    private int buscarHora(String hora) {
        for (int i = 0; i < horas.length; i++) {
            if (horas[i].equals(hora)) return i;
        }
        return -1;
    }
}