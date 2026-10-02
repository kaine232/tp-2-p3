package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DivisorDeRegiones {
	
	public static List<Set<Provincia>> obtenerRegiones(Grafo arbolOriginal, int cantidadRegiones) {
		int cantidadProvincias = arbolOriginal.cantidadProvincias();
		if(cantidadRegiones<1 ||cantidadRegiones>cantidadProvincias) {
			throw new IllegalArgumentException("La cantidad de regiones debe estar entre 1 y la cantidad de provincias en total");
		}
		
		Grafo grafoRecortado = grafoEditadoSinAristasMasPesadas(arbolOriginal, cantidadRegiones);
		
		return agruparRegiones(grafoRecortado);
	}

	private static Grafo grafoEditadoSinAristasMasPesadas(Grafo arbol, int cantidadRegiones) {
		Grafo grafoRecortado = new Grafo();
		List<Arista> aristasOrdenadas = new ArrayList<>(arbol.getAristas());
		int cantidadARecortar = cantidadRegiones-1;
		
		aristasOrdenadas.sort(Comparator.comparingInt(Arista::getPeso).reversed());
		
		for(Provincia provincia : arbol.getProvincias()) {
			grafoRecortado.agregarProvincia(provincia);
		}
		
		for(int i = cantidadARecortar; i<aristasOrdenadas.size(); i++) {
			Arista arista = aristasOrdenadas.get(i);
			grafoRecortado.agregarArista(arista.getProvinciaOrigen(), arista.getProvinciaDestino(), arista.getPeso());
		}
		return grafoRecortado;
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
