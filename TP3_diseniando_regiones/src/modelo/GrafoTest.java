package modelo;
import org.junit.Test;
import static org.junit.Assert.*;
public class GrafoTest {
	//metodos auxiliares
	private Provincia provincia(String nombre) {
		return new Provincia(nombre);
	}
	private Grafo grafoConArista(String nombreA, String nombreB, int peso) {
		Grafo g = new Grafo();
		g.agregarArista(provincia(nombreA), provincia(nombreB), peso);
		return g;
	}
	@Test
	public void testAgregarProvinciaYArista() {
		Grafo g = grafoConArista("Buenos Aires", "Cordoba", 5);
		
		assertTrue(g.existeAristaEntreProvincias(provincia("Buenos Aires"), provincia("Cordoba")));
		assertEquals(2, g.cantidadProvincias());
	}
	@Test
	public void testAristaDuplicadaNoSeAgrega() {
		Grafo g = grafoConArista("Buenos Aires", "Cordoba",5);
		
		assertFalse(g.agregarArista(provincia("Buenos Aires"),provincia("Cordoba"), 5));
		assertFalse(g.agregarArista(provincia("Cordoba"),provincia("Buenos Aires"), 5));
		
	}
}
