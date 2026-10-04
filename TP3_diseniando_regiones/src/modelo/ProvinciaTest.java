package modelo;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProvinciaTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testNombreNoPuedeSerNull() {
		new Provincia(null);
	}
	
	@Test
	public void testEqualsHashcode() {
		Provincia p1 = new Provincia("Buenos Aires");
		Provincia p2 = new Provincia("Buenos Aires");
		Provincia p3 = new Provincia("Cordoba");
		
		assertEquals(p1,p2);
		assertNotEquals(p1,p3);
		assertEquals(p1.hashCode(),p2.hashCode());
	}
}
