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
	}

	// getter y setter de los atributos
	protected Integer getId() {
		return id;
	}

	protected String getNombre() {
		return nombre;
	}


	protected double getPresupuesto() {
		return presupuesto;
	}

	protected void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}

	protected double getTiempo() {
		return tiempo;
	}

	protected void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}

	protected Integer getIdItinerario() {
		return idItinerario;
	}

	protected void setIdItinerario(Integer idItinerario) {
		this.idItinerario = idItinerario;
	}

	public String getAtraccion_preferida() {
		return atraccion_preferida;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", presupuesto=" + presupuesto + ", tiempo=" + tiempo
				+ ", idItinerario=" + idItinerario + ", atraccion_preferida=" + atraccion_preferida + "]";
	}

	
}
