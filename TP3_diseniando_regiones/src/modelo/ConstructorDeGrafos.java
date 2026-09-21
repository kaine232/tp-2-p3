package modelo;

public class ConstructorDeGrafos {

	public class ConstructorDeGrafo {

		private ConstructorDeGrafo() {

		}

		public static Grafo construir(String[][] vecindades) {
			Grafo grafo = new Grafo();
			for (String[] par : vecindades) {
				Provincia origen = new Provincia(par[0]);
				Provincia destino = new Provincia(par[1]);
				if (!grafo.existeAristaEntreProvincias(origen, destino)) {
					grafo.agregarArista(origen, destino);
				}
			}
			return grafo;
		}
	}

}
