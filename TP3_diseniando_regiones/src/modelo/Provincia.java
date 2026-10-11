package modelo;

import java.util.Objects;

public class Provincia {

	public final String nombre;
	private final double latitud;
	private final double longitud;
	
	public Provincia (String nombre, double latitud, double longitud){
		if (nombre==null) {
			throw new IllegalArgumentException ("El nombre de provincia no puede ser vacío.");
		}
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public double getLatitud() {
		return this.latitud;
	}
	
	public double getLongitud() {
		return this.longitud;
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
