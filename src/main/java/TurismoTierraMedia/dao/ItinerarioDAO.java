package TurismoTierraMedia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TurismoTierraMedia.Atraccion;
import TurismoTierraMedia.Itinerario;
import TurismoTierraMedia.Promocion;


public class ItinerarioDAO {

	private void insert (Atraccion at) throws SQLException{
		//aca deberia llamar a toItineraio y pasarle los datos para generar el objeto
		//debo generar un registro en itinerario
		/* insert into en itinerario_tiene_atraccion
		 */
              //https://www.sqlitetutorial.net/sqlite-java/insert/
		
	} 
	
	private void insert (Promocion pr) throws SQLException{
		//aca deberia llamar a toItineraio y pasarle los datos pra generar el objeto
		
		/* insert into en itinerario_tiene_promocion
		 */		
	}
	
	public void update (Interger id, Atraccion at) throws SQLException{
		//con un if pregunta si existe itinerario, sino llama a insert con parametro atraccion
		//pero si existe sigue la secuencia y describe si actualiz la lista atraccion o promocion...
		
	}
	
	public void update (Interger id, Promocion pr) throws SQLException{
		//con un if pregunta si existe itinerario, sino llama a insert con parametro promocion
		//pero si existe sigue la secuencia y describe si actualiz la lista atraccion o promocion...
	
	}
	
	//devuelve una lista con el itinerario del usuario pedido...
	public List<Itinerario> findallbyiduser(Integer id)throws SQLException{
		List<Itinerario> litinerario= new ArrayList<Itinerario>();
		
		return litinerario;
	}
	
	public Itinerario toItinerario(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		
		
		//aca se debe definir a que constructor llamar...
		//si desea llenar con atracciones o con promos
		
		o lleno it_tiene_atracc
		o lleno it_tiene_promo
		
		return new Itinerario(id, List<Atraccion> lista_atracciones, List<Promocion> lista_promociones);
	}
}
