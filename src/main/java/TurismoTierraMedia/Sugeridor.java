package TurismoTierraMedia;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import TurismoTierraMedia.dao.AtraccionDAO;
import TurismoTierraMedia.dao.PromocionDAO;


public class Sugeridor {

	public static void sugerir(Usuario usuario, List<Usuario> listaUsuarios) throws SQLException {
		PromocionDAO promociones = new PromocionDAO();
		AtraccionDAO atracciones = new AtraccionDAO();
		List<Promocion> promosPreferidas = promociones.promocionesPreferidas(usuario.getId());
		List<Promocion> promosNoPreferidas = promociones.promocionesNoPreferidas(usuario.getId());
		List<Atraccion> atraccPreferidas = atracciones.atraccionesPreferidas(usuario.getId());
		List<Atraccion> atraccNoPreferidas = atracciones.atraccionesNoPreferidas(usuario.getId());
		
		int opcion = 1;
		// Dependiento de las listas filtradas es por donde comienza.
		while (opcion != 99999) {
			if (!promosPreferidas.isEmpty()) {
				sugerirPromocionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			} else if (!atraccPreferidas.isEmpty()) {
				sugerirAtraccionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			} else if (!promosNoPreferidas.isEmpty()) {
				sugerirPromocionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			} else if (!atraccNoPreferidas.isEmpty()) {
				sugerirAtraccionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			} else {
				noMasCompras(usuario, opcion, listaUsuarios);
			}
		}
		
	}

	//------------------------------Metodo que sugiere las promos preferidas--------------------------
		public static void sugerirPromocionesPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
				List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
				int opcion, List<Usuario> listaUsuarios) throws SQLException {
			
			PromocionDAO promociones = new PromocionDAO();
			AtraccionDAO atracciones = new AtraccionDAO();
		
			while(!promosPreferidas.isEmpty()) {
				sugerirPromos(promosPreferidas,usuario, true);
				Scanner sc = new Scanner(new InputStreamReader(System.in));
				String entrada = sc.next();
				if (entrada.toUpperCase().compareTo("C") == 0) {
					sugerirAtraccionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
							atraccNoPreferidas, opcion, listaUsuarios);
				}
				try {
					if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
						opcion = (int) Double.parseDouble(entrada);
						actualizarPromo(usuario, promosPreferidas.get(opcion - 1));
						
						//Se filtra nuevamente las listas para volver a sugerir al usuario las listas actualizadas
						promosPreferidas = promociones.promocionesPreferidas(usuario.getId());
						promosNoPreferidas = promociones.promocionesNoPreferidas(usuario.getId());
						atraccPreferidas = atracciones.atraccionesPreferidas(usuario.getId());
						atraccNoPreferidas = atracciones.atraccionesNoPreferidas(usuario.getId());
						
						sugerirPromocionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
								atraccNoPreferidas, opcion, listaUsuarios);
					} else {
						opcion = promosPreferidas.size() + 1;
					}
				} catch (IndexOutOfBoundsException ex) {
					System.out.println("El valor ingresado solamente puede ser un entero entre 1 y " + promosPreferidas.size());
				}
			}
			sugerir(usuario, listaUsuarios);
		}
		
		//------------------------------Metodo que sugiere las atracciones preferidas--------------------------
		public static void sugerirAtraccionesPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
				List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
				int opcion, List<Usuario> listaUsuarios) throws SQLException {
			
			PromocionDAO promociones = new PromocionDAO();
			AtraccionDAO atracciones = new AtraccionDAO();
			
			while(!atraccPreferidas.isEmpty()) {
				sugerirAtracciones(atraccPreferidas, usuario, true);
				Scanner sc = new Scanner(new InputStreamReader(System.in));
				String entrada = sc.next();
				if (entrada.toUpperCase().compareTo("C") == 0) {
					sugerirPromocionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
							atraccNoPreferidas, opcion, listaUsuarios);
				}
				try {
					if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
						opcion = (int) Double.parseDouble(entrada);
						actualizarAtraccion(usuario, atraccPreferidas.get(opcion-1));
						
						promosPreferidas = promociones.promocionesPreferidas(usuario.getId());
						promosNoPreferidas = promociones.promocionesNoPreferidas(usuario.getId());
						atraccPreferidas = atracciones.atraccionesPreferidas(usuario.getId());
						atraccNoPreferidas = atracciones.atraccionesNoPreferidas(usuario.getId());
						
						sugerirAtraccionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
								atraccNoPreferidas, opcion, listaUsuarios);
					} else {
						opcion = atraccPreferidas.size() + 1;
					}
				} catch (IndexOutOfBoundsException ex) {
					System.out.println("El valor ingresado solamente puede ser un entero entre 1 y " + atraccPreferidas.size());
				}				
			}
			sugerir(usuario, listaUsuarios);
		}

		//------------------------------Metodo que sugiere las promos NO preferidas--------------------------
		public static void sugerirPromocionesNoPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
				List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
				int opcion, List<Usuario> listaUsuarios) throws SQLException {
			
			PromocionDAO promociones = new PromocionDAO();
			AtraccionDAO atracciones = new AtraccionDAO();
			
			while(!promosNoPreferidas.isEmpty()) {
				sugerirPromos(promosNoPreferidas,usuario, false);
				Scanner sc = new Scanner(new InputStreamReader(System.in));
				String entrada = sc.next();
				if (entrada.toUpperCase().compareTo("C") == 0) {
					sugerirAtraccionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
							atraccNoPreferidas, opcion, listaUsuarios);
				}
				try {
					if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
						opcion = (int) Double.parseDouble(entrada);
						actualizarPromo(usuario, promosNoPreferidas.get(opcion - 1));
						
						promosPreferidas = promociones.promocionesPreferidas(usuario.getId());
						promosNoPreferidas = promociones.promocionesNoPreferidas(usuario.getId());
						atraccPreferidas = atracciones.atraccionesPreferidas(usuario.getId());
						atraccNoPreferidas = atracciones.atraccionesNoPreferidas(usuario.getId());
						
						sugerirPromocionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
								atraccNoPreferidas, opcion, listaUsuarios);
					} else {
						opcion = promosNoPreferidas.size() + 1;
					}
				} catch (IndexOutOfBoundsException ex) {
					System.out.println("El valor ingresado solamente puede ser un entero entre 1 y " + promosNoPreferidas.size());
				}
			}
			sugerir(usuario, listaUsuarios);
		}


		//------------------------------Metodo que sugiere las atracciones NO preferidas--------------------------
		public static void sugerirAtraccionesNoPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
				List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
				int opcion, List<Usuario> listaUsuarios) throws SQLException {
			
			PromocionDAO promociones = new PromocionDAO();
			AtraccionDAO atracciones = new AtraccionDAO();
			
			while(!atraccNoPreferidas.isEmpty()) {
				sugerirAtracciones(atraccNoPreferidas, usuario, false);
				Scanner sc = new Scanner(new InputStreamReader(System.in));
				String entrada = sc.next();
				if (entrada.toUpperCase().compareTo("C") == 0) {
					sugerirPromocionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
							atraccNoPreferidas, opcion, listaUsuarios);
				}
				try {
					if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
						opcion = (int) Double.parseDouble(entrada);
						actualizarAtraccion(usuario, atraccNoPreferidas.get(opcion-1));
						
						promosPreferidas = promociones.promocionesPreferidas(usuario.getId());
						promosNoPreferidas = promociones.promocionesNoPreferidas(usuario.getId());
						atraccPreferidas = atracciones.atraccionesPreferidas(usuario.getId());
						atraccNoPreferidas = atracciones.atraccionesNoPreferidas(usuario.getId());
						
						sugerirAtraccionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
								atraccNoPreferidas, opcion, listaUsuarios);
					} else {
						opcion = atraccNoPreferidas.size() + 1;
					}
				} catch (IndexOutOfBoundsException ex) {
					System.out
							.println("El valor ingresado solamente puede ser un entero entre 1 y " + atraccNoPreferidas.size());
				}
			}
			sugerir(usuario, listaUsuarios);	
		}


	
	
	//------------------------------Metodo para cuando ya no se puede comprar mas--------------------------
		public static void noMasCompras(Usuario usuario, int opcion, List<Usuario> listaUsuarios) throws SQLException {
			System.out.println("No puedes realizar compras!");
			//ItinerarioViejo.generarArchivoUsuario(usuario);
			opcion = 99999;
			//ItinerarioViejo.mostrarItinerario(usuario);
			App.consola();
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
		
		//----------------------Metodos de actualizacion----------------------------------
		//Llama a los metodos de actualizacion de: presupuesto y tiempo del usuario que compro una promo
		//actualizacion de historial de promociones y actualizacion de cupo de las atracciones de una promo
		public static void actualizarPromo(Usuario usuario, Promocion promocion) {
			System.out.println("\nHas elegido la promocion: " + promocion.getNombre());
			//actualizarPresupuestoTiempoPromocion(usuario, promocion);
			//actualizarHistorialPromociones(usuario, promocion);
			//actualizarCupoAtraccionPromo(promocion);
		}
		
		//Llama a los metodos de actualizacion de: presupuesto y tiempo del usuario que compro una atraccion
		//actualizacion de historial de atracciones y actualizacion de cupo de la atraccion
		public static void actualizarAtraccion(Usuario usuario, Atraccion atraccion) {
			System.out.println("\nHas elegido la atraccion: " + atraccion.getNombre());
			//actualizarPresupuestoTiempoAtraccion(usuario, atraccion);
			//actualizarHistorialAtracciones(usuario, atraccion);
			//actualizarCupoAtraccion(atraccion);
		}
		
		//------------------------------Metodos de Actualizacion de listas de usuario--------------------------
		// Actualizar historial de Atracciones //VER con sql
//		public static void actualizarHistorialAtracciones(Usuario usuario, Atraccion atraccion) {
//			usuario.getHistorialAtracciones().add(atraccion);
//			usuario.getTodasLasAtracciones().add(atraccion);
//		}

		// Actualizar historial de Promociones //VER con sql
//		public static void actualizarHistorialPromociones(Usuario usuario, Promocion promo) {
//			usuario.getHistorialPromociones().add(promo);
//			for(Atraccion ap: promo.getAtracciones()) {
//				usuario.getTodasLasAtracciones().add(ap);
//			}
//			if(promo.tipoPromocion()== 3) {
//				String gratis = promo.ImprimirBonus();
//				for (Atraccion atraccL : listaAtracciones) {
//					if (gratis.compareTo(atraccL.getNombre()) == 0) {
//						usuario.getTodasLasAtracciones().add(atraccL);
//					}
//				}
//			}
//		}

		// Actualizar presupuesto y tiempo del usuario cuando elige promocion //VER con sql
//		public static void actualizarPresupuestoTiempoPromocion(Usuario usuario, Promocion promo) {
//			usuario.setPresupuesto((int) (usuario.getPresupuesto() - promo.costoPromocion()));
//			usuario.setTiempoDisponible(usuario.getTiempoDisponible() - promo.tiempoPromocion());
//		}

		// Actualizar presupuesto y tiempo del usuario cuando elige atraccion //VER con sql
//		public static void actualizarPresupuestoTiempoAtraccion(Usuario usuario, Atraccion atraccion) {
//			usuario.setPresupuesto((int) (usuario.getPresupuesto() - atraccion.getCosto()));
//			usuario.setTiempoDisponible(usuario.getTiempoDisponible() - atraccion.getTiempo());
//		}

		//------------------------------Metodos de Actualizacion de cupos--------------------------
		// De atracciones de una promocion//VER con sql
//		public static void actualizarCupoAtraccionPromo(Promocion promo) {
//			List<Atraccion> atraccionesPromo = new ArrayList<Atraccion>();
//			for (Atraccion a : listaAtracciones) {
//				atraccionesPromo = promo.getAtracciones();
//				for (Atraccion atracc : atraccionesPromo) {
//					if ((a.getNombre()).compareTo(atracc.getNombre()) == 0) {
//						a.setCupo(a.getCupo() - 1);
//					}
//				}
//			}
			// Esta parte es para actualizar el cupo de la atraccion gratis si la promo es
			// AxB////VER con sql
//			if (promo.tipoPromocion() == 3) {
//				String gratis = promo.ImprimirBonus();
//				for (Atraccion atraccion : listaAtracciones) {
//					if (gratis.compareTo(atraccion.getNombre()) == 0) {
//						atraccion.setCupo(atraccion.getCupo() - 1);
//					}
//				}
//			}

//		}

		// De atraccion//VER con sql
//		public static void actualizarCupoAtraccion(Atraccion atraccion) {
//			atraccion.setCupo(atraccion.getCupo() - 1);
//		}

}
