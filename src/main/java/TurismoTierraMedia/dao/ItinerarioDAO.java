package TurismoTierraMedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import TurismoTierraMedia.Atraccion;
import TurismoTierraMedia.dao.AtraccionDAO;
import TurismoTierraMedia.Itinerario;
import TurismoTierraMedia.Promocion;
import TurismoTierraMedia.db.ConnectionProvider;


public class ItinerarioDAO {

	//aca se inserta un registro en la tabla itinerario, luego debemos usar esto para modificar usuario
	private void insert() throws SQLException{
		Connection connection = ConnectionProvider.getConnection();
		String query = "INSERT INTO itineratio(\"fecha\") VALUES (?)";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//obtengo la fecha del sistema, la casteo a string y se la paso a la query
		preparedStatement.setString(1,LocalDateTime.now().toString());
		preparedStatement.executeUpdate();

	}
	
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
	
	//devuelve un itinerario
	public Itinerario findById(Integer id) throws SQLException {
		Itinerario itinerario = null;
		
		Connection connection = ConnectionProvider.getConnection();

		String query = "SELECT * from itineratio i inner join itineratio_tiene_atraccion inner join itineratio_tiene_promocion where i.id=?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			itinerario = toItinerario(resultSet);
		}

		return itinerario;
	}

	
	public Itinerario toItinerario(ResultSet resultSet) throws SQLException {
		
		//listas null
		Integer id = resultSet.getInt("id");
		String fecha = resultSet.getString("fecha");
		List<Atraccion> lista_atracciones = new ArrayList<Atraccion>();
		List<Promocion> lista_promociones = new ArrayList<Promocion>();
		
		
		return new Itinerario(id, fecha, lista_atracciones, lista_promociones);
	}
}
