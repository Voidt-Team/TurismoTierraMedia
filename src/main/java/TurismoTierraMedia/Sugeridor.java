package TurismoTierraMedia;

import java.io.InputStreamReader;
import java.sql.SQLException;
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

		sugerirPromocionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
				atraccNoPreferidas, 1, listaUsuarios);

	}

	// ------------------------------Metodo que sugiere las promos
	// preferidas--------------------------
	public static void sugerirPromocionesPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
			List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
			int opcion, List<Usuario> listaUsuarios) throws SQLException {

		PromocionDAO promociones = new PromocionDAO();
		AtraccionDAO atracciones = new AtraccionDAO();
		
		
		while (!promosPreferidas.isEmpty()) {
			sugerirPromos(promosPreferidas, usuario, true);
			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();

			//Consultar si cancela la compra 
			if (entrada.toUpperCase().compareTo("F") == 0) {
				noMasCompras(usuario);
			}
			//Esta opcion es por si no quiere comprar promociones
			if (entrada.toUpperCase().compareTo("C") == 0) {
				sugerirAtraccionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			}
			try {
				if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
					opcion = (int) Double.parseDouble(entrada);

					//Aca se llama al metodo que actualiza el itinerario y ahi dentro al que actualiza la promocion y el usuario
					actualizarItinerarioPromocion(usuario,promosPreferidas.get(opcion-1));
					
					// Se filtra nuevamente las listas para volver a sugerir al usuario las listas
					// actualizadas
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
				System.out.println(
						"El valor ingresado solamente puede ser un entero entre 1 y " + promosPreferidas.size());
			}
		}

		if (promosPreferidas.isEmpty()) {
			System.out.println("No tienes promociones para elegir!");
			sugerirAtraccionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
					atraccNoPreferidas, opcion, listaUsuarios);
		}
	}

	
	// ------------------------------Metodo que sugiere las atracciones
	// preferidas--------------------------
	public static void sugerirAtraccionesPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
			List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
			int opcion, List<Usuario> listaUsuarios) throws SQLException {

		PromocionDAO promociones = new PromocionDAO();
		AtraccionDAO atracciones = new AtraccionDAO();

		sugerirAtracciones(atraccPreferidas, usuario, true);

		while (!atraccPreferidas.isEmpty()) {

			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();

			if (entrada.toUpperCase().compareTo("F") == 0) {
				noMasCompras(usuario);
			}
			if (entrada.toUpperCase().compareTo("C") == 0) {
				sugerirPromocionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			}
			try {
				if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
					opcion = (int) Double.parseDouble(entrada);
					
					//Aca se llama al metodo que actualiza el itinerario y ahi dentro al que actualiza la promocion y el usuario
					actualizarItinerarioAtraccion(usuario,atraccPreferidas.get(opcion - 1));

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
				System.out.println(
						"El valor ingresado solamente puede ser un entero entre 1 y " + atraccPreferidas.size());
			}
		}
		if (atraccPreferidas.isEmpty()) {
			System.out.println("No tienes atracciones para elegir!");
			sugerirPromocionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
					atraccNoPreferidas, opcion, listaUsuarios);
		}

	}



	// ------------------------------Metodo que sugiere las promos NO
	// preferidas--------------------------
	public static void sugerirPromocionesNoPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
			List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
			int opcion, List<Usuario> listaUsuarios) throws SQLException {

		PromocionDAO promociones = new PromocionDAO();
		AtraccionDAO atracciones = new AtraccionDAO();

		sugerirPromos(promosNoPreferidas, usuario, false);
		
		while (!promosNoPreferidas.isEmpty()) {

			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();

			if (entrada.toUpperCase().compareTo("F") == 0) {
				noMasCompras(usuario);
			}

			if (entrada.toUpperCase().compareTo("C") == 0) {
				sugerirAtraccionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			}
			try {
				if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
					opcion = (int) Double.parseDouble(entrada);

					//Aca se llama al metodo que actualiza el itinerario y ahi dentro al que actualiza la promocion y el usuario
					actualizarItinerarioPromocion(usuario,promosNoPreferidas.get(opcion-1));

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
				System.out.println(
						"El valor ingresado solamente puede ser un entero entre 1 y " + promosNoPreferidas.size());
			}
		}

		if (promosNoPreferidas.isEmpty()) {
			System.out.println("No tienes promociones para elegir!");
			sugerirAtraccionesNoPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
					atraccNoPreferidas, opcion, listaUsuarios);
		}

	}

	// ------------------------------Metodo que sugiere las atracciones NO
	// preferidas--------------------------
	public static void sugerirAtraccionesNoPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
			List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
			int opcion, List<Usuario> listaUsuarios) throws SQLException {

		PromocionDAO promociones = new PromocionDAO();
		AtraccionDAO atracciones = new AtraccionDAO();

		sugerirAtracciones(atraccNoPreferidas, usuario, false);
		
		while (!atraccNoPreferidas.isEmpty()) {

			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();

			if (entrada.toUpperCase().compareTo("F") == 0) {
				noMasCompras(usuario);
			}

			if (entrada.toUpperCase().compareTo("C") == 0) {
				sugerirPromocionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			}
			try {
				if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
					opcion = (int) Double.parseDouble(entrada);

					//Aca se llama al metodo que actualiza el itinerario y ahi dentro al que actualiza la promocion y el usuario
					actualizarItinerarioAtraccion(usuario,atraccPreferidas.get(opcion - 1));

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
				System.out.println(
						"El valor ingresado solamente puede ser un entero entre 1 y " + atraccNoPreferidas.size());
			}
		}

		if (atraccNoPreferidas.isEmpty()) {
			System.out.println("No tienes atracciones para elegir!");
			noMasCompras(usuario);
		}

	}
	
	
	//Metodo para actualizar itinerario de atracciones
	private static void actualizarItinerarioAtraccion(Usuario usuario, Atraccion atraccion) throws SQLException {
		// ACA ESTA LO DEL ITINERARIO
		ItinerarioDAO itinerario = new ItinerarioDAO();
		Integer itinerario_id = usuario.getIdItinerario();

		if (itinerario_id == null) {
			// Insert de itinerario
			itinerario.insertItinerario(usuario.getId());
			itinerario_id = itinerario.findById(usuario.getId()).getId();
			usuario.setIdItinerario(itinerario_id);
		}

		// Insertar atraccion de un itinerario
		itinerario.insertItinerario(atraccion, usuario);
		Itinerario itinerarioOBJ = itinerario.findById(usuario.getId());
		itinerarioOBJ.setId(itinerario_id);
		itinerarioOBJ.setUsuario_id(usuario.getId());
		itinerarioOBJ.setCosto(itinerarioOBJ.getCosto() + atraccion.getCosto());
		itinerarioOBJ.setTiempo(itinerarioOBJ.getTiempo() + atraccion.getTiempo());
		itinerarioOBJ.getlAtracciones().add(atraccion);
		//Aca se llaman a los otros metodos
		actualizarAtraccion(usuario, atraccion, itinerario_id);
		//Aca faltaria actualizar el valor del itinerario en costo y tiempo
	}
	
	//Metodo para actualizar itinerario de promociones
	private static void actualizarItinerarioPromocion(Usuario usuario, Promocion promocion) throws SQLException {
		// ACA ESTA LO DEL ITINERARIO
		ItinerarioDAO itinerario = new ItinerarioDAO();
		Integer itinerario_id = usuario.getIdItinerario();

		if (itinerario_id == null) {
			// Insert de itinerario
			itinerario.insertItinerario(usuario.getId());
			itinerario_id = itinerario.findById(usuario.getId()).getId();
			usuario.setIdItinerario(itinerario_id);
		}

		// Insertar atraccion de un itinerario
		itinerario.insertItinerario(promocion, usuario);
		Itinerario itinerarioOBJ = itinerario.findById(usuario.getId());
		itinerarioOBJ.setId(itinerario_id);
		itinerarioOBJ.setUsuario_id(usuario.getId());
		itinerarioOBJ.setCosto(itinerarioOBJ.getCosto() + promocion.costoPromocion());
		itinerarioOBJ.setTiempo(itinerarioOBJ.getTiempo() + promocion.tiempoPromocion());
		itinerarioOBJ.getlPromociones().add(promocion);
		//Aca se llaman a los otros metodos
		actualizarPromo(usuario, promocion, itinerario_id);
		//Aca faltaria actualizar el valor del itinerario en costo y tiempo
	}

	

	// ------------------------------Metodo para cuando ya no se puede comprar
	// mas--------------------------
	public static void noMasCompras(Usuario usuario) throws SQLException {
		System.out.println("Finalizo la ronda de compras!");
		ItinerarioDAO itinerario = new ItinerarioDAO();
		List<Promocion> promosItinerario = itinerario.buscarItinerarioPromociones(usuario.getIdItinerario());
		List<Atraccion> atraccItinerario = itinerario.buscarItinerarioAtracciones(usuario.getIdItinerario());

		if (!promosItinerario.isEmpty()) {
			mostrarItinerarioP(promosItinerario);
		}
		if (!atraccItinerario.isEmpty()) {
			mostrarItinerarioA(atraccItinerario);
		}
		App.consola();
	}

	private static void mostrarItinerarioA(List<Atraccion> atraccItinerario) {
		List<Atraccion> atraccionesCompradas = atraccItinerario;
		System.out.println("\nAtracciones Compradas:");
		double suma_costos = 0;
		double suma_horas = 0;
		for (Atraccion atraccion : atraccionesCompradas) {
			suma_costos += atraccion.getCosto();
			suma_horas += atraccion.getTiempo();
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("\nAtraccion: " + atraccion.getNombre() + ", precio: " + atraccion.getCosto()
					+ " monedas, duracion: " + atraccion.getTiempo() + " hs.");
		}
		System.out.println(
				"_________________________________________________________________________________________________________________________________");
		System.out.println(
				"\nCosto total de Atracciones: " + suma_costos + " monedas, tiempo necesario: " + suma_horas + " hs.");
		System.out.println(
				"=================================================================================================================================");

	}

	private static void mostrarItinerarioP(List<Promocion> promosItinerario) {
		System.out.println(
				"=================================================================================================================================");
		System.out.println("\nA continuacion le detallamos el itinerario:");
		double suma_costos = 0;
		double suma_horas = 0;
		List<Promocion> promocionesCompradas = promosItinerario;
		System.out.println("\nPromociones Compradas:");
		for (Promocion promo : promocionesCompradas) {
			suma_costos += promo.costoPromocion();
			suma_horas += promo.tiempoPromocion();
			List<Atraccion> atraccionesPromo = promo.getLista_atracciones();
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("\nPromocion: " + promo.getNombre() + ", precio: " + promo.costoPromocion()
					+ " monedas, duracion: " + promo.tiempoPromocion() + " hs., bonus: " + promo.ImprimirBonus());
			System.out.println("\nAtracciones Incluidas:");
			for (Atraccion atraccion : atraccionesPromo) {
				System.out.println("\n* " + atraccion.getNombre());
			}
		}
		System.out.println(
				"_________________________________________________________________________________________________________________________________");
		System.out.println(
				"\nCosto total de Promociones: " + suma_costos + " monedas, tiempo necesario: " + suma_horas + " hs.");
		System.out.println(
				"=================================================================================================================================");
	}

	// Muestra las promociones a sugerir al usuario dependiendo de sus gustos
	public static void sugerirPromos(List<Promocion> promociones, Usuario usuario, boolean prefONo) {
		if (!promociones.isEmpty()) {
			if (prefONo) {
				System.out.println("\nPromociones recomendadas para usted:");
			} else {
				System.out.println("\nOtras Promociones recomendadas para usted:");
			}
			mostrarPromociones(promociones, usuario);

			System.out.println(
					"_________________________________________________________________________________________________________________\n");
			System.out.println(
					"\nSi desea comprar una promocion del listado ingrese su numero\nSino presione la tecla C");
			System.out.println(
					"_________________________________________________________________________________________________________________\n");
			System.out.println("\nSi desea finalizar su comprar ingrese la tecla F");

		}

	}

	// Muestra las atracciones a sugerir al usuario dependiendo de sus gustos
	public static void sugerirAtracciones(List<Atraccion> atracciones, Usuario usuario, boolean prefONo) {

		if (!atracciones.isEmpty()) {

			if (prefONo) {
				System.out.println("\nAtracciones recomendadas para usted:");
			} else {
				System.out.println("\nOtras Atracciones recomendadas para usted:");
			}
			mostrarAtracciones(atracciones);
			System.out.println(
					"_________________________________________________________________________________________________________________\n");
			System.out.println(
					"\nSi desea comprar una atraccion del listado ingrese su numero\nSino presione la tecla C");
			System.out.println(
					"_________________________________________________________________________________________________________________\n");
			System.out.println("\nSi desea finalizar su comprar ingrese la tecla F");
		}
	}

	// ------------------------------Metodos que muestran listados por pantalla
	// Mostrar Promociones
	public static void mostrarPromociones(List<Promocion> promocionesMostrar, Usuario usuario) {
		List<Promocion> promociones = promocionesMostrar;
		int cant = 0;

		for (Promocion promocion : promociones) {		
		
			if (promocion.costoPromocion() <= usuario.getPresupuesto()
					&& promocion.tiempoPromocion() <= usuario.getTiempo()) {
				
				cant++;
				System.out.print(cant + " - " + promocion.getNombre() + " - Destinos: ");
				for (Atraccion atraccion : promocion.getLista_atracciones()) {
					System.out.print(atraccion.getNombre() + ", ");
				}
				System.out.println(promocion.ImprimirBonus() + ". Precio de la promo: " + promocion.costoPromocion()
						+ " monedas, duracion: " + promocion.tiempoPromocion() + " hs.");
			
			}
		}
		if (cant == 0) {
			promocionesMostrar.removeAll(promocionesMostrar);
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

	// ----------------------Metodos de
	// actualizacion----------------------------------
	// Llama a los metodos de actualizacion de: presupuesto y tiempo del usuario que
	// compro una promo
	public static void actualizarPromo(Usuario usuario, Promocion promocion, Integer itinerario_id)
			throws SQLException {
		System.out.println("\nHas elegido la promocion: " + promocion.getNombre());
		actualizarUsuarioPromocion(usuario, promocion, itinerario_id);
		actualizarCupoAtraccionPromo(promocion);
	}

	// Llama a los metodos de actualizacion de: presupuesto y tiempo del usuario que
	// compro una atraccion
	public static void actualizarAtraccion(Usuario usuario, Atraccion atraccion, Integer itinerario_id)
			throws SQLException {
		System.out.println("\nHas elegido la atraccion: " + atraccion.getNombre());
		actualizarUsuarioAtraccion(usuario, atraccion, itinerario_id);
		actualizarCupoAtraccion(atraccion);
	}

	// ------------------------------Metodos de Actualizacion de cupos de
	// ATRACCIONES--------------------------

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

	// ------------------------------Metodos de Actualizacion de
	// USUARIO--------------------------
	// Actualizar presupuesto y tiempo del usuario cuando elige atraccion //VER con
	// sql
	public static void actualizarUsuarioAtraccion(Usuario usuario, Atraccion atraccion, Integer itinerario_id)
			throws SQLException {
		UsuarioDAO usuarioDao = new UsuarioDAO();
		usuarioDao.actualizarUsuario(usuario, atraccion, itinerario_id);
	}

	// Actualizar presupuesto y tiempo del usuario cuando elige promocion //VER con
	// sql
	public static void actualizarUsuarioPromocion(Usuario usuario, Promocion promocion, Integer itinerario_id)
			throws SQLException {
		UsuarioDAO usuarioDao = new UsuarioDAO();
		usuarioDao.actualizarUsuario(usuario, promocion, itinerario_id);
	}

}
