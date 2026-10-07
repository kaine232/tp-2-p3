package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class LectorDeArchivos {

	public static Grafo leer(File archivo) throws IOException {
		Grafo grafo = new Grafo();

		try (BufferedReader lector = Files.newBufferedReader(archivo.toPath(), StandardCharsets.UTF_8)) {
			String linea;
			int numeroLinea = 0;
			while ((linea = lector.readLine()) != null) {
				numeroLinea++;
				linea = linea.replace("\uFEFF", "").trim();
				if (!linea.isEmpty()) {
					agregarLinea(grafo, linea, numeroLinea);
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

	private static void agregarLinea(Grafo grafo, String linea, int numeroLinea) {
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

		grafo.agregarArista(new Provincia(a), new Provincia(b), peso);
	}
}