package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LectorDeArchivos {

	public static Grafo leer(File archivo) throws IOException {
		Map<String, Coordenada> coordenadas = new HashMap<>();
		Grafo grafo = new Grafo();

		try (BufferedReader lector = Files.newBufferedReader(archivo.toPath(), StandardCharsets.UTF_8)) {
			String linea;
			int numeroLinea = 0;
			while ((linea = lector.readLine()) != null) {
				numeroLinea++;
				linea = linea.replace("\uFEFF", "").trim();
				if (linea.isEmpty()) {
					continue;
				}
				if (linea.startsWith("COORD,")) {
					agregarCoordenada(coordenadas, linea, numeroLinea);
				} else {
					agregarLinea(grafo, coordenadas, linea, numeroLinea);
				}
			}
		}

		if (grafo.cantidadProvincias() == 0) {
			throw new IllegalArgumentException("El archivo está vacío.");
		}
		
		if (!BFS.esConexo(grafo)) {
			throw new IllegalArgumentException("El grafo del archivo no es conexo: todas las provincias deben estar conectadas entre sí.");
		}
		return grafo;
	}

	private static void agregarLinea(Grafo grafo, Map<String, Coordenada> coordenadas, String linea, int numeroLinea) {
		String[] partes = linea.split(",");
		if (partes.length != 3) {
			throw new IllegalArgumentException("Línea " + numeroLinea + " inválida. El formato es: provincia1,provincia2,peso");
		}

		String a = partes[0].trim();
		String b = partes[1].trim();
		if (a.isEmpty() || b.isEmpty()) {
			throw new IllegalArgumentException("Línea " + numeroLinea + ": falta el nombre de una provincia.");
		}
		if (a.equals(b)) {
			throw new IllegalArgumentException("Línea " + numeroLinea + ": una provincia no puede ser vecina de sí misma.");
		}

		int peso;
		try {
			peso = Integer.parseInt(partes[2].trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Línea " + numeroLinea + ": el peso debe ser un número entero.");
		}
		if (peso <= 0) {
			throw new IllegalArgumentException("Línea " + numeroLinea + ": el peso debe ser mayor a 0.");
		}

		grafo.agregarArista(crearProvincia(a, coordenadas, numeroLinea),crearProvincia(b, coordenadas, numeroLinea),peso);
	}
	
	private static Provincia crearProvincia(String nombre, Map<String, Coordenada> coordenadas, int numeroLinea) {
		Coordenada coordenada = coordenadas.get(nombre);
		if (coordenada == null) {
			throw new IllegalArgumentException(
				"Línea " + numeroLinea + ": falta la línea COORD de \"" + nombre + "\" (tiene que aparecer antes que sus aristas en el archivo)."
			);
		}
		return new Provincia(nombre, coordenada.getLatitud(), coordenada.getLongitud());
	}
	
	private static void agregarCoordenada(Map<String, Coordenada> coordenadas, String linea, int numeroLinea) {
		String[] partes = linea.split(",");
		if (partes.length != 4) {
			throw new IllegalArgumentException("Línea " + numeroLinea + " inválida. El formato es: COORD,provincia,latitud,longitud");
		}

		String nombre = partes[1].trim();
		double latitud;
		double longitud;
		try {
			latitud = Double.parseDouble(partes[2].trim());
			longitud = Double.parseDouble(partes[3].trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Línea " + numeroLinea + ": la latitud y la longitud deben ser números.");
		}

		coordenadas.put(nombre, new Coordenada(latitud, longitud));
	}
}