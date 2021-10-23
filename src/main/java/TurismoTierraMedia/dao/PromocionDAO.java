package TurismoTierraMedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		String nombre = resultSet.getString("nombre");
		Integer absoluta = resultSet.getInt("absoluta");
		Integer axb = resultSet.getInt("axb");
		Double porcentual = resultSet.getDouble("porcentual");

		return new Promocion(nombre, absoluta, axb, porcentual);

	}

}
