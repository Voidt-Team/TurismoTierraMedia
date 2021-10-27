package TurismoTierraMedia;

import java.util.List;


public class Itinerario {

	private Integer id;
	private Integer usuario_id;
	private List<Atraccion> lAtracciones; 
	private List<Promocion> lPromociones;
	

	public Itinerario(Integer id, Integer usuario_id, List<Atraccion> lAtracciones, List<Promocion> lPromociones) {
		super();
		this.id = id;
		this.usuario_id = usuario_id;
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


	public Integer getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
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
		return "Itinerario [id=" + id + ", usuario_id=" + usuario_id + ", lAtracciones=" + lAtracciones
				+ ", lPromociones=" + lPromociones + "]";
	}


	
	
	
}
