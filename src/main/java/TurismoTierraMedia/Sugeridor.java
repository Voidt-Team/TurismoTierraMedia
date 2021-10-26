package TurismoTierraMedia;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import TurismoTierraMedia.dao.AtraccionDAO;


public class Sugeridor {

	public static void sugerir(Usuario usuario, List<Usuario> lusuarios) throws SQLException {
		AtraccionDAO atracciones = new AtraccionDAO();
		List<Atraccion> atraccionesPreferidas = atracciones.findbyidUser(usuario.getId());
		sugerirAtracciones(atraccionesPreferidas, usuario, true);
		
		

		
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
