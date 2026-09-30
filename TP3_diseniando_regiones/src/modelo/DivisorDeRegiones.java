package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DivisorDeRegiones {

	private Grafo arbolGM;
	
	public static Grafo grafoSinAristasMasPesadas(Grafo arbolOriginal, int cantidadRegiones) {
		Grafo arbolDivididoEnRegiones = new Grafo();
		for(Provincia provincia : arbolOriginal.getProvincias()) {
			arbolDivididoEnRegiones.agregarProvincia(provincia);
		}
		
		List<Arista> aristasPorPesoDescendiente = new ArrayList<>(arbolOriginal.getAristas());
		aristasPorPesoDescendiente.sort(Comparator.comparingInt(Arista::getPeso).reversed());
		
		return grafoEditado(cantidadRegiones, arbolDivididoEnRegiones, aristasPorPesoDescendiente);
	}

	private static Grafo grafoEditado(int cantidadABorrar, Grafo arbol, List<Arista> aristasOrdenadas) {
		int indiceABorrar = cantidadABorrar-1;
		for(int i = indiceABorrar; i<aristasOrdenadas.size(); i++) {
			Arista arista = aristasOrdenadas.get(i);
			arbol.agregarArista(arista.getProvinciaOrigen(), arista.getProvinciaDestino(), arista.getPeso());
		}
		return arbol;
	}
	
	private static List<Set<Provincia>> agruparRegiones(Grafo grafoEditado) { 
		List<Set<Provincia>> regiones = new ArrayList<>();
		Set<Provincia> provinciasYaEnRegiones = new HashSet<>();
		
		for(Provincia provincia : grafoEditado.getProvincias()) {
			if(!provinciasYaEnRegiones.contains(provincia)) {
				Set<Provincia> region = BFS.alcanzables(grafoEditado,provincia);
				regiones.add(region);
				provinciasYaEnRegiones.addAll(region);
			}
		}
		
		return regiones;
	}
	
}
