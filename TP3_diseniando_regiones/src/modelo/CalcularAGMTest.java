package modelo;
import org.junit.Test;
import static org.junit.Assert.*;
public class CalcularAGMTest {
	private Grafo grafoTriangulo() {
		Grafo g = new Grafo();
		Provincia a = new Provincia("Buenos Aires", 0.0, 0.0);
		Provincia b = new Provincia("Cordoba", 0.0, 0.0);
		Provincia c = new Provincia("Santa Fe", 0.0, 0.0);
		
		g.agregarArista(a, b, 1);
		g.agregarArista(b, c, 2);
		g.agregarArista(a, c, 3);
		
		return g;
	}
	@Test
	public void testAGMDeGrafoConexo() {
		Grafo g = grafoTriangulo();
		Grafo agm = CalculadorAGM.calcular(g);
		
		assertEquals(3,agm.cantidadProvincias());
		assertEquals(2, agm.getAristas().size());
	}
}
