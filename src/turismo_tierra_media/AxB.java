package turismo_tierra_media;

import java.util.List;

public class AxB extends Promocion {

	private Atraccion destinoExtra; 
	
	public AxB(String nombrePromocion, List<Atraccion> atraccionesIncluidas, Atraccion extra) {
		super(nombrePromocion, atraccionesIncluidas);
		//llegamos al acuerdo de separar el destino extra de la lista a los fines practicos de utilizacion.
		this.destinoExtra = extra;
	}

	public Atraccion getDestinoExtra() { 
		return destinoExtra;
	}
	

	@Override
	public double costoPromocion() {
		double costo = 0;
		
		for(Atraccion atraccion : this.atracciones) {
		   costo += atraccion.getCosto();
		}
	
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
	
	//Metodo que retorna la atraccion gratuita 
	@Override
	public String ImprimirBonus() {
		String destinoGratis = destinoExtra.getNombre();
		//String gratis;
		//gratis = "ademas: " + destinoGratis + " gratis!";
		return destinoGratis;
	}

	@Override
	public int tipoPromocion() {
		return 3;
	}
	
}
