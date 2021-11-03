package TurismoTierraMedia;

import java.util.List;


public class Itinerario {

	private Integer id;
	private Integer usuario_id;
	private List<Atraccion> lAtracciones; 
	private List<Promocion> lPromociones;
	private Double tiempo;
	private Double costo;
	

	public Itinerario(Integer id, Integer usuario_id, List<Atraccion> lAtracciones, List<Promocion> lPromociones, Double tiempo, Double costo) {
		super();
		this.id = id;
		this.usuario_id = usuario_id;
		this.lAtracciones = lAtracciones;
		this.lPromociones = lPromociones;
		this.tiempo = tiempo;
		this.costo = costo;
	}

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	protected void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}

	public Integer getUsuario_id() {
		return usuario_id;
	}

	public List<Atraccion> getlAtracciones() {
		return lAtracciones;
	}

	public List<Promocion> getlPromociones() {
		return lPromociones;
	}


	public Double getTiempo() {
		return tiempo;
	}

	public void setTiempo(Double tiempo) {
		this.tiempo = tiempo;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}
	
	@Override
	public String toString() {
		return "Itinerario [id=" + id + ", usuario_id=" + usuario_id + ", lAtracciones=" + lAtracciones
				+ ", lPromociones=" + lPromociones + "]";
	}
}
