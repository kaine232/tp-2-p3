package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class CalculadorAGM {

    public static Grafo calcular(Grafo grafoOriginal) {
        if (!BFS.esConexo(grafoOriginal)) {
            throw new IllegalArgumentException(
                "No se puede calcular el árbol generador mínimo de un grafo no conexo"
            );
        }

        Grafo arbol = new Grafo();
        for (Provincia provincia : grafoOriginal.getProvincias()) {
            arbol.agregarProvincia(provincia);
        }

        List<Arista> aristasOrdenadasAscendente = new ArrayList<>(grafoOriginal.getAristas());
        aristasOrdenadasAscendente.sort(Comparator.comparingInt(Arista::getPeso));

        for (Arista arista : aristasOrdenadasAscendente) {
        	Provincia origen = arista.getProvinciaOrigen();
            Provincia destino = arista.getProvinciaDestino();

            boolean formariaCiclo = BFS.alcanzables(arbol, origen).contains(destino);
            if (!formariaCiclo) {
                arbol.agregarArista(origen, destino, arista.getPeso());
            }
        }

        return arbol;
    }
}