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

	// este metodo devuelve una lista con todos los usuarios y sus tipo de atraccion...
	public List<Usuario> findAll() throws SQLException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Connection connection = ConnectionProvider.getConnection();

		String query = "select u.id,u.nombre,u.presupuesto,u.tiempo, t.nombre as 'tipo atraccion preferida' from usuario u inner join tipo_de_atraccion t where u.tipo_atraccion_id=t.id order by u.nombre";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Usuario usuario = toUsuario(resultSet);
			usuarios.add(usuario);
		}
		return usuarios;
	}

	
	public void insert(Usuario usu) throws SQLException {
		// metodo no utilizable...
	}

	public void update(Usuario usu) throws SQLException {
		// no se si vamos a usar este... si uno que actualice el itinerario asignado
	}

	public void delete(Usuario usu) throws SQLException {
		// borra un usuario
		// creo que para este caso de uso de base de datos, no hace falta borrarlo al
		// usuario, sino que en el select
		// traiga los que tienen tiempo/costo mayor al min de tiempo y costo
		// Solo lo borrabamos del anterior para que no los muestre en el listado
	}

	// este metodo se encarga de llamar al constructor con los resultados de la
	// consulta
	public Usuario toUsuario(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		Double presupuesto = resultSet.getDouble("presupuesto");
		Double tiempo = resultSet.getDouble("tiempo");
		Double latitud = resultSet.getDouble("latitud");//se consulto con roberto si esto es necesario
		Double longitud = resultSet.getDouble("longitud");//se consulto con roberto si esto es necesario
		String atraccion_preferida = resultSet.getString("tipo atraccion preferida");
		return new Usuario(id, nombre, presupuesto, tiempo, latitud, longitud, atraccion_preferida);
	}

}
