package TurismoTierraMedia;

import java.util.List;


public class Itinerario {

	private Integer id;
	private List<Atraccion> lista_atracciones; //aca van a ir todas las atracciones compradas
	private List<Promocion> lista_promociones; // aca van a ir las promos compradas
	
	//si genero un constructor con atracciones y otro con promo me da error porque ambos reciben
	//integer y lista, evidentemente no distingue su tipo de lista...
	public Itinerario(Integer id, List<Atraccion> lista_atracciones, List<Promocion> lista_promociones) {
		super();
		this.id = id;
		this.lista_atracciones = lista_atracciones;
		this.lista_promociones = lista_promociones;
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
