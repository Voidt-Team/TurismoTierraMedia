package TurismoTierraMedia;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import TurismoTierraMedia.dao.AtraccionDAO;
import TurismoTierraMedia.dao.PromocionDAO;


public class Sugeridor {

	public static void sugerir(Usuario usuario, List<Usuario> lusuarios) throws SQLException {
		PromocionDAO promociones = new PromocionDAO();
		AtraccionDAO atracciones = new AtraccionDAO();
		List<Promocion> promocionesPreferidas = promociones.promocionesPreferidas(usuario.getId());
		List<Promocion> promocionesNoPreferidas = promociones.promocionesNoPreferidas(usuario.getId());
		List<Atraccion> atraccionesPreferidas = atracciones.atraccionesPreferidas(usuario.getId());
		List<Atraccion> atraccionesNoPreferidas = atracciones.atraccionesNoPreferidas(usuario.getId());
		
		sugerirPromos(promocionesPreferidas, usuario, true);
		sugerirPromos(promocionesNoPreferidas, usuario, false);
		sugerirAtracciones(atraccionesPreferidas, usuario, true);
		sugerirAtracciones(atraccionesNoPreferidas, usuario, false);
		
	}

	//Muestra las promociones a sugerir al usuario dependiendo de sus gustos
		public static void sugerirPromos(List<Promocion> promociones, Usuario usuario, boolean prefONo) {
			if(prefONo) {
				System.out.println("\nPromociones recomendadas para usted:");
			}else {
				System.out.println("\nOtras Promociones recomendadas para usted:");
			}
			mostrarPromociones(promociones);
			System.out.println("_________________________________________________________________________________________________________________");
			System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y " + usuario.getTiempo()+ " hs disponibles.");
			System.out.println("_________________________________________________________________________________________________________________\n");
			System.out.println("\nSi desea comprar una promocion del listado ingrese su numero\nSino presione la tecla C");
		}
	
	//Muestra las atracciones a sugerir al usuario dependiendo de sus gustos
		public static void sugerirAtracciones(List<Atraccion> atracciones, Usuario usuario, boolean prefONo) {
			if(prefONo) {
				System.out.println("\nAtracciones recomendadas para usted:");
			}else {
				System.out.println("\nOtras Atracciones recomendadas para usted:");
			}
			//ordenarAtraccionesCostoHoras(atracciones);
			mostrarAtracciones(atracciones);
			System.out.println("_________________________________________________________________________________________________________________");
			System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y " + usuario.getTiempo()+ " hs disponibles.");
			System.out.println("_________________________________________________________________________________________________________________\n");
			System.out.println("\nSi desea comprar una atraccion del listado ingrese su numero\nSino presione la tecla C");
		}
		
		
		// ------------------------------Metodos que muestran listados por pantalla
		// Mostrar Promociones
		public static void mostrarPromociones(List<Promocion> promocionesMostrar) {
			List<Promocion> promociones = promocionesMostrar;
			int cant = 0;
			for (Promocion promocion : promociones) {
				cant++;
				System.out.print(cant + " - " + promocion.getNombre() + " - Destinos: ");
				for (Atraccion atraccion : promocion.getLista_atracciones()) {
					System.out.print(atraccion.getNombre() + ", ");
				}
				System.out.println(promocion.ImprimirBonus() + ". Precio de la promo: " + promocion.costoPromocion()
						+ " monedas, duracion: " + promocion.tiempoPromocion() + " hs.");
			}
		}

		// Mostrar Atracciones
		public static void mostrarAtracciones(List<Atraccion> atraccionesMostrar) {
		List<Atraccion> atracciones = atraccionesMostrar;
			int cant = 0;
			for (Atraccion atraccion : atracciones) {
				cant++;
				System.out.println(cant + " - " + atraccion.getNombre() + ": su costo es de " + atraccion.getCosto()
						+ " monedas, debe disponer de " + atraccion.getTiempo() + " hs. y su cupo es de "
						+ atraccion.getCupo() + " personas.");
			}
			
		}
}
