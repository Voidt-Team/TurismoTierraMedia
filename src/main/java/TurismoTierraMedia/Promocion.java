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
		if(getAbsoluta()>0) {
			Double descuento = this.descuentoMonedas();
			String descuentoS = descuento.toString();
			descuentoS = "¡se ahorra " + descuentoS + " monedas!";
			return descuentoS;
			//ver como hacer para sacar el precio de la atraccion gratis
		}

		if(getAxb()>0) {
			Integer destinoGratis = getAxb();
			return destinoGratis;
			//Hay que ver como conseguir el nombre del destino gratis a partir del id
		}
		
		if(getPorcentual()>0) {
			Double descuento = this.getPorcentual() * 100;
			String descuentoS = descuento.toString();
			descuentoS = "obtiene un descuento del " + descuentoS + " %";
			return descuentoS;
		}
	}
	
	public double costoPromocion() {
		if(getAbsoluta()>0) {
			return getAbsoluta();
		}
		if(getAxb()>0) {
			double costo = 0;
			for(Atraccion atraccion : this.lista_atracciones) {
			   costo += atraccion.getCosto();
			}
			return costo;
		}
		if(getPorcentual()>0) {
			double costo = 0;
		    for(Atraccion atraccion : this.lista_atracciones) {
		    	costo += atraccion.getCosto();
		    }
		    costo = costo - (costo * this.getPorcentual());
		    return costo;
		}
	}

	public double tiempoPromocion() {
		double horas = 0;
		if(getAbsoluta()>0 || getPorcentual()>0) {n
		    for(Atraccion atraccion : this.lista_atracciones) {
		    	horas += atraccion.getTiempo();
		    }
		    return horas; 
		}
		if(getAxb()>0) {
			for(Atraccion atraccion : this.lista_atracciones) {
				horas += atraccion.getTiempo();
			}
			    return horas;
			//Aca faltaría sumar el tiempo del destino extra también
		}
	}

}
