package controlador;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import modelo.CalculadorAGM;
import modelo.DivisorDeRegiones;
import modelo.Grafo;
import modelo.LectorDeArchivos;
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
    
    public ControladorRegiones(VistaRegiones vista, ControladorVecinos controladorVecinos) {
    	this(vista, controladorVecinos.obtenerGrafo());
    }

    public static ControladorRegiones desdeArchivo(VistaRegiones vista, File archivo) throws IOException {
    	return new ControladorRegiones(vista, LectorDeArchivos.leer(archivo));
    }

    public int obtenerCantidadProvincias() {
    	return grafo.cantidadProvincias();
    }
    
}
