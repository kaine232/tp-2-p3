package vista;

import java.awt.Color;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

public class MarcadorDeRegiones extends MapMarkerDot {

	private static final long serialVersionUID = 1L;
	private final Color color;

	public MarcadorDeRegiones(String nombreProvincia, double latitud, double longitud, Color color) {
		super(latitud, longitud);
		this.color = color;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Color getBackColor() {
		return color;
	}
}