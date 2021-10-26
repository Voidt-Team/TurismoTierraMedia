package TurismoTierraMedia;
import java.util.List;

public class Promocion {
	
	private Integer id;
	protected String nombre;
	protected Integer absoluta; 
	protected Atraccion axb;
	protected Double porcentual;
	private List<Atraccion> lista_atracciones;

	
	
	public Promocion(Integer id, String nombre, Integer absoluta, Atraccion axb, Double porcentual,
			List<Atraccion> lista_atracciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.absoluta = absoluta;
		this.axb = axb;
		this.porcentual = porcentual;
		this.lista_atracciones = lista_atracciones;
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

	//Atraccion gratis
	protected Atraccion getAxb() {
		return axb;
	}

	//porcentaje de descuento a aplicar ...
	protected Double getPorcentual() {
		return porcentual;
	}

	
	protected List<Atraccion> getLista_atracciones() {
		return lista_atracciones;
	}


	
	//Imprime el Bonus de la promo
	public String ImprimirBonus() {
		String descuentoS = "";
		if(getAbsoluta()!= null) {
			double costo = 0;
			for(Atraccion atraccion : this.lista_atracciones) {
		    	costo += atraccion.getCosto();
		    }
			Double descuento = costo - getAbsoluta();
			descuentoS = descuento.toString();
			descuentoS = "¡se ahorra " + descuentoS + " monedas!";
		}

		if(getAxb() != null) {
			String destinoGratis = getAxb().getNombre();
			descuentoS = "¡obtiene " + destinoGratis + " gratis!";
		}
		
		if(getPorcentual() != null) {
			Double descuento = this.getPorcentual() * 100;
			descuentoS = descuento.toString();
			descuentoS = "obtiene un descuento del " + descuentoS + " %";
		}

		return descuentoS;
	}
	
	//Retorna el costo de la promocion
	public double costoPromocion() {
		double costo = 0;
		if(getAbsoluta()>0) {
			costo  = getAbsoluta();
		}
		if(getAxb()!= null) {
			for(Atraccion atraccion : this.lista_atracciones) {
			   costo += atraccion.getCosto();
			}
		}
		if(getPorcentual()>0) {
		    for(Atraccion atraccion : this.lista_atracciones) {
		    	costo += atraccion.getCosto();
		    }
		    costo = costo - (costo * this.getPorcentual());
		}
		 return costo;
	}

	//Retorna el tiempo que dura la promo
	public double tiempoPromocion() {
		double horas = 0;
		if(getAbsoluta() > 0 || getPorcentual() > 0) {
		    for(Atraccion atraccion : this.lista_atracciones) {
		    	horas += atraccion.getTiempo();
		    }
		   
		}
		if(getAxb()!= null) {
			for(Atraccion atraccion : this.lista_atracciones) {
				horas += atraccion.getTiempo();
			}
			horas += getAxb().getTiempo();
		}
		 return horas; 
	}

}
