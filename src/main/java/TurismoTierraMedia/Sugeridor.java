package TurismoTierraMedia;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import TurismoTierraMedia.dao.AtraccionDAO;
import TurismoTierraMedia.dao.ItinerarioDAO;
import TurismoTierraMedia.dao.PromocionDAO;
import TurismoTierraMedia.dao.UsuarioDAO;


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
				int cont = 0;
				for (Promocion promocion : promosPreferidas) {
					if(promocion.costoPromocion() <= usuario.getPresupuesto() && promocion.tiempoPromocion() <= usuario.getTiempo()) {
						cont++;
					}
				}	
				if(cont > 0) {
					sugerirPromocionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
				}else {
					sugerirAtraccionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
							atraccNoPreferidas, opcion, listaUsuarios);
				}
			} else if (!atraccPreferidas.isEmpty()) {
				sugerirAtraccionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			} else if (!promosNoPreferidas.isEmpty()) {
				int cont = 0;
				for (Promocion promocion : promosNoPreferidas) {
					if(promocion.costoPromocion() <= usuario.getPresupuesto() && promocion.tiempoPromocion() <= usuario.getTiempo()) {
						cont++;
					}
				}	
				if(cont > 0) {
					sugerirPromocionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
							atraccNoPreferidas, opcion, listaUsuarios);
				}else {
					sugerirAtraccionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
							atraccNoPreferidas, opcion, listaUsuarios);
				}

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
			ItinerarioDAO itinerario = new ItinerarioDAO();

		
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
						
						//ACA ESTA LO DEL ITINERARIO
						Integer itinerario_id = usuario.getIdItinerario();
						System.out.println("ITTTTT:" + itinerario_id);
						
						if(itinerario_id == null) {
							//Insert de itinerario
							itinerario.insertItinerario(usuario.getId());
							itinerario_id = itinerario.findById(usuario.getId()).getId();
							usuario.setIdItinerario(itinerario_id);
							System.out.println("ITTTTT2:" + itinerario_id);
						}
						
						//Insertar atraccion de un itinerario
						itinerario.insertItinerario(promosPreferidas.get(opcion - 1), usuario);
						Itinerario itinerarioOBJ = itinerario.findById(usuario.getId());
						itinerarioOBJ.setId(itinerario_id);
						itinerarioOBJ.setUsuario_id(usuario.getId());
						itinerarioOBJ.getlPromociones().add(promosPreferidas.get(opcion - 1));
						
						actualizarPromo(usuario, promosPreferidas.get(opcion - 1),itinerario_id);
						

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
			ItinerarioDAO itinerario = new ItinerarioDAO();
			
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
						
						//ACA ESTA LO DEL ITINERARIO
						Integer itinerario_id = usuario.getIdItinerario();
						System.out.println("ITTTTT:" + itinerario_id);
						
						if(itinerario_id == null) {
							//Insert de itinerario
							itinerario.insertItinerario(usuario.getId());
							itinerario_id = itinerario.findById(usuario.getId()).getId();
							usuario.setIdItinerario(itinerario_id);
							System.out.println("ITTTTT2:" + itinerario_id);
						}
						
						//Insertar atraccion de un itinerario
						itinerario.insertItinerario(atraccPreferidas.get(opcion - 1), usuario);
						Itinerario itinerarioOBJ = itinerario.findById(usuario.getId());
						itinerarioOBJ.setId(itinerario_id);
						itinerarioOBJ.setUsuario_id(usuario.getId());
						itinerarioOBJ.getlAtracciones().add(atraccPreferidas.get(opcion - 1));
						
						actualizarAtraccion(usuario, atraccPreferidas.get(opcion - 1),itinerario_id);
					
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
			ItinerarioDAO itinerario = new ItinerarioDAO();
			
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

						//ACA ESTA LO DEL ITINERARIO
						Integer itinerario_id = usuario.getIdItinerario();
						System.out.println("ITTTTT:" + itinerario_id);
						
						if(itinerario_id == null) {
							//Insert de itinerario
							itinerario.insertItinerario(usuario.getId());
							itinerario_id = itinerario.findById(usuario.getId()).getId();
							usuario.setIdItinerario(itinerario_id);
							System.out.println("ITTTTT2:" + itinerario_id);
						}
						
						//Insertar atraccion de un itinerario
						itinerario.insertItinerario(promosNoPreferidas.get(opcion - 1), usuario);
						Itinerario itinerarioOBJ = itinerario.findById(usuario.getId());
						itinerarioOBJ.setId(itinerario_id);
						itinerarioOBJ.setUsuario_id(usuario.getId());
						itinerarioOBJ.getlPromociones().add(promosNoPreferidas.get(opcion - 1));
						
						actualizarPromo(usuario, promosNoPreferidas.get(opcion - 1),itinerario_id);

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
			ItinerarioDAO itinerario = new ItinerarioDAO();
			
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
						
						//ACA ESTA LO DEL ITINERARIO
						Integer itinerario_id = usuario.getIdItinerario();
						System.out.println("ITTTTT:" + itinerario_id);
						
						if(itinerario_id == null) {
							//Insert de itinerario
							itinerario.insertItinerario(usuario.getId());
							itinerario_id = itinerario.findById(usuario.getId()).getId();
							usuario.setIdItinerario(itinerario_id);
							System.out.println("ITTTTT2:" + itinerario_id);
						}
						
						//Insertar atraccion de un itinerario
						itinerario.insertItinerario(atraccNoPreferidas.get(opcion - 1), usuario);
						Itinerario itinerarioOBJ = itinerario.findById(usuario.getId());
						itinerarioOBJ.setId(itinerario_id);
						itinerarioOBJ.setUsuario_id(usuario.getId());
						itinerarioOBJ.getlAtracciones().add(atraccNoPreferidas.get(opcion - 1));
						
						actualizarAtraccion(usuario, atraccNoPreferidas.get(opcion - 1),itinerario_id);
						
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
		// ItinerarioViejo.generarArchivoUsuario(usuario);
		opcion = 99999;
		// ItinerarioViejo.mostrarItinerario(usuario);
		App.consola();
	}


	//Muestra las promociones a sugerir al usuario dependiendo de sus gustos
	public static void sugerirPromos(List<Promocion> promociones, Usuario usuario, boolean prefONo) {
		if (prefONo) {
			System.out.println("\nPromociones recomendadas para usted:");
		} else {
			System.out.println("\nOtras Promociones recomendadas para usted:");
		}
		
		mostrarPromociones(promociones, usuario);
		System.out.println(
				"_________________________________________________________________________________________________________________");
		System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y " + usuario.getTiempo()
				+ " hs disponibles.");
		System.out.println(
				"_________________________________________________________________________________________________________________\n");
		System.out.println("\nSi desea comprar una promocion del listado ingrese su numero\nSino presione la tecla C");

	}
	
	//Muestra las atracciones a sugerir al usuario dependiendo de sus gustos
	public static void sugerirAtracciones(List<Atraccion> atracciones, Usuario usuario, boolean prefONo) {
		if (prefONo) {
			System.out.println("\nAtracciones recomendadas para usted:");
		} else {
			System.out.println("\nOtras Atracciones recomendadas para usted:");
		}
		mostrarAtracciones(atracciones);
		System.out.println(
				"_________________________________________________________________________________________________________________");
		System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y " + usuario.getTiempo()
				+ " hs disponibles.");
		System.out.println(
				"_________________________________________________________________________________________________________________\n");
		System.out.println("\nSi desea comprar una atraccion del listado ingrese su numero\nSino presione la tecla C");
	}
		
		// ------------------------------Metodos que muestran listados por pantalla
		// Mostrar Promociones
		public static void mostrarPromociones(List<Promocion> promocionesMostrar, Usuario usuario) {
			List<Promocion> promociones = promocionesMostrar;
			int cant = 0;
			for (Promocion promocion : promociones) {
				if(promocion.costoPromocion() <= usuario.getPresupuesto() && promocion.tiempoPromocion() <= usuario.getTiempo()) {
					cant++;
					System.out.print(cant + " - " + promocion.getNombre() + " - Destinos: ");
					for (Atraccion atraccion : promocion.getLista_atracciones()) {
						System.out.print(atraccion.getNombre() + ", ");
					}
					System.out.println(promocion.ImprimirBonus() + ". Precio de la promo: " + promocion.costoPromocion()
							+ " monedas, duracion: " + promocion.tiempoPromocion() + " hs.");
				}
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
		// Llama a los metodos de actualizacion de: presupuesto y tiempo del usuario que
		// compro una promo
		public static void actualizarPromo(Usuario usuario, Promocion promocion, Integer itinerario_id) throws SQLException {
			System.out.println("\nHas elegido la promocion: " + promocion.getNombre());
			actualizarUsuarioPromocion(usuario, promocion,itinerario_id);
			actualizarCupoAtraccionPromo(promocion);
		}

		// Llama a los metodos de actualizacion de: presupuesto y tiempo del usuario que
		// compro una atraccion
		public static void actualizarAtraccion(Usuario usuario, Atraccion atraccion, Integer itinerario_id) throws SQLException {
			System.out.println("\nHas elegido la atraccion: " + atraccion.getNombre());
			actualizarUsuarioAtraccion(usuario, atraccion,itinerario_id);
			actualizarCupoAtraccion(atraccion);
		}
		
		
		//------------------------------Metodos de Actualizacion de cupos de ATRACCIONES--------------------------
		
		// Actualizar cupo de atracciones
		public static void actualizarCupoAtraccion(Atraccion atraccion) throws SQLException {
			AtraccionDAO atraccionDao = new AtraccionDAO();
			atraccionDao.actualizarAtraccion(atraccion);
		}
		
		// Actualizar cupo de atracciones de una promocion
		public static void actualizarCupoAtraccionPromo(Promocion promo) throws SQLException {
			List<Atraccion> atraccionesPromo = promo.getLista_atracciones();
			for (Atraccion atraccion : atraccionesPromo) {
				actualizarCupoAtraccion(atraccion);
			}
			if (promo.getAxb_id() != 0) {
				actualizarCupoAtraccion(promo.getAxb());
			}
		}
		
		//------------------------------Metodos de Actualizacion de USUARIO--------------------------
		// Actualizar presupuesto y tiempo del usuario cuando elige atraccion //VER con sql
		public static void actualizarUsuarioAtraccion(Usuario usuario, Atraccion atraccion, Integer itinerario_id) throws SQLException {
			UsuarioDAO usuarioDao = new UsuarioDAO();
			usuarioDao.actualizarUsuario(usuario, atraccion, itinerario_id);
		}
		
		// Actualizar presupuesto y tiempo del usuario cuando elige promocion //VER con sql
		public static void actualizarUsuarioPromocion(Usuario usuario, Promocion promocion, Integer itinerario_id) throws SQLException {
			UsuarioDAO usuarioDao = new UsuarioDAO();
			usuarioDao.actualizarUsuario(usuario, promocion, itinerario_id);
		}


}
