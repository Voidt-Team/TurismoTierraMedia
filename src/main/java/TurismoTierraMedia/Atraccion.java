package TurismoTierraMedia;


public class Atraccion {

	private Integer id;
	private String nombre;
	private Double costo;
	private Double tiempo;
	private Integer cupo;
	private String tipo_atraccion;
	private Double latitud;
	private Double longitud;

	public Atraccion(Integer id,String nombre, Double costo, Double tiempo, Integer cupo, String tipo_atraccion, Double latitud, Double longitud) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.costo = costo;
		this.tiempo = tiempo;
		this.cupo = cupo;
		this.tipo_atraccion = tipo_atraccion;
		this.latitud = latitud;
		this.longitud = longitud;
	} 

	public String getNombre() {
		return nombre;
	}


	public String getTipoDeAtraccion() {
		return tipo_atraccion;
	}


	public Double getCosto() {
		return costo;
	}

	public double getTiempo() {
		return tiempo;
	}


	public int getCupo() {
		return cupo;
	}

	public void setCupo(int cupo) {
		this.cupo = cupo;
	}

	protected Integer getId() {
		return id;
	}
	
	public Double getLatitud() {
		return latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	@Override
	public String toString() {
		return "Atraccion [id=" + id + ", nombre=" + nombre + ", costo=" + costo + ", tiempo=" + tiempo + ", cupo="
				+ cupo + ", tipo_atraccion=" + tipo_atraccion + ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}

	
}
