package TurismoTierraMedia;

public class Usuario {

	// Ordenamos los atributos segun el orden del txt.-
	private Integer id;
	private String nombre;
	private Double presupuesto;
	private Double tiempo;
	private Integer idItinerario;
	private String atraccion_preferida;


	public Usuario(Integer id, String nombre, Double presupuesto, Double tiempo, String atraccion_preferida) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.presupuesto = presupuesto;
		this.tiempo = tiempo;
		this.atraccion_preferida = atraccion_preferida;
		this.idItinerario=null;
	}

	// getter y setter de los atributos
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Double getTiempo() {
		return tiempo;
	}

	public void setTiempo(Double tiempo) {
		this.tiempo = tiempo;
	}

	public Integer getIdItinerario() {
		return idItinerario;
	}

	
	protected void setIdItinerario(Integer idItinerario) {
		this.idItinerario = idItinerario;
	}

	public String getAtraccion_preferida() {
		return atraccion_preferida;
	}

	public void setAtraccion_preferida(String atraccion_preferida) {
		this.atraccion_preferida = atraccion_preferida;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", presupuesto=" + presupuesto + ", tiempo=" + tiempo
				+ ", idItinerario=" + idItinerario + ", atraccion_preferida=" + atraccion_preferida + "]";
	}

	
	
}
