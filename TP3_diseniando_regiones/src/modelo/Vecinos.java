package modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vecinos {

	private final List<String> nombres;
	private final Map<String, Map<String, Integer>> vecinos = new HashMap<>();
	private final Map<String, Coordenada> coordenadas;
	
	public Vecinos(List<String> nombres, Map<String, Coordenada> coordenadas) {
		this.nombres = nombres;
		this.coordenadas = coordenadas;
		for (String nombre : nombres) {
			vecinos.put(nombre, new HashMap<>());
		}
	}
	
	public void unirProvincias(String vecino1, String vecino2, int peso) {
		vecinos.get(vecino1).put(vecino2, peso);
		vecinos.get(vecino2).put(vecino1, peso);
	}
	
	public void separarProvincias(String vecino1, String vecino2) {
		vecinos.get(vecino1).remove(vecino2);
		vecinos.get(vecino2).remove(vecino1);
	}
	
	public boolean sonVecinas(String a, String b) {
		return vecinos.get(a).containsKey(b);
	}

	public int getPeso(String a, String b) {
		return vecinos.get(a).get(b);
	}

	public Map<String, Integer> getVecinas(String provincia) {
		return new HashMap<>(vecinos.get(provincia));
	}

	public boolean esConexo() {
		return BFS.esConexo(armarGrafo());
	}

	public Grafo armarGrafo() {
		Grafo grafo = new Grafo();
		for (String nombre : nombres) {
			grafo.agregarProvincia(crearProvincia(nombre));
		}
		for (String a : nombres) {
			for (String b : vecinos.get(a).keySet()) {
				grafo.agregarArista(crearProvincia(a), crearProvincia(b), vecinos.get(a).get(b));
			}
		}
		return grafo;
	}
	
	private Provincia crearProvincia(String nombre) {
		Coordenada coordenada = coordenadas.get(nombre);
		if (coordenada == null) {
			throw new IllegalArgumentException("Falta la coordenada de la provincia: " + nombre);
		}
		return new Provincia(nombre, coordenada.getLatitud(), coordenada.getLongitud());
	}
}
