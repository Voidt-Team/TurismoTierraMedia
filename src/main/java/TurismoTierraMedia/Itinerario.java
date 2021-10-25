package TurismoTierraMedia;

import java.util.List;


public class Itinerario {

	private Integer id;
	private Atraccion atraccion; 
	private Promocion promocion;
	

	public Itinerario(Integer id, Atraccion atraccion, Promocion promocion) {
		super();
		this.id = id;
		this.atraccion = atraccion;
		this.promocion = promocion;
	}

	protected Integer getId() {
		return id;
	}

	protected List<Atraccion> getLista_atracciones() {
		return lista_atracciones;
	}

	protected void setLista_atracciones(List<Atraccion> lista_atracciones) {
		this.lista_atracciones = lista_atracciones;
	}

	protected List<Promocion> getLista_promociones() {
		return lista_promociones;
	}

	protected void setLista_promociones(List<Promocion> lista_promociones) {
		this.lista_promociones = lista_promociones;
	}

	@Override
	public String toString() {
		return "Itinerario [id=" + id + ", lista_atracciones=" + lista_atracciones + ", lista_promociones="
				+ lista_promociones + "]";
	}
	
	
	
}
