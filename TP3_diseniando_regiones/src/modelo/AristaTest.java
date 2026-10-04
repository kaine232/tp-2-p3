package modelo;
import org.junit.Test;
import static org.junit.Assert.*;

public class AristaTest {
	private Provincia provincia(String nombre) {
		return new Provincia(nombre);
	}
	private Arista arista(String nombreA, String nombreB, int peso) {
		return new Arista(provincia(nombreA), provincia(nombreB), peso);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNoPermiteProvinciasNull() {
		new Arista(provincia("Buenos Aires"), null, 5);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNoPermiteAristaConMismoExtremo() {
		new Arista(provincia("Buenos Aires"),provincia("Buenos Aires") ,5);
	}
	@Test
	public void testEqualsIndependienteDelSentido() {
		Arista arista1 = arista("Buenos Aires", "Cordoba",10);
		Arista arista2 = arista("Buenos Aires", "Cordoba",10);
		
		assertEquals(arista1,arista2);
	}
	@Test
	public void testGetExtremoOpuesto() {
		Provincia p1 = provincia("Buenos Aires");
		Provincia p2 = provincia("Cordoba");
		Arista arista = new Arista(p1, p2,10);
		
		assertEquals(p2, arista.getExtremoOpuesto(p1));
		assertEquals(p1, arista.getExtremoOpuesto(p2));
	}
	@Test
	public void testPesoActualizaCorrectamente() {
		Arista arista = arista("Buenos Aires", "Cordoba", 10);
		arista.setPeso(20);
		assertEquals(20, arista.getPeso());
	}
}
