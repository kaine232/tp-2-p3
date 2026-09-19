package modelo;

import java.util.Objects;

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
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provincia)) return false;
        Provincia that = (Provincia) o;
        return nombre.equals(that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
	
    @Override
    public String toString() {
        return this.nombre;
    }
}
