package modelo;

public class Provincia {

	public final String nombre;
	
	public Provincia (String nombre){
		if (nombre==null) {
			throw new IllegalArgumentException ("El nombre de provincia no puede ser vacío.");
		}
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
}
