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
	// este metodo devuelve una lista con todas las promociones
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
		// Aca le asignamos el resultado de findAllAttractionsByPromoId a la lista de
		// atracciones de la promo que se esta construyendo
		List<Atraccion> lista_atracciones = findAllAttractionsByPromoId(id);

		return new Promocion(id, nombre, absoluta, axb, porcentual, lista_atracciones);
	}

	// devuelve una lista con las atracciones que contiene una promocion solicitada
	public List<Atraccion> findAllAttractionsByPromoId(Integer id) throws SQLException {
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		Connection connection = ConnectionProvider.getConnection();

		// esta consulta trae la lista de atracciones de una promo buscada por id...
		// String query = "SELECT z.id,z.nombre,z.costo,z.tiempo,z.cupo,z.TipoAtraccion
		// FROM (select A.*,ta.nombre as \"TipoAtraccion\" from atraccion A join
		// tipo_de_atraccion ta on A.tipo_atraccion_id=ta.id) z INNER JOIN
		// promocion_tiene_atraccion PA INNER JOIN promocion P ON PA.promocion_id = P.id
		// and z.id = PA.atraccion_id and P.id=?";

		String query = "SELECT  A.* FROM promocion P INNER JOIN  promocion_tiene_atraccion PA ON P.id = PA.promocion_id INNER JOIN atraccion A ON PA.atraccion_id = A.id WHERE P.id = ? ORDER BY id";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Atraccion atraccion = toAtraccionesPromo(resultSet);
			atracciones.add(atraccion);
		}
		return atracciones;
	}

	// este metodo se encarga de llamar al constructor con los resultados de la
	// consulta
	public Atraccion toAtraccionesPromo(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		Double costo = resultSet.getDouble("costo");
		Double tiempo = resultSet.getDouble("tiempo");
		Integer cupo = resultSet.getInt("cupo");
		String tipo_atraccion = resultSet.getString("tipo_atraccion");

		return new Atraccion(id, nombre, costo, tiempo, cupo, tipo_atraccion);
	}
}
