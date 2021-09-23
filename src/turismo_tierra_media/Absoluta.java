package turismo_tierra_media;


import java.util.List;

public class Absoluta extends Promocion {
	
	private int costoFinal;
	
	public Absoluta(String nombrePromocion, List<Atraccion> atraccionesIncluidas, int costoFinal) {
		super(nombrePromocion, atraccionesIncluidas);
		this.costoFinal = costoFinal;
	}

	public int getCostoFinal() {
		return costoFinal; 
	}
	

	@Override
	public double costoPromocion() {
		double costo = this.getCostoFinal();
	    return costo;
	}
	
	public double descuentoMonedas() {
		
		double costoSinDescuento = 0;
		double descuentoMonedas = 0;

	    for(Atraccion atraccion : this.atracciones) {
	    	costoSinDescuento += atraccion.getCosto();
	    }
		
		descuentoMonedas = costoSinDescuento - this.costoPromocion();
		return descuentoMonedas;
		
	}
	
	//Metodo que retorna una sumatoria del tiempo que te lleva hacer las excursiones de esta promocion
	@Override
	public double tiempoPromocion() {
		double horas = 0;

	    for(Atraccion atraccion : this.atracciones) {
	    	horas += atraccion.getTiempo();
	    }
	    return horas; 
	}
	
	
	//Metodo que retorna el descuento de la promo
	@Override
	public String ImprimirBonus() {
		Double descuento = this.descuentoMonedas();
		String descuentoS = descuento.toString();
		descuentoS = "¡se ahorra " + descuentoS + " monedas!";
		return descuentoS;
	}

	@Override
	public int tipoPromocion() {
		return 1;
	}

}
