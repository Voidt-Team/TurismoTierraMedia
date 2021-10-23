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
	public List<Atraccion> findAllConJoin() throws SQLException {
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		Connection connection = ConnectionProvider.getConnection();
		//no recuerdo si se puede hacer este tipo de joins jaja
		//esto deberia traer las atracciones de una promocion
		String query = "SELECT A.* FROM atraccion A INNER JOIN  promocion_tiene_atraccion PA INNER JOIN promocion P ON PA.promocion_id = P.id and A.id = PA.atraccion_id";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Atraccion atraccion = toAtraccionesPromo(resultSet);
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
		Integer tipo_atraccion_id = resultSet.getInt("tipo_atraccion_id");
		Double latitud = resultSet.getDouble("latitud");
		Double longitud = resultSet.getDouble("longitud");
		return new Atraccion(id, nombre, costo, tiempo, cupo, tipo_atraccion_id, latitud, longitud);
	}
}
