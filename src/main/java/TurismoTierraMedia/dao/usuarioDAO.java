package TurismoTierraMedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TurismoTierraMedia.db.ConnectionProvider;
import TurismoTierraMedia.Usuario;

public class usuarioDAO {

	//este metodo devuelve una lista con todos los usuarios...
	public List<Usuario> findAll() throws SQLException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Connection connection = ConnectionProvider.getConnection();
		
		//formular mejor la consulta para que traiga el nombre del tipo de atraccion y no el id...
		String query = "select * from usuarios";

		PreparedStatement preparedStatement = connection.prepareStatement(query);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Usuario usuario = toUsuario(resultSet);
			usuarios.add(usuario);
		}

		return usuarios;

	}
	
	//este metodo se encarga de llamar al constructor con los resultados de la consulta
	public Usuario toUsuario(ResultSet resultSet) throws SQLException {
		String nombre = resultSet.getString("nombre");
		Double presupuesto = resultSet.getDouble("presupuesto");
		Double tiempo = resultSet.getDouble("tiempo");
		Integer tipo_atraccion_id = resultSet.getInt("tipo_atraccion_id");

		return new Usuario(nombre, presupuesto, tiempo,tipo_atraccion_id);

	}

}