package controlador;

import java.util.List;

import modelo.Grafo;
import modelo.Vecinos;

public class ControladorVecinos {
	
	private final Vecinos vecinos;

	public ControladorVecinos(List<String> nombres) {
		this.vecinos = new Vecinos(nombres);
	}
	
	public void unir(String provA, String provB, int peso) {
		vecinos.unirProvincias(provA, provB, peso);
	}
	
	public void separar(String provA, String provB) {
		vecinos.separarProvincias(provA, provB);
	}
	
	public Vecinos obtenerVecinos() {
		return vecinos;
	}
	
	public Grafo obtenerGrafo() {
		return vecinos.armarGrafo();
	}
}
