package modelo;

public class ConstructorDeGrafos {

	public static Grafo construir(String[][] vecindades) {
		Grafo grafo = new Grafo();
			for (String[] par : vecindades) {
				Provincia origen = new Provincia(par[0]);
				Provincia destino = new Provincia(par[1]);
				grafo.agregarArista(origen, destino);
			}
		return grafo;
	}

}
