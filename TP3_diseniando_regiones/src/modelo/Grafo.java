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

    public void agregarArista(Provincia a, Provincia b) { //default
        agregarArista(a, b, 0);
    }
    
    public void agregarArista(Provincia a, Provincia b, int peso) { //dado un peso
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
    
    public boolean existeAristaEntreProvincias(Provincia a, Provincia b) {
    	List<Arista> aristasDeA = provinciasYSusAristas.get(a);
    	if (aristasDeA == null) {
    		return false;
    	}
    	for (Arista arista : aristasDeA) {
    		if(arista.getExtremoOpuesto(a).equals(b)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public List<Provincia> getProvincias() {
        return new ArrayList<>(provinciasYSusAristas.keySet());
    }
    
    public List<Arista> getAristasDe(Provincia p) {
        List<Arista> vecinas = provinciasYSusAristas.get(p);
        if (vecinas == null) {
            throw new IllegalArgumentException("La provincia no existe en el grafo: " + p);
        }
        return new ArrayList<>(vecinas);
    }
    
    public int cantidadProvincias() {
        return provinciasYSusAristas.size();
    }
    
}
