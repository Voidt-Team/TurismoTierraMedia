package TurismoTierraMedia;
import java.util.List;

public class Promocion {
	
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

	public String getNombre() { 
		return nombre;
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
