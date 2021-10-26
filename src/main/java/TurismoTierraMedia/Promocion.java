package TurismoTierraMedia;
import java.util.List;

public class Promocion {
	
	private Integer id;
	protected String nombre;
	protected Integer absoluta; 
	protected Atraccion axb;
	protected Double porcentual;
	protected Integer axb_id;
	private List<Atraccion> lista_atracciones;
	
	public Promocion(Integer id, String nombre, Integer absoluta, Atraccion axb, Double porcentual,
			Integer axb_id, List<Atraccion> lista_atracciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.absoluta = absoluta;
		this.axb = axb;
		this.porcentual = porcentual;
		this.axb_id = axb_id;
		this.lista_atracciones = lista_atracciones;
	}

	protected Integer getId() {
		return id;
	}

	public String getNombre() { 
		return nombre;
	}
	
	//devuelve el precio final de la promo...
	public Integer getAbsoluta() {
		return absoluta;
	}

	//Atraccion gratis
	public Atraccion getAxb() {
		return axb;
	}

	//porcentaje de descuento a aplicar ...
	public Double getPorcentual() {
		return porcentual;
	}

	public Integer getAxb_id() {
		return axb_id;
	}

	public List<Atraccion> getLista_atracciones() {
		return lista_atracciones;
	}

	//Imprime el Bonus de la promo
	public String ImprimirBonus() {
		String descuentoS = "";
		if(getAbsoluta()!= 0) {
			double costo = 0;
			for(Atraccion atraccion : this.lista_atracciones) {
		    	costo += atraccion.getCosto();
		    }
			Double descuento = costo - getAbsoluta();
			descuentoS = descuento.toString();
			descuentoS = "¡se ahorra " + descuentoS + " monedas!";
		}else if(getPorcentual() != 0) {
			Double descuento = this.getPorcentual() * 100;
			descuentoS = descuento.toString();
			descuentoS = "obtiene un descuento del " + descuentoS + " %";
		}else if(getAxb_id() !=0){
			descuentoS = "¡obtiene " + getAxb().getNombre() + " gratis!";
		}
		return descuentoS;
	}
	
	//Retorna el costo de la promocion
	public double costoPromocion() {
		double costo = 0;
		if(getAbsoluta()!= 0) {
			costo  = getAbsoluta();
		}else if(getPorcentual()!= 0) {
			    for(Atraccion atraccion : this.lista_atracciones) {
			    	costo += atraccion.getCosto();
			    }
			    costo = costo - (costo * this.getPorcentual());
		}else if(getAxb_id() !=0){
			for(Atraccion atraccion : this.lista_atracciones) {
			   costo += atraccion.getCosto();
			}
		}
		 return costo;
	}

	//Retorna el tiempo que dura la promo
	public double tiempoPromocion() {
		double horas = 0;
		if(getAbsoluta() != 0 || getPorcentual() != 0) {
		    for(Atraccion atraccion : this.lista_atracciones) {
		    	horas += atraccion.getTiempo();
		    }
		   
		}else if(getAxb_id() !=0){
			for(Atraccion atraccion : this.lista_atracciones) {
				horas += atraccion.getTiempo();
			}
			horas += getAxb().getTiempo();
		}
		 return horas; 
	}
}
