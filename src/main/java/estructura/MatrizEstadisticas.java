package estructura;

public class MatrizEstadisticas {
    private int[][] atenciones;

    public MatrizEstadisticas(int numEspecialidades, int numDias) {
        this.atenciones = new int[numEspecialidades][numDias];
    }

    public void registrarAtencion(int idEspecialidad, int dia) {
        if (idEspecialidad >= 0 && idEspecialidad < atenciones.length
                && dia >= 0 && dia < atenciones[0].length) {
            atenciones[idEspecialidad][dia]++;
        }
    }

    public int obtenerAtencionesDia(int idEspecialidad, int dia) {
        if (idEspecialidad >= 0 && idEspecialidad < atenciones.length
                && dia >= 0 && dia < atenciones[0].length) {
            return atenciones[idEspecialidad][dia];
        }
        return 0;
    }

    public int[] obtenerTotalPorEspecialidad() {
        int[] totales = new int[atenciones.length];
        for (int i = 0; i < atenciones.length; i++) {
            for (int j = 0; j < atenciones[i].length; j++) {
                totales[i] += atenciones[i][j];
            }
        }
        return totales;
    }
}