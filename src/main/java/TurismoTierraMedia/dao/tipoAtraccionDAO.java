package TurismoTierraMedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TurismoTierraMedia.TipoAtraccion;
import TurismoTierraMedia.db.ConnectionProvider;

public class tipoAtraccionDAO {

	// este metodo devuelve una lista con todos los tipos de atracciones...
	public List<TipoAtraccion> findAll() throws SQLException {
		List<TipoAtraccion> tipos_atracciones = new ArrayList<TipoAtraccion>();
		Connection connection = ConnectionProvider.getConnection();

		String query = "select * from tipo_de_atraccion";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			TipoAtraccion tipo_atraccion = totipoAtraccion(resultSet);
			tipos_atracciones.add(tipo_atraccion);
		}
		return tipos_atracciones;
	}

	// este metodo se encarga de llamar al constructor con los resultados de la
	// consulta
	public TipoAtraccion totipoAtraccion(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		return new TipoAtraccion(id, nombre);
	}

}
