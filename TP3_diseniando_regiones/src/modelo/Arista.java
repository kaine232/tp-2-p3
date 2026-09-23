package modelo;

public class Arista {

	private Provincia provinciaOrigen;
	private Provincia provinciaDestino;
	private int peso;
	
	public Arista (Provincia origen, Provincia destino, int peso) {
        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Los extremos de la arista no pueden estar vacíos");
        }
        if (origen.equals(destino)) {
            throw new IllegalArgumentException("Las provincias no pueden conectarse consigo mismas");
        }
		this.provinciaOrigen=origen;
		this.provinciaDestino=destino;
		this.peso=peso;
	}
	
	public Provincia getProvinciaOrigen() {
		return this.provinciaOrigen;
	}
	
	public Provincia getProvinciaDestino(){
		return this.provinciaDestino;
	}
	
	public int getPeso() {
		return this.peso;
	}
	
	public Provincia getExtremoOpuesto(Provincia prov) {
		if (prov.equals(this.provinciaOrigen)) return this.provinciaDestino;
        if (prov.equals(this.provinciaDestino)) return this.provinciaOrigen;
        throw new IllegalArgumentException("Esta arista no se relaciona con la provincia");
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Arista)) return false;
		Arista that = (Arista) o;

		boolean mismoSentido = provinciaOrigen.equals(that.provinciaOrigen)
				&& provinciaDestino.equals(that.provinciaDestino);
		boolean sentidoInvertido = provinciaOrigen.equals(that.provinciaDestino)
				&& provinciaDestino.equals(that.provinciaOrigen);

		return mismoSentido || sentidoInvertido;
	}

	@Override
	public int hashCode() {
		return provinciaOrigen.hashCode() + provinciaDestino.hashCode();
	}
}
