package turismo_tierra_media;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TurismoTierraMedia {

	private static List<Usuario> listaUsuarios = LectorDeArchivos.leerUsuarios();

	//Consola de lista de usuarios
	public static void consola() { 

		int opcion = 1;
		while (opcion != 99999) {
			// Si no hay mas usuarios se finaliza la ejecucion del programa
			if (listaUsuarios.isEmpty()) {
				System.out.println("--------- SE HAN PROCESADO TODOS LOS USUARIOS ---------");
				System.exit(0);
			}
			// Se crea el menu de opciones
			System.out.println("\nBienvenido a la tierra media!\nQue personaje te gustaria ser?");
			mostrarUsuarios();
			// Se selecciona el usuario que quiere ser
			System.out.println("\nIngrese el numero correspondiente a su personaje");
			// Se crea el objeto de tipo Scanner
			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();
			if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
				opcion = (int) Double.parseDouble(entrada);
			} else {
				opcion = listaUsuarios.size() + 1;
			}

			try {
				System.out.println("\nHas elegido ser: " + listaUsuarios.get(opcion - 1).getNombre());
				// El objeto creado se usa para llamar al metodo sugerir
				Sugeridor.sugerir(listaUsuarios.get(opcion - 1),listaUsuarios);
				opcion = 99999;
			} catch (IndexOutOfBoundsException ex) {
				System.out
						.println("El valor ingresado solamente puede ser un entero entre 1 y " + listaUsuarios.size());
			}
		}
	}
	
	// Mostrar lista de usuarios
	public static void mostrarUsuarios() {
		int p = 0;
		// creamos el iterator para recorrer la lista sin ordenar
		Iterator<Usuario> itUsuarios = listaUsuarios.iterator();

		// imprime la lista sin ordenar
		while (itUsuarios.hasNext()) {
			Usuario usuario = itUsuarios.next();
			p += 1;
			System.out.println(p + " - " + usuario.getNombre() + "------>   Atraccion preferida:"
					+ usuario.getAtraccionPreferida() + " - Presupuesto: " + usuario.getPresupuesto() + " monedas"
					+ " - Tiempo disponible: " + usuario.getTiempoDisponible() + " hs.");
		}
	}
				
	

	public static void main(String[] args) {
		consola();
	}

}