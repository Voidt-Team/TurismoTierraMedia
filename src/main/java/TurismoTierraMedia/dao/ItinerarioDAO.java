package TurismoTierraMedia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import TurismoTierraMedia.Atraccion;
import TurismoTierraMedia.Itinerario;
import TurismoTierraMedia.Promocion;


public class ItinerarioDAO {

	public Itinerario toItinerario(ResultSet resultSet) throws SQLException {
		//aca se debe definir a que constructor llamar...
		//si desea llenar con atracciones o con promos
		return new Itinerario(Integer id, List<Atraccion> lista_atracciones, List<Promocion> lista_promociones);
	}
}
