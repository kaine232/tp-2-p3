package modelo;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Set;

public class DivisorDeRegionesTest {
	private Grafo grafoLinea() {
		Grafo g = new Grafo();
		Provincia p1 = new Provincia("Buenos Aires");
		Provincia p2 = new Provincia("Cordoba");
		Provincia p3 = new Provincia("Santa Fe");
		
		g.agregarArista(p1, p2, 1);
		g.agregarArista(p2, p3, 10);
		
		return g;
	}
	@Test
	public void testDivisorDosRegiones() {
		Grafo g = grafoLinea();
		Grafo agm = CalculadorAGM.calcular(g);
		
		List<Set<Provincia>> regiones = DivisorDeRegiones.obtenerRegiones(agm, 2);
		assertEquals(2,regiones.size());
	}
}
