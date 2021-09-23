package turismo_tierra_media;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Sugeridor {
	private static List<Atraccion> listaAtracciones = LectorDeArchivos.leerAtracciones(); 
	private static List<Promocion> listaPromociones = LectorDeArchivos.leerPromociones();

	
	
	
	// Consola de lista de sugerencias
	public static void sugerir(Usuario usuario, List<Usuario> listaUsuarios) {
		List<ArrayList<Promocion>> listasFiltradasP = filtrarListasPromocion(usuario);
		List<ArrayList<Atraccion>> listasFiltradasA = filtrarListasAtraccion(usuario);
		List<Promocion> promosPreferidas = listasFiltradasP.get(0);
		List<Atraccion> atraccPreferidas = listasFiltradasA.get(0);
		List<Promocion> promosNoPreferidas = listasFiltradasP.get(1);
		List<Atraccion> atraccNoPreferidas = listasFiltradasA.get(1);
		
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
				borrarUsuario(usuario, opcion, listaUsuarios);
			}
		}
	}
	
	
	private static void sugerirPromocionesPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
			List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
			int opcion, List<Usuario> listaUsuarios) {
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
					List<ArrayList<Promocion>> listasFiltradasP = filtrarListasPromocion(usuario);
					List<ArrayList<Atraccion>> listasFiltradasA = filtrarListasAtraccion(usuario);
					promosPreferidas = listasFiltradasP.get(0);
					atraccPreferidas = listasFiltradasA.get(0);
					promosNoPreferidas = listasFiltradasP.get(1);
					atraccNoPreferidas = listasFiltradasA.get(1);
				} else {
					opcion = promosPreferidas.size() + 1;
				}
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("El valor ingresado solamente puede ser un entero entre 1 y " + promosPreferidas.size());
			}
		}
		sugerir(usuario, listaUsuarios);
	}
	
	private static void sugerirAtraccionesPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
			List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
			int opcion, List<Usuario> listaUsuarios) {
		while(!atraccPreferidas.isEmpty()) {
			sugerirAtracciones(atraccPreferidas, usuario, true);
			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();
			if (entrada.toUpperCase().compareTo("C") == 0) {
				sugerirPromocionesPreferidas(usuario, promosPreferidas, atraccPreferidas, promosNoPreferidas,
						atraccNoPreferidas, opcion, listaUsuarios);
			}
			try {
				if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
					opcion = (int) Double.parseDouble(entrada);
					actualizarAtraccion(usuario, atraccPreferidas.get(opcion-1));
					List<ArrayList<Promocion>> listasFiltradasP = filtrarListasPromocion(usuario);
					List<ArrayList<Atraccion>> listasFiltradasA = filtrarListasAtraccion(usuario);
					promosPreferidas = listasFiltradasP.get(0);
					atraccPreferidas = listasFiltradasA.get(0);
					promosNoPreferidas = listasFiltradasP.get(1);
					atraccNoPreferidas = listasFiltradasA.get(1);
				} else {
					opcion = atraccPreferidas.size() + 1;
				}
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("El valor ingresado solamente puede ser un entero entre 1 y " + atraccPreferidas.size());
			}				
		}
		sugerir(usuario, listaUsuarios);
	}

	private static void sugerirPromocionesNoPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
			List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
			int opcion, List<Usuario> listaUsuarios) {
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
					List<ArrayList<Promocion>> listasFiltradasP = filtrarListasPromocion(usuario);
					List<ArrayList<Atraccion>> listasFiltradasA = filtrarListasAtraccion(usuario);
					promosPreferidas = listasFiltradasP.get(0);
					atraccPreferidas = listasFiltradasA.get(0);
					promosNoPreferidas = listasFiltradasP.get(1);
					atraccNoPreferidas = listasFiltradasA.get(1);
				} else {
					opcion = promosNoPreferidas.size() + 1;
				}
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("El valor ingresado solamente puede ser un entero entre 1 y " + promosNoPreferidas.size());
			}
		}
		sugerir(usuario, listaUsuarios);
	}


	private static void sugerirAtraccionesNoPreferidas(Usuario usuario, List<Promocion> promosPreferidas,
			List<Atraccion> atraccPreferidas, List<Promocion> promosNoPreferidas, List<Atraccion> atraccNoPreferidas,
			int opcion, List<Usuario> listaUsuarios) {
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
					List<ArrayList<Promocion>> listasFiltradasP = filtrarListasPromocion(usuario);
					List<ArrayList<Atraccion>> listasFiltradasA = filtrarListasAtraccion(usuario);
					promosPreferidas = listasFiltradasP.get(0);
					atraccPreferidas = listasFiltradasA.get(0);
					promosNoPreferidas = listasFiltradasP.get(1);
					atraccNoPreferidas = listasFiltradasA.get(1);
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



	//----------------------Metodos de sugerencias----------------------------------

	public static void sugerirPromos(List<Promocion> promociones, Usuario usuario, boolean prefONo) {
		if(prefONo) {
			System.out.println("\nPromociones recomendadas para usted:");
		}else {
			System.out.println("\nOtras Promociones recomendadas para usted:");
		}
		mostrarPromociones(promociones);
		System.out.println("_________________________________________________________________________________________________________________");
		System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y " + usuario.getTiempoDisponible()+ " hs disponibles.");
		System.out.println("_________________________________________________________________________________________________________________\n");
		System.out.println("\nSi desea comprar una promocion del listado ingrese su numero\nSino presione la tecla C");
	}
	
	private static void actualizarPromo(Usuario usuario, Promocion promocion) {
		System.out.println("\nHas elegido la promocion: " + promocion.getNombre());
		actualizarUsuarioPromocion(usuario, promocion);
		actualizarHistorialPromociones(usuario, promocion);
		actualizarCupoAtraccionPromo(promocion);
	}
	
	
	public static void sugerirAtracciones(List<Atraccion> atracciones, Usuario usuario, boolean prefONo) {
		if(prefONo) {
			System.out.println("\nAtracciones recomendadas para usted:");
		}else {
			System.out.println("\nOtras Atracciones recomendadas para usted:");
		}
		ordenarAtraccionesCostoHoras(atracciones);
		mostrarAtracciones(atracciones);
		System.out.println("_________________________________________________________________________________________________________________");
		System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y " + usuario.getTiempoDisponible()+ " hs disponibles.");
		System.out.println("_________________________________________________________________________________________________________________\n");
		System.out.println("\nSi desea comprar una atraccion del listado ingrese su numero\nSino presione la tecla C");
	}

	private static void actualizarAtraccion(Usuario usuario, Atraccion atraccion) {
		System.out.println("\nHas elegido la atraccion: " + atraccion.getNombre());
		actualizarUsuarioAtraccion(usuario, atraccion);
		actualizarHistorialAtracciones(usuario, atraccion);
		actualizarCupoAtraccion(atraccion);
	}
//------------------------------------------------------------------------------------------

	// Metodo para cuando ya no se puede comprar mas
	public static void borrarUsuario(Usuario usuario, int opcion, List<Usuario> listaUsuarios) {
		//System.out.println("No puedes realizar compras!");
		Itinerario.generarArchivoUsuario(usuario);
		opcion = 99999;
		Itinerario.mostrarItinerario(usuario);
		listaUsuarios.remove(usuario);
		TurismoTierraMedia.consola();
	}
	
	
	// ------------------------------Metodos de Actualizacion de listas de usuario
		// ----------------------------
		// Actualizar historial de Atracciones
		public static void actualizarHistorialAtracciones(Usuario usuario, Atraccion atraccion) {
			usuario.getHistorialAtracciones().add(atraccion);
			usuario.getTodasLasAtracciones().add(atraccion);
		}

		// Actualizar historial de Promociones
		public static void actualizarHistorialPromociones(Usuario usuario, Promocion promo) {
			usuario.getHistorialPromociones().add(promo);
			for(Atraccion ap: promo.getAtracciones()) {
				usuario.getTodasLasAtracciones().add(ap);
			}
			if(promo.tipoPromocion()== 3) {
				String gratis = promo.ImprimirBonus();
				for (Atraccion atraccL : listaAtracciones) {
					if (gratis.compareTo(atraccL.getNombre()) == 0) {
						usuario.getTodasLasAtracciones().add(atraccL);
					}
				}
			}
		}

		// Actualizar presupuesto y tiempo del usuario cuando elige promocion
		public static void actualizarUsuarioPromocion(Usuario usuario, Promocion promo) {
			usuario.setPresupuesto((int) (usuario.getPresupuesto() - promo.costoPromocion()));
			usuario.setTiempoDisponible(usuario.getTiempoDisponible() - promo.tiempoPromocion());
		}

		// Actualizar presupuesto y tiempo del usuario cuando elige atraccion
		public static void actualizarUsuarioAtraccion(Usuario usuario, Atraccion atraccion) {
			usuario.setPresupuesto((int) (usuario.getPresupuesto() - atraccion.getCosto()));
			usuario.setTiempoDisponible(usuario.getTiempoDisponible() - atraccion.getTiempo());
		}

		// ------------------------------Metodos de Actualizacion de listas: Atracciones
		// y Promociones ----------------------------

		// Actualizar del cupo de atraccion que esta en una promocion
		public static void actualizarCupoAtraccionPromo(Promocion promo) {
			List<Atraccion> atraccionesPromo = new ArrayList<Atraccion>();
			for (Atraccion a : listaAtracciones) {
				atraccionesPromo = promo.getAtracciones();
				for (Atraccion atracc : atraccionesPromo) {
					if ((a.getNombre()).compareTo(atracc.getNombre()) == 0) {
						a.setCupo(a.getCupo() - 1);
					}
				}
			}
			// Esta parte es para actualizar el cupo de la atraccion gratis si la promo es
			// AxB
			if (promo.tipoPromocion() == 3) {
				String gratis = promo.ImprimirBonus();
				for (Atraccion atraccion : listaAtracciones) {
					if (gratis.compareTo(atraccion.getNombre()) == 0) {
						atraccion.setCupo(atraccion.getCupo() - 1);
					}
				}
			}

		}

		// Actualizar cupo de la atraccion
		// metodo testeado...
		public static void actualizarCupoAtraccion(Atraccion atraccion) {
			atraccion.setCupo(atraccion.getCupo() - 1);
		}

	//Filtrar Promociones
	public static List<ArrayList<Promocion>> filtrarListasPromocion(Usuario usuario) {
		String preferida = usuario.getAtraccionPreferida();
		double tiempo = usuario.getTiempoDisponible();
		double presupuesto = usuario.getPresupuesto();
		
		List<ArrayList<Promocion>> listasFiltradas = new ArrayList<ArrayList<Promocion>>();
		List<Promocion>listaPromocionesUsuario = filtrarHistorialPromos(usuario);
		List<Promocion> promosPreferidas = new ArrayList<Promocion>();
		List<Promocion> promosNoPreferidas = new ArrayList<Promocion>();
		List<Atraccion> atraccionesP = new ArrayList<Atraccion>();
		
		// Se recorre la lista original de promociones
		for (Promocion promocion : listaPromocionesUsuario) {
			atraccionesP = promocion.getAtracciones();
			int pref = 0;
			int nPref = 0;
			// este if (si es 3 es axb)
			if (promocion.tipoPromocion() == 3) {
				tipoAtraccionGratis(promocion);
				for (Atraccion atraccionP : atraccionesP) {
					if (tipoAtraccionGratis(promocion).compareTo(preferida) == 0) {
						if (atraccionP.getCupo() > 0 && presupuesto >= promocion.costoPromocion()
								&& tiempo >= promocion.tiempoPromocion()) {
							pref++;
						}
					} else {
						if (atraccionP.getCupo() > 0 && presupuesto >= promocion.costoPromocion()
								&& tiempo >= promocion.tiempoPromocion()) {
							nPref++;
						}
					}
				}
			} else {
				for (Atraccion atraccionP : atraccionesP) {
					if ((atraccionP.getTipoDeAtraccion()).compareTo(preferida) == 0) {
						if (atraccionP.getCupo() > 0 && presupuesto >= promocion.costoPromocion()
								&& tiempo >= promocion.tiempoPromocion()) {
							pref++;
						}
					} else {
						if (atraccionP.getCupo() > 0 && presupuesto >= promocion.costoPromocion()
								&& tiempo >= promocion.tiempoPromocion()) {
							nPref++;
						}
					}
				}
			}
			if (pref > 0) {
				promosPreferidas.add(promocion);
			}
			if (nPref == atraccionesP.size()) {
				promosNoPreferidas.add(promocion);
			}
		}
		listasFiltradas.add((ArrayList<Promocion>) promosPreferidas);
		listasFiltradas.add((ArrayList<Promocion>) promosNoPreferidas);
		return listasFiltradas;
	}
	
	//Filtrar atracciones
	public static List<ArrayList<Atraccion>> filtrarListasAtraccion(Usuario usuario) {
		String preferida = usuario.getAtraccionPreferida();
		double tiempo = usuario.getTiempoDisponible();
		double presupuesto = usuario.getPresupuesto();
		
		List<ArrayList<Atraccion>> listasFiltradas = new ArrayList<ArrayList<Atraccion>>();
		List<Atraccion>listaAtraccionesUsuario = filtrarHistorialAtracciones(usuario);
		List<Atraccion> atraccPreferidas = new ArrayList<Atraccion>();
		List<Atraccion> atraccNoPreferidas = new ArrayList<Atraccion>();
		
		// Filtrar Atracciones
		for (Atraccion atraccion : listaAtraccionesUsuario) {
			if (presupuesto >= atraccion.getCosto() && tiempo >= atraccion.getTiempo() && atraccion.getCupo() > 0) {
				if (atraccion.getTipoDeAtraccion().compareTo(preferida) == 0) {
					atraccPreferidas.add(atraccion);
				} else {
					atraccNoPreferidas.add(atraccion);
				}
			}
		}
		listasFiltradas.add((ArrayList<Atraccion>) atraccPreferidas);
		listasFiltradas.add((ArrayList<Atraccion>) atraccNoPreferidas);
		return listasFiltradas;
	}
	
	
	// Metodo que devuelve el tipo del destino extra
		public static String tipoAtraccionGratis(Promocion promocion) {
			String gratis = promocion.ImprimirBonus();
			String tipo_gratis = "";
			for (Atraccion atraccion : listaAtracciones) {
				if (gratis.compareTo(atraccion.getNombre()) == 0) {
					tipo_gratis = atraccion.getTipoDeAtraccion();
				}
			}
			return tipo_gratis;
		}
		
		// Metodo que devuelve el cupo del destino extra
		public static int cupoAtraccionGratis(Promocion promocion) {
			String gratis = promocion.ImprimirBonus();
			int cupo_gratis = 0;
			for (Atraccion atraccion : listaAtracciones) {
				if (gratis.compareTo(atraccion.getNombre()) == 0) {
					cupo_gratis = atraccion.getCupo();
				}
			}
			return cupo_gratis;
		}
			
		

		
	//Retorna lista de promociones sin las del historial del usuario
	public static List<Promocion> filtrarHistorialPromos(Usuario usuario) {
		List<Promocion> promosNoCompradas = new ArrayList<Promocion>();
		int cont = 0; 
		for(Promocion promo: listaPromociones) {
			if(usuario.getHistorialPromociones().contains(promo)) {
				cont++;
			}
			for(Atraccion atraccionP: promo.getAtracciones()) {
				if(usuario.getTodasLasAtracciones().contains(atraccionP)) {
					cont++;
				}
			}
			if(promo.tipoPromocion() == 3) {
				for(Atraccion atraccion: listaAtracciones) {
					if(promo.ImprimirBonus().compareTo(atraccion.getNombre()) == 0) {
						Atraccion bonus = atraccion;
						if(usuario.getTodasLasAtracciones().contains(bonus)) {
							cont++;
						}
					}
				}
			}
			if(cont == 0) {
				promosNoCompradas.add(promo);
			}
		}
		return promosNoCompradas;
	}
	
	
	
	// Retorna lista de atracciones sin las del historial del usuario
	public static List<Atraccion> filtrarHistorialAtracciones(Usuario usuario) {
		List<Atraccion> listaAtraccionesSinHistorial = new ArrayList<Atraccion>();
		int cont = 0;
		for (Atraccion atraccion : listaAtracciones) {
			if (usuario.getTodasLasAtracciones().contains(atraccion)) {
				cont++;
			}
			if (cont == 0) {
				listaAtraccionesSinHistorial.add(atraccion);
			}
		}
		return listaAtraccionesSinHistorial;
	}
	
	

	
	// ------------------------------Metodos que muestran listados por pantalla
		// ----------------------------
		// Mostrar Promociones
		public static void mostrarPromociones(List<Promocion> promocionesMostrar) {
			List<Promocion> promociones = promocionesMostrar;
			int cant = 0;
			for (Promocion promocion : promociones) {
				cant++;
				System.out.print(cant + " - " + promocion.getNombre() + " - Destinos: ");
				for (Atraccion atraccion : promocion.getAtracciones()) {
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
	
	
	// Metodo que ordena las atracciones por costo y horas, respectivamente
	// metodo testeado
	public static void ordenarAtraccionesCostoHoras(List<Atraccion> listaAtracciones) {
		Collections.sort(listaAtracciones, new ComparadorAtracciones());
	}

}
