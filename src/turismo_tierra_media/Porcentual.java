package turismo_tierra_media;

import java.util.List;

public class Porcentual extends Promocion {
	
	private double descuento;

	public Porcentual(String nombrePromocion, List<Atraccion> atraccionesIncluidas, double descuento) {
		super(nombrePromocion, atraccionesIncluidas);
		this.descuento = descuento;
	}

	public double getDescuento() { 
		return descuento;
	}
	
	@Override
	public double costoPromocion() {
		double costo = 0;

	    for(Atraccion atraccion : this.atracciones) {
	    	costo += atraccion.getCosto();
	    }
	    costo = costo - (costo * this.getDescuento());
	    return costo;
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
	
	//Metodo que retorna el porcentaje de descuento
	@Override
	public String ImprimirBonus() {
		Double descuento = this.getDescuento() * 100;
		String descuentoS = descuento.toString();
		descuentoS = "obtiene un descuento del " + descuentoS + " %";
		return descuentoS;
	}

	@Override
	public int tipoPromocion() {
		return 2;
	}
	
	

}

