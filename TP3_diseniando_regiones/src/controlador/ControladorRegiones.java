package controlador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import modelo.Arista;
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
    
    public List<Object[]> obtenerUniones() {
    	List<Object[]> uniones = new ArrayList<>();
    	for (Arista arista : grafo.getAristas()) {
    		uniones.add(new Object[] {
    			arista.getProvinciaOrigen().getNombre(),
    			arista.getProvinciaDestino().getNombre(),
    			arista.getPeso() });
    	}
    	uniones.sort((x, y) -> ((String) x[0]).compareTo((String) y[0]));
    	return uniones;
    }

    public void actualizarPeso(String origen, String destino, int peso) {
    	for (Arista arista : grafo.getAristas()) {
    		boolean esLaUnion = arista.getProvinciaOrigen().getNombre().equals(origen)
    				&& arista.getProvinciaDestino().getNombre().equals(destino);
    		if (esLaUnion) {
    			arista.setPeso(peso);
    		}
    	}
    }
    
}
