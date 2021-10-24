package TurismoTierraMedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import TurismoTierraMedia.Atraccion;
import TurismoTierraMedia.Promocion;
import TurismoTierraMedia.db.ConnectionProvider;


public class PromocionDAO {
	// este metodo devuelve una lista con todos los usuarios...
	public List<Promocion> findAll() throws SQLException {
		List<Promocion> promociones = new ArrayList<Promocion>();
		Connection connection = ConnectionProvider.getConnection();

		String query = "select * from promocion";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Promocion promocion = toPromocion(resultSet);
			promociones.add(promocion);
		}
		return promociones;
	}

	// este metodo se encarga de llamar al constructor con los resultados de la
	// consulta
	public Promocion toPromocion(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		Integer absoluta = resultSet.getInt("absoluta");
		Integer axb = resultSet.getInt("axb");
		Double porcentual = resultSet.getDouble("porcentual");

		return new Promocion(id, nombre, absoluta, axb, porcentual);
	}

	// Esto deberia funcionar para traer las atracciones de una promo
	public List<Atraccion> findAllConJoin(integer id) throws SQLException {
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		Connection connection = ConnectionProvider.getConnection();
		
		//no recuerdo si se puede hacer este tipo de joins jaja
		//esto deberia traer las atracciones de una promocion
		
		//esta consulta trae la lista de atracciones de una promo buscada por id...
		//solo faltaria un join que me devuelva el nombre del tipo de atraccion... pero esa bien la idea
		String query = "SELECT A.* FROM atraccion A INNER JOIN  promocion_tiene_atraccion PA INNER JOIN promocion P ON PA.promocion_id = P.id and A.id = PA.atraccion_id and P.id=?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Atraccion atraccion = toAtraccionesPromo(resultSet);//aca creo yo que se podria llamar a atraccionDAO y usar su toAtraccion...
			atracciones.add(atraccion);
		}
		return atracciones;
	}

	// este metodo se encarga de llamar al constructor con los resultados de la
	// consulta
	// No se si esto este bien en realidad
	public Atraccion toAtraccionesPromo(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		Double costo = resultSet.getDouble("costo");
		Double tiempo = resultSet.getDouble("tiempo");
		Integer cupo = resultSet.getInt("cupo");
		String tipo_atraccion = resultSet.getString("tipo_atraccion");//ojo aca, el constructor recibe un string con el nombre

		return new Atraccion(id, nombre, costo, tiempo, cupo, tipo_atraccion);
	}
}
