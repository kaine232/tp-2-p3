package modelo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BFS 
{
	public static boolean esConexo(Grafo g) {
		if (g == null) {
			throw new IllegalArgumentException("El grafo no puede ser null.");
		}

		if (g.cantidadProvincias() <= 1) {
			return true;
		}

		Provincia origen = g.getProvincias().get(0);
		return alcanzables(g, origen).size() == g.cantidadProvincias();
	}

	public static Set<Provincia> alcanzables(Grafo g, Provincia origen) {
		Set<Provincia> marcadas = new HashSet<>();
		List<Provincia> porVisitar = new LinkedList<>();

		porVisitar.add(origen);
		marcadas.add(origen);

		while (!porVisitar.isEmpty()) {
			Provincia actual = porVisitar.remove(0);

			for (Arista arista : g.getAristasDe(actual)) {
				Provincia vecino = arista.getExtremoOpuesto(actual);
				if (!marcadas.contains(vecino)) {
					marcadas.add(vecino);
					porVisitar.add(vecino);
				}
			}
		}

		return marcadas;
	}
}
