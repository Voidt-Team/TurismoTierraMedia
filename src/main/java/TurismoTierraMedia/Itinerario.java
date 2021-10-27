package TurismoTierraMedia;

import java.util.List;


public class Itinerario {

	private Integer id;
	private String fecha;
	private List<Atraccion> lAtracciones; 
	private List<Promocion> lPromociones;
	
	
	public Itinerario(Integer id, String fecha, List<Atraccion> lAtracciones, List<Promocion> lPromociones) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.lAtracciones = lAtracciones;
		this.lPromociones = lPromociones;
	}


	public Integer getId() {
		return id;
	}

	//setearemos con el id autogenerado de la tabla
	public void setId(Integer id) {
		this.id = id;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
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
		return "Itinerario [id=" + id + ", fecha=" + fecha + ", lAtracciones=" + lAtracciones + ", lPromociones="
				+ lPromociones + "]";
	}
	
	
	
	
	
}
