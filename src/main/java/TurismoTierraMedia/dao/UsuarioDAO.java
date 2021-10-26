package TurismoTierraMedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TurismoTierraMedia.db.ConnectionProvider;
import TurismoTierraMedia.Atraccion;
import TurismoTierraMedia.Usuario;

public class UsuarioDAO {

	// este metodo devuelve una lista con todos los usuarios que tienen presupuesto y tiempo para hacer atracciones...
	public List<Usuario> findAll() throws SQLException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Connection connection = ConnectionProvider.getConnection();

		String query = "SELECT u.id,u.nombre,u.presupuesto,u.tiempo, t.nombre as 'tipo atraccion preferida' "
				+ "FROM usuario u INNER JOIN tipo_de_atraccion t "
				+ "WHERE u.tipo_atraccion_id = t.id "
				+ "AND u.tiempo >= (select min(tiempo) from atraccion) "
				+ "AND u.presupuesto >= (select min(costo) from atraccion) "
				+ "ORDER BY u.id";
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Usuario usuario = toUsuario(resultSet);
			usuarios.add(usuario);
		}
		return usuarios;
	}
	
	public Usuario findById(Integer id) throws SQLException {
		Usuario usuario = null;

		Connection connection = ConnectionProvider.getConnection();

		String query = "select * from Usuario where id=?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		// eye! empieza en 1 el resultset
		if (resultSet.next()) {
			// aca, el resultset, esta posicionado en el primer resultado

			// aca procesamos el resultset
			usuario = toUsuario(resultSet);

		}
		return usuario;
	}

	
	// este metodo se encarga de llamar al constructor con los resultados de la consulta
	public Usuario toUsuario(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		Double presupuesto = resultSet.getDouble("presupuesto");
		Double tiempo = resultSet.getDouble("tiempo");
		String atraccion_preferida = resultSet.getString("tipo atraccion preferida");
		
		return new Usuario(id, nombre, presupuesto, tiempo, atraccion_preferida);
	}
	
	

}
