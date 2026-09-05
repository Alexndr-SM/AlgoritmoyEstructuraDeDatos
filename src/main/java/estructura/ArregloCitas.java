package estructura;

import modelo.CitaMedica;

public class ArregloCitas {
    private CitaMedica[] citas;
    private int capacidad;
    private int tamaño;

    public ArregloCitas(int capacidad) {
        this.capacidad = capacidad;
        this.citas = new CitaMedica[capacidad];
        this.tamaño = 0;
    }

    public boolean insertar(CitaMedica c) {
        if (tamaño < capacidad) {
            citas[tamaño] = c;
            tamaño++;
            return true;
        }
        return false;
    }

    public CitaMedica buscarPorId(int id) {
        for (int i = 0; i < tamaño; i++) {
            if (citas[i].getIdCita() == id) {
                return citas[i];
            }
        }
        return null;
    }

    public CitaMedica[] buscarPorPaciente(int idPaciente) {
        CitaMedica[] resultado = new CitaMedica[tamaño];
        int cont = 0;
        for (int i = 0; i < tamaño; i++) {
            if (citas[i].getPaciente().getIdPaciente() == idPaciente) {
                resultado[cont] = citas[i];
                cont++;
            }
        }
        return resultado;
    }

    public void ordenarPorFecha() {
        for (int i = 0; i < tamaño - 1; i++) {
            for (int j = 0; j < tamaño - 1 - i; j++) {
                if (citas[j].getFecha().after(citas[j + 1].getFecha())) {
                    CitaMedica temp = citas[j];
                    citas[j] = citas[j + 1];
                    citas[j + 1] = temp;
                }
            }
        }
    }

    public ArregloCitas fusionar(ArregloCitas otro) {
        ArregloCitas fusion = new ArregloCitas(this.tamaño + otro.tamaño);
        for (int i = 0; i < this.tamaño; i++) {
            fusion.insertar(this.citas[i]);
        }
        for (int i = 0; i < otro.tamaño; i++) {
            fusion.insertar(otro.citas[i]);
        }
        return fusion;
    }
}