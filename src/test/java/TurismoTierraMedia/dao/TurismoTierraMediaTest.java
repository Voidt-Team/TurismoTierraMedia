package TurismoTierraMedia.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TurismoTierraMediaTest {

	Atraccion prueba;
	List<Atraccion> listaAtracciones;
	List<Usuario> listaUsuarios;
	List<Promocion> listaPromociones;
	
	@Before
	public void setUp() {
		
		listaUsuarios=LectorDeArchivos.leerUsuarios();
		listaAtracciones = LectorDeArchivos.leerAtracciones();
		listaPromociones = LectorDeArchivos.leerPromociones();
		
	}
	
	@Test
	public void testLeerUsuarios() {
		assertNotNull(listaUsuarios);
	}

	@Test
	public void testLeerAtracciones() {
		assertNotNull(listaAtracciones);;
	}

	@Test
	public void testLeerPromociones() {
		assertNotNull(listaAtracciones);;
	}
	

	
	@Test
	public void testbuscadorAtraccion() {//busca la atraccion y de paso pregunta su tiempo para saber que esta trayendo el objeto
		prueba = LectorDeArchivos.buscadorAtraccion("La Comarca");
		Assert.assertEquals("La Comarca",prueba.getNombre());
		Assert.assertEquals(6.5,prueba.getTiempo(),0);
		
	}
	
	@Test
	public void testOrdenarAtraccionesPorCostoHoras() {
		List<Atraccion> listaCopia;
		listaCopia = new ArrayList<>(listaAtracciones);
		SugeridorViejo.ordenarAtraccionesCostoHoras(listaCopia);
		Assert.assertNotEquals(listaAtracciones, listaCopia);
		
	}
	
	@Test
	public void actualizarCupoAtraccion() {//recibe una atraccion y le resta 1 a su cupo
		Atraccion atraccion = listaAtracciones.get(2);
		SugeridorViejo.actualizarCupoAtraccion(atraccion);
		Assert.assertNotEquals(150, atraccion.getCupo());
		
	}

}
