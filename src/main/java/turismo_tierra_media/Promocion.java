package turismo_tierra_media;
import java.util.List;

public abstract class Promocion {
	
	protected String nombre;
	protected List<Atraccion> atracciones;
	
	public Promocion(String nombrePromocion, List<Atraccion> atraccionesIncluidas) {
		super();
		this.nombre = nombrePromocion;
		this.atracciones = atraccionesIncluidas;
	}

	public String getNombre() { 
		return nombre;
	}
	
	public List<Atraccion> getAtracciones() {
		return atracciones;
	}
	
	//Metodos implementados en las subclases
	public abstract String ImprimirBonus();
	
	public abstract double costoPromocion();

	public abstract double tiempoPromocion();
	
	public abstract int tipoPromocion();
	
}
