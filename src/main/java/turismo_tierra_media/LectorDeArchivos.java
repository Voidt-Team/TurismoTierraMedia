package turismo_tierra_media;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorDeArchivos {
		// Declaracion de variables listas 
		private static List<Atraccion> listaAtracciones;
		private static List<Usuario> listaUsuarios;
		private static List<Promocion> listaPromociones;

		//Metodo testeado - genera lista de usuarios
		public static List<Usuario> leerUsuarios() {
			listaUsuarios = new ArrayList<>();

			FileReader fr = null;
			BufferedReader br = null;

			try {
				fr = new FileReader("Archivos/Usuarios.txt");
				br = new BufferedReader(fr);

				String linea = br.readLine();
				while ((linea != null)) {
					String[] valores = linea.split(",");
					Usuario nuevoUsuario = new Usuario(valores[0], valores[1], (int) Double.parseDouble(valores[2]),
							Double.parseDouble(valores[3]));
					listaUsuarios.add(nuevoUsuario);
					linea = br.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fr != null) {
						fr.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return listaUsuarios;
		}

		// metodo testeado - genera lista de atracciones
		public static List<Atraccion> leerAtracciones() {
			listaAtracciones = new ArrayList<>();

			FileReader fr = null;
			BufferedReader br = null;

			try {
				fr = new FileReader("Archivos/Atracciones.txt");
				br = new BufferedReader(fr);

				String linea = br.readLine();
				while ((linea != null)) {
					String[] valores = linea.split(",");
					Atraccion nuevaAtraccion = new Atraccion(valores[0], (int) Double.parseDouble(valores[1]),
							Double.parseDouble(valores[2]), (int) Double.parseDouble(valores[3]), valores[4]);
					listaAtracciones.add(nuevaAtraccion);
					linea = br.readLine();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fr != null) {
						fr.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return listaAtracciones;
		}

		// metodo testeado - genera lista promociones
		public static List<Promocion> leerPromociones() {

			listaPromociones = new ArrayList<>();

			FileReader fr = null;
			BufferedReader br = null;

			try {
				fr = new FileReader("Archivos/Promociones.txt");
				br = new BufferedReader(fr);

				String linea = br.readLine();
				while ((linea != null)) {
					String[] valores = linea.split(",");

					if (!esNumerico(valores[valores.length - 1].trim())) {
						// promo axb...

						// llamado al constructor AxB con el pasaje de parametros llamando al metodo
						// buscarAtraccion
						AxB nuevaPromocion = new AxB(valores[0], analizarPromocion(valores),
								buscadorAtraccion(valores[valores.length - 1]));
						listaPromociones.add(nuevaPromocion);
					} else {
						try {
							// promo absoluta...
							Absoluta nuevaPromocion = new Absoluta(valores[0], analizarPromocion(valores),
									(int) Integer.parseInt(valores[valores.length - 1].trim()));
							listaPromociones.add(nuevaPromocion);
						} catch (NumberFormatException e) {
							// promo porcentual...
							Porcentual nuevaPromocion = new Porcentual(valores[0], analizarPromocion(valores),
									(double) Double.parseDouble(valores[valores.length - 1].trim()));
							listaPromociones.add(nuevaPromocion);

						}
					}
					linea = br.readLine();
				}

			} catch (

			IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fr != null) {
						fr.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return listaPromociones;
		}

		//----------------------
		
		// Metodo para verificar si la variable es numerica
		public static boolean esNumerico(String str) {
			// regexp para verificar si es numerico
			return str.matches("-?\\d+(\\.\\d+)?");
		}

		// Metodo que recibe la linea de lectura y devuelve un arraylist de las atracciones para el
		// llamado del constructor
		public static List<Atraccion> analizarPromocion(String[] lectura) {
			List<Atraccion> atracciones = new ArrayList<>();
			for (int x = 1; x <= (lectura.length - 2); x++) {
				atracciones.add(buscadorAtraccion(lectura[x]));
			}
			return atracciones;
		}
		
		
		// Metodo que recibe el nombre de una atraccion, busca en el arraylist de
		// atracciones y devuelve una atraccion
		// metodo testeado
		public static Atraccion buscadorAtraccion(String str) {
			Atraccion resultado = null;
			for (Atraccion atraccion : listaAtracciones) {
				if (atraccion.getNombre().equals(str.trim())) {
					resultado = atraccion;
				}
			}
			return resultado;
		}



}
