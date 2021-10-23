package TurismoTierraMedia;


public class Atraccion {

	private String nombre;
	private Double costo;
	private Double tiempo;
	private Integer cupo;
	private Integer tipo_atraccion_id;

	public Atraccion(String nombre, Double costo, Double tiempo, Integer cupo, Integer tipo_atraccion_id) {
		super();
		this.nombre = nombre;
		this.costo = costo;
		this.tiempo = tiempo;
		this.cupo = cupo;
		this.tipo_atraccion_id = tipo_atraccion_id;
	} 

	public String getNombre() {
		return nombre;
	}


	public Integer getTipoDeAtraccion() {
		return tipo_atraccion_id;
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

	
	
}
