package TurismoTierraMedia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import TurismoTierraMedia.Atraccion;
import TurismoTierraMedia.Itinerario;
import TurismoTierraMedia.Promocion;


public class ItinerarioDAO {

	public void insert (Itinerario it) throws SQLException{
		
	} 
	
	public void updateListAtraction (Atraccion at) throws SQLException{
		//si el llamado proviene del menu de atracciones disponibles 
	}

	public void delete (Itinerario it) throws SQLException{
		
	} 
	
	
	public Itinerario toItinerario(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		
		
		//aca se debe definir a que constructor llamar...
		//si desea llenar con atracciones o con promos
		return new Itinerario(id, List<Atraccion> lista_atracciones, List<Promocion> lista_promociones);
	}
}
