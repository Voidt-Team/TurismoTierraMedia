package TurismoTierraMedia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class ItinerarioViejo {
	
	//------------------------------Metodos de Salida de Usuario ----------------------------

		// Mostrar Itinerario de compras
		public static void mostrarItinerario(Usuario usuario) {
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------------");
			if (usuario.getHistorialPromociones().isEmpty() && usuario.getHistorialAtracciones().isEmpty()) {
				System.out.println("Usted no pudo comprar nada!!!!");
			} else {
				System.out.println(
						"=================================================================================================================================");
				System.out.println("\nA continuacion le detallamos el itinerario:");
				double suma_costos = 0;
				double suma_horas = 0;
				if (!(usuario.getHistorialPromociones().isEmpty())) {
					List<Promocion> promocionesCompradas = usuario.getHistorialPromociones();
					System.out.println("\nPromociones Compradas:");
					for (Promocion promo : promocionesCompradas) {
						suma_costos += promo.costoPromocion();
						suma_horas += promo.tiempoPromocion();
						List<Atraccion> atraccionesPromo = promo.getAtracciones();
						System.out.println(
								"---------------------------------------------------------------------------------------------------------------------------------");
						System.out.println("\nPromocion: " + promo.getNombre() + ", precio: " + promo.costoPromocion()
								+ " monedas, duracion: " + promo.tiempoPromocion() + " hs., bonus: "
								+ promo.ImprimirBonus());
						System.out.println("\nAtracciones Incluidas:");
						for (Atraccion atraccion : atraccionesPromo) {
							System.out.println("\n* " + atraccion.getNombre());
						}
					}
		
					System.out.println(
							"_________________________________________________________________________________________________________________________________");
				}
				
				if (!(usuario.getHistorialAtracciones().isEmpty())) {
					List<Atraccion> atraccionesCompradas = usuario.getHistorialAtracciones();
					System.out.println("\nAtracciones Compradas:");
					
					for (Atraccion atraccion : atraccionesCompradas) {
						suma_costos += atraccion.getCosto();
						suma_horas += atraccion.getTiempo();
						System.out.println(
								"---------------------------------------------------------------------------------------------------------------------------------");
						System.out.println("\nAtraccion: " + atraccion.getNombre() + ", precio: " + atraccion.getCosto()
								+ " monedas, duracion: " + atraccion.getTiempo() + " hs.");
					}
				}
				System.out.println(
						"_________________________________________________________________________________________________________________________________");
				System.out.println("\nCosto total: " + suma_costos + " monedas, tiempo necesario: " + suma_horas + " hs.");
				System.out.println(
						"=================================================================================================================================");
			}
		}
		
		
			//Metodo que genera el archivo final por usuario
			public static void generarArchivoUsuario(Usuario unUsuario) {

				// este metodo va a ser para generar el archivo .txt con las compras del
				// usuario...

				try {
					String ruta = "./Archivos/Comprobantes/" + unUsuario.getNombre() + ".txt";

					// en esta variable se carga los datos a grabar en el archivo...
					String contenido = "";

					contenido = "Nombre: " + unUsuario.getNombre();
					contenido += "\n==================================================================================";
					contenido += "\nDetalles de su compra: ";
					contenido += "\n==================================================================================";
					if (!(unUsuario.getHistorialPromociones().isEmpty())) {
						double costoTotal = 0;
						double tiempoTotal = 0;
						contenido += "\nPromociones Compradas:";
						for (Promocion promo : unUsuario.getHistorialPromociones()) {
							List<Atraccion> atraccionesPromo = promo.getAtracciones();
							contenido += "\n----------------------------------------------------------------------------------";
							contenido += "\nPromocion: " + promo.getNombre() + ", precio: " + promo.costoPromocion()
									+ " monedas, duracion: " + promo.tiempoPromocion() + " hs., bonus: "
									+ promo.ImprimirBonus();
							contenido += "\nAtracciones Incluidas:";
							costoTotal += promo.costoPromocion();
							tiempoTotal += promo.tiempoPromocion();
							for (Atraccion atraccion : atraccionesPromo) {
								contenido += "\n* " + atraccion.getNombre();
							}
						}
						contenido += "\nCosto total de las promociones: " + costoTotal;
						contenido += "\nTiempo total de las promociones: " + tiempoTotal;
						contenido += "\n----------------------------------------------------------------------------------";
					}

					if (!(unUsuario.getHistorialAtracciones().isEmpty())) {
						double costoTotal = 0;
						double tiempoTotal = 0;

						contenido += "\nAtracciones Compradas:";
						for (Atraccion atraccion : unUsuario.getHistorialAtracciones()) {
							contenido += "\n----------------------------------------------------------------------------------";
							contenido += "\nAtraccion: " + atraccion.getNombre() + ", precio: " + atraccion.getCosto()
									+ " monedas, duracion: " + atraccion.getTiempo() + " hs.";
							costoTotal += atraccion.getCosto();
							tiempoTotal += atraccion.getTiempo();
						}
						contenido += "\nCosto total de las atracciones: " + costoTotal;
						contenido += "\nTiempo total de las atracciones: " + tiempoTotal;
						contenido += "\n----------------------------------------------------------------------------------";
					}

					File file = new File(ruta);

					// Si el archivo no existe es creado
					if (!file.exists()) {
						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(fw);

					bw.write(contenido);

					bw.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Archivo generado exitosamente!");
			}


}
