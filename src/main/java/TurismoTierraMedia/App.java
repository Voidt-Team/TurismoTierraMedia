package TurismoTierraMedia;

import java.util.List;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import TurismoTierraMedia.Usuario;
import TurismoTierraMedia.dao.ItinerarioDAO;
import TurismoTierraMedia.dao.UsuarioDAO;

public class App {
	public static void consola() throws SQLException { 
		UsuarioDAO usuarios = new UsuarioDAO();
		List<Usuario> lusuarios = usuarios.findAll();
		ItinerarioDAO itinDAO = new ItinerarioDAO();
		Itinerario itin = null;
		
		int opcion = 1;
		while (opcion != 99999) {
			// Si no hay mas usuarios se finaliza la ejecucion del programa
			if (lusuarios.isEmpty()) {
				System.out.println("--------- SE HAN PROCESADO TODOS LOS USUARIOS ---------");
				System.exit(0);
			}
			// Se crea el menu de opciones
			System.out.println("\nBienvenido a la tierra media!\nQue personaje te gustaria ser?");
			imprimirUsuarios(lusuarios);
			// Se selecciona el usuario que quiere ser
			System.out.println("\nIngrese el numero correspondiente a su personaje");
			// Se crea el objeto de tipo Scanner
			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();
			if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
				opcion = (int) Double.parseDouble(entrada);
			} else {
				opcion = lusuarios.size() + 1;
			}
			try {
				System.out.println("\nHas elegido ser: " + lusuarios.get(opcion - 1).getNombre());
				// llamamos al metodo que genera el itinerario para el usuario
				itinDAO.insertItinerario(lusuarios.get(opcion - 1).getId());
				//recuperamos el objeto itinerario para el usuario seleccionado
				itin = itinDAO.findById(lusuarios.get(opcion - 1).getId());
				//actualizamos la tabla usuarios con el id itinerario recien creado
				usuarios.actualizarUsuario(lusuarios.get(opcion - 1), itin.getId());
				//actualizamos el estado del objeto usuario seleccionado con el id de itinerario para ese usuario
				lusuarios.get(opcion - 1).setIdItinerario(itin.getId());
				// El objeto creado se usa para llamar al metodo sugerir
				Sugeridor.sugerir(lusuarios.get(opcion - 1),lusuarios);
				opcion = 99999;
			} catch (IndexOutOfBoundsException ex) {
				System.out
						.println("El valor ingresado solamente puede ser un entero entre 1 y " + lusuarios.size());
			}
		}
	}
	public static void imprimirUsuarios(List<Usuario> lusuarios) {
		int p= 0;
		// creamos el iterator para recorrer la lista sin ordenar
		Iterator<Usuario> itUsuarios = lusuarios.iterator();

		// imprime la lista sin ordenar
		while (itUsuarios.hasNext()) {
			Usuario usuario = itUsuarios.next();
			p++;
			System.out.println(p + " - " + usuario.getNombre() + "------>   Atraccion preferida:"
					+ usuario.getAtraccion_preferida() + " - Presupuesto: " + usuario.getPresupuesto() + " monedas"
					+ " - Tiempo disponible: " + usuario.getTiempo() + " hs.");
		}
	}
	
	public static void main(String[] args) throws SQLException {
		consola();
	}
}

