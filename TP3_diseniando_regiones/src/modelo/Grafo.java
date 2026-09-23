package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grafo {

	private final Map<Provincia, List <Arista>> provinciasYSusAristas = new HashMap<>();
	private final Set<Arista> aristas = new HashSet<>();

    public void agregarProvincia(Provincia provincia) {
        provinciasYSusAristas.putIfAbsent(provincia, new ArrayList<>());
    }

    public boolean agregarArista(Provincia a, Provincia b) { //default
        return agregarArista(a, b, 0);
    }
    
    public boolean agregarArista(Provincia a, Provincia b, int peso) { //dado un peso
        agregarProvincia(a);
        agregarProvincia(b);

        Arista arista = new Arista(a, b, peso);
        boolean esNueva = aristas.add(arista); // false si ya existía (en cualquier direccion)

        if (esNueva) {
            provinciasYSusAristas.get(a).add(arista);
            provinciasYSusAristas.get(b).add(arista);
        }

        return esNueva;
    }
    
    public boolean existeAristaEntreProvincias(Provincia a, Provincia b) {
    	return aristas.contains(new Arista(a, b, 0));
    }
    
    public List<Provincia> getProvincias() {
        return new ArrayList<>(provinciasYSusAristas.keySet());
    }
    
    public List<Arista> getAristas() {
        return new ArrayList<>(aristas);
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
