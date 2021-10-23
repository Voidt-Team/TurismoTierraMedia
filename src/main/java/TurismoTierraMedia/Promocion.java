package TurismoTierraMedia;
import java.util.List;

public class Promocion {
	
	private Integer id;
	protected String nombre;
	protected Integer absoluta; 
	protected Integer axb;
	protected Double porcentual;
	
	public Promocion(String nombre, Integer absoluta, Integer axb, Double porcentual) {
		super();
		this.nombre = nombre;
		this.absoluta = absoluta;
		this.axb = axb;
		this.porcentual = porcentual;
	}

	
	
	protected Integer getId() {
		return id;
	}


	public String getNombre() { 
		return nombre;
	}
	
	
	//devuelve el precio final de la promo...
	protected Integer getAbsoluta() {
		return absoluta;
	}

	//devuelve el id de la atraccion extra...
	protected Integer getAxb() {
		return axb;
	}


	//porcentaje de descuento a aplicar ...
	protected Double getPorcentual() {
		return porcentual;
	}


	//Metodos implementados en las subclases
	public String ImprimirBonus() {

	}
	
	public double costoPromocion() {
		return costo;
	}

	public double tiempoPromocion() {
		return horas; 
	}
	
	public int tipoPromocion() {
		return 1; 
	}
	
	
}
