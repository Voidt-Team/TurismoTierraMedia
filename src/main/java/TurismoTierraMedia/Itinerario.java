package TurismoTierraMedia;

import java.util.List;


public class Itinerario {

	private Integer id;
	private List<Atraccion> lAtracciones; 
	private List<Promocion> lPromociones;
	
	
	public Itinerario(Integer id, List<Atraccion> lAtracciones, List<Promocion> lPromociones) {
		super();
		this.id = id;
		this.lAtracciones = lAtracciones;
		this.lPromociones = lPromociones;
	}


	public Integer getId() {
		return id;
	}

	//necesitamos este metodo para poder sincronizar el id con el autogenerado en la base de datos...
	public void setId(Integer id) {
		this.id = id;
	}


	public List<Atraccion> getlAtracciones() {
		return lAtracciones;
	}


	public void setlAtracciones(List<Atraccion> lAtracciones) {
		this.lAtracciones = lAtracciones;
	}


	public List<Promocion> getlPromociones() {
		return lPromociones;
	}


	public void setlPromociones(List<Promocion> lPromociones) {
		this.lPromociones = lPromociones;
	}


	@Override
	public String toString() {
		return "Itinerario [id=" + id + ", lAtracciones=" + lAtracciones + ", lPromociones=" + lPromociones + "]";
	}
	
	
	
	
}
