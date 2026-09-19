package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {

	private final Map<Provincia, List <Arista>> provinciasYSusAristas = new HashMap<>();
	private final List<Arista> aristas = new ArrayList<>();

    public void agregarProvincia(Provincia provincia) {
        provinciasYSusAristas.putIfAbsent(provincia, new ArrayList<>());
    }

    public void agregarArista(Provincia a, Provincia b) {
        agregarArista(a, b, 0);
    }
    
    public void agregarArista(Provincia a, Provincia b, int peso) {
        agregarProvincia(a);
        agregarProvincia(b);
        if(existeAristaEntreProvincias(a,b)) {
        	throw new IllegalArgumentException("Ya existe una arista entre estas provincias");
        }
        Arista arista = new Arista(a, b, peso);
        aristas.add(arista);
        provinciasYSusAristas.get(a).add(arista);
        provinciasYSusAristas.get(b).add(arista);
    }
    
    private boolean existeAristaEntreProvincias(Provincia a, Provincia b) {
    	for (Arista arista : provinciasYSusAristas.get(a)) {
    		if(arista.getExtremoOpuesto(a).equals(b)) {
    			return true;
    		}
    	}
    	return false;
    }
    
}
