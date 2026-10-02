package modelo;

import java.util.List;
import java.util.Set;

public class PresenterRegiones {

    private final VistaRegiones vista;
    private final Grafo grafo;

    public PresenterRegiones(VistaRegiones vista, Grafo grafo) {
        this.vista = vista;
        this.grafo = grafo;

        vista.mostrarAristasParaCargarPesos(grafo.getAristas());
    }


    public void actualizarPesoLuegoDeEditar(Arista arista, int nuevoPeso) {
        arista.setPeso(nuevoPeso);
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
