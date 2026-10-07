package controlador;

import java.util.List;
import java.util.Set;

import modelo.Provincia;

public interface VistaRegiones {
	
	void mostrarRegiones(List<Set<Provincia>> regiones);
	
	void mostrarError(String mensaje);
}
