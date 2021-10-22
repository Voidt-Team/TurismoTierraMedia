package TurismoTierraMedia;

public class tipoAtraccion {

	private Integer id;
	private String nombre;
	
	
	public tipoAtraccion(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}


	protected Integer getId() {
		return id;
	}


	protected void setId(Integer id) {
		this.id = id;
	}


	protected String getNombre() {
		return nombre;
	}


	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public String toString() {
		return "tipoAtraccion [id=" + id + ", nombre=" + nombre + "]";
	}
	
	

}
