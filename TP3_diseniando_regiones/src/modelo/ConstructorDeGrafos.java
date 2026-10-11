package modelo;

import java.util.Map;

public class ConstructorDeGrafos {

	public static Grafo construir(String[][] vecindades, Map<String, Coordenada> coordenadas) {
		Grafo grafo = new Grafo();
			for (String[] par : vecindades) {
				Provincia origen = crearProvincia(par[0], coordenadas);
				Provincia destino = crearProvincia(par[1], coordenadas);
				grafo.agregarArista(origen, destino);
			}
		return grafo;
	}
	
	private static Provincia crearProvincia(String nombre, Map<String, Coordenada> coordenadas) {
		Coordenada coordenada = coordenadas.get(nombre);
		if (coordenada == null) {
			throw new IllegalArgumentException("Falta la coordenada de la provincia: " + nombre);
		}
		return new Provincia(nombre, coordenada.getLatitud(), coordenada.getLongitud());
	}

}
