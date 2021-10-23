package TurismoTierraMedia;

public class Usuario {

	// Ordenamos los atributos segun el orden del txt.-
	private Integer id;
	private String nombre;
	private Double presupuesto;
	private Double tiempo;
	private Integer idTipoAtraccion;
	private Integer idItinerario;
	private Double latitud;
	private Double longitud;
	private String atraccion_preferida;

	// este constructor no usa el campo id porque es un atributo que se autogenera
	// en la base de datos...
	public Usuario(Integer id, String nombre, Double presupuesto, Double tiempo, Integer idTipoAtraccion,
			Double latitud, Double longitud, String atraccion_preferida) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.presupuesto = presupuesto;
		this.tiempo = tiempo;
		this.idTipoAtraccion = idTipoAtraccion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.atraccion_preferida = atraccion_preferida;
	}

	// getter y setter de los atributos
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

	public Double getLatitud() {
		return latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public String getAtraccion_preferida() {
		return atraccion_preferida;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", presupuesto=" + presupuesto + ", tiempo=" + tiempo
				+ ", idTipoAtraccion=" + idTipoAtraccion + ", idItinerario=" + idItinerario + "]";
	}
}
