package controlador;

import java.util.List;
import java.util.Set;

import modelo.Arista;
import modelo.CalculadorAGM;
import modelo.DivisorDeRegiones;
import modelo.Grafo;
import modelo.Provincia;

public class ControladorRegiones {

    private final VistaRegiones vista;
    private final Grafo grafo;

    public ControladorRegiones(VistaRegiones vista, Grafo grafo) {
        this.vista = vista;
        this.grafo = grafo;

    }

    public void ejecutarAlgoritmo(int cantidadRegiones) {
        try {
            Grafo arbol = CalculadorAGM.calcular(grafo);
            List<Set<Provincia>> regiones = DivisorDeRegiones.obtenerRegiones(arbol, cantidadRegiones);
            vista.mostrarRegiones(regiones);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }
}
