package modelo;

import java.util.List;
import java.util.Set;

public interface VistaRegiones {

	void mostrarAristasParaCargarPesos(List<Arista> aristas);
	
	void mostrarRegiones(List<Set<Provincia>> regiones);
	
	void mostrarError(String mensaje);
}
