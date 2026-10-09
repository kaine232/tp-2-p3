package controlador;

import java.util.List;
import java.util.Map;

import modelo.Grafo;
import modelo.Vecinos;

public class ControladorVecinos {

	private final Vecinos vecinos;

	public ControladorVecinos(List<String> nombres) {
		this.vecinos = new Vecinos(nombres);
	}

	public void unir(String a, String b, int peso) {
		vecinos.unirProvincias(a, b, peso);
	}

	public void separar(String a, String b) {
		vecinos.separarProvincias(a, b);
	}

	public boolean sonVecinas(String a, String b) {
		return vecinos.sonVecinas(a, b);
	}

	public int obtenerPeso(String a, String b) {
		return vecinos.getPeso(a, b);
	}

	public Map<String, Integer> obtenerVecinas(String provincia) {
		return vecinos.getVecinas(provincia);
	}

	public boolean esConexo() {
		return vecinos.esConexo();
	}

	public Grafo obtenerGrafo() {
		return vecinos.armarGrafo();
	}
}