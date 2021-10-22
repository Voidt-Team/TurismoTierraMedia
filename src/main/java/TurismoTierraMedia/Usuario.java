package TurismoTierraMedia;


public class Usuario {
	
	// Ordenamos los atributos segun el orden del txt.-
	private Integer id;
	private String nombre;
	private double presupuesto;
	private double tiempo;
	private Integer idTipoAtraccion;
	private Integer idItinerario;
	

	//este constructor no usa el campo id porque es un atributo que se autogenera en la base de datos...
	public Usuario(String nombre, double presupuesto, double tiempo, Integer idTipoAtraccion) {
		super();
		this.nombre = nombre;
		this.presupuesto = presupuesto;
		this.tiempo = tiempo;
		this.idTipoAtraccion = idTipoAtraccion;
	}
	
	//getter y setter de los atributos
	protected Integer getId() {
		return id;
	}

	protected String getNombre() {
		return nombre;
	}

	protected void setNombre(String nombre) {
		this.nombre = nombre;
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

	protected Integer getIdTipoAtraccion() {
		return idTipoAtraccion;
	}

	protected void setIdTipoAtraccion(Integer idTipoAtraccion) {
		this.idTipoAtraccion = idTipoAtraccion;
	}

	protected Integer getIdItinerario() {
		return idItinerario;
	}

	protected void setIdItinerario(Integer idItinerario) {
		this.idItinerario = idItinerario;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", presupuesto=" + presupuesto + ", tiempo=" + tiempo
				+ ", idTipoAtraccion=" + idTipoAtraccion + ", idItinerario=" + idItinerario + "]";
	}

	
	
	
}
