package estructura;

import modelo.Paciente;

public class ArregloPacientes {
    private Paciente[] pacientes;
    private int capacidad;
    private int tamaño;

    public ArregloPacientes(int capacidad) {
        this.capacidad = capacidad;
        this.pacientes = new Paciente[capacidad];
        this.tamaño = 0;
    }

    public boolean insertar(Paciente p) {
        if (tamaño < capacidad) {
            pacientes[tamaño] = p;
            tamaño++;
            return true;
        }
        return false;
    }

    public boolean actualizar(int indice, Paciente p) {
        if (indice >= 0 && indice < tamaño) {
            pacientes[indice] = p;
            return true;
        }
        return false;
    }

    public boolean eliminar(int indice) {
        if (indice >= 0 && indice < tamaño) {
            for (int i = indice; i < tamaño - 1; i++) {
                pacientes[i] = pacientes[i + 1];
            }
            pacientes[tamaño - 1] = null;
            tamaño--;
            return true;
        }
        return false;
    }

    public void recorrer() {
        for (int i = 0; i < tamaño; i++) {
            System.out.println(pacientes[i]);
        }
    }

    public Paciente buscarPorDNI(String dni) {
        for (int i = 0; i < tamaño; i++) {
            if (pacientes[i].getDni().equals(dni)) {
                return pacientes[i];
            }
        }
        return null;
    }

    public Paciente[] buscarPorNombre(String nombre) {
        Paciente[] resultado = new Paciente[tamaño];
        int cont = 0;
        for (int i = 0; i < tamaño; i++) {
            if (pacientes[i].getNombres().toLowerCase().contains(nombre.toLowerCase())) {
                resultado[cont] = pacientes[i];
                cont++;
            }
        }
        return resultado;
    }

    public ArregloPacientes copiar() {
        ArregloPacientes copia = new ArregloPacientes(this.capacidad);
        for (int i = 0; i < this.tamaño; i++) {
            copia.insertar(this.pacientes[i]);
        }
        return copia;
    }

    public int getTamaño() { return tamaño; }
    public Paciente getPaciente(int indice) { return pacientes[indice]; }
}