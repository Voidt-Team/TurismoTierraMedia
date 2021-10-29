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
import TurismoTierraMedia.Usuario;
import TurismoTierraMedia.db.ConnectionProvider;


public class ItinerarioDAO {
	
	//Crea un registro en la tabla itinerario con el id de usuario que lo compro
	public void insertItinerario(Integer usuario_id) throws SQLException{
		Connection connection = ConnectionProvider.getConnection();
		String query = "INSERT or IGNORE INTO itinerario(\"usuario_id\") "
				+ "VALUES (?)";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1,usuario_id);
		preparedStatement.executeUpdate();
	}
	
	
	//Crea un registro en la tabla itinerario_tiene_atraccion
	public void insertItinerario(Atraccion at, Usuario u) throws SQLException {
		// primero se agrega el registro a itinerario
		insertItinerario(u.getId());
		// se llama a findbyid para obtener el itinerario generado para el usuario
		Itinerario i = findById(u.getId());
		// se crea un objeto usuarioDAO para acceder a los metodos de usuarioDAO y actualizar el usuario.itinerario_id
		UsuarioDAO udao = new UsuarioDAO();
		udao.actualizarUsuario(u, i.getId());

		// con esos datos mas la atraccion elegida se agrega el registro a
		// itinerario_tiene_atraccon
		Connection connection = ConnectionProvider.getConnection();
		String query = "INSERT INTO itinerario_tiene_atraccion (\"itinerario_id\",\"atraccion_id\") "
				+ "VALUES (?,?);";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, i.getId());
		preparedStatement.setInt(2, at.getId());
		preparedStatement.executeUpdate();
	}
	
	//Crea un registro en la tabla itinerario_tiene_promocion
	public void insertItinerario(Promocion pr, Usuario u) throws SQLException {
		// primero se agrega el registro a itinerario
		insertItinerario(u.getId());
		// se llama a findbyid para obtener el itinerario generado para el usuario
		Itinerario i = findById(u.getId());
		// se crea un objeto usuarioDAO para acceder a los metodos de usuarioDAO y actualizar el usuario.itinerario_id
		UsuarioDAO udao = new UsuarioDAO();
		udao.actualizarUsuario(u, i.getId());
				
		// con esos datos mas la promocion elegida se agrega el registro a
		// promocion
		Connection connection = ConnectionProvider.getConnection();
		String query = "INSERT INTO itinerario_tiene_promocion (\"itinerario_id\",\"promocion_id\") "
				+ "VALUES (?,?);";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, i.getId());
		preparedStatement.setInt(2, pr.getId());
		preparedStatement.executeUpdate();
	}
	

	//Devuelve un itinerario filtrado por id de usuario
	public Itinerario findById(Integer id) throws SQLException {
		Itinerario itinerario = null;
		Connection connection = ConnectionProvider.getConnection();
		String query = "SELECT * FROM itinerario i "
				+ "INNER JOIN itinerario_tiene_atraccion "
				+ "INNER JOIN itinerario_tiene_promocion "
				+ "WHERE i.usuario_id = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			itinerario = toItinerario(resultSet);
		}

		return itinerario;
	}

	//Se genera el objeto itinerario pero con listas vacias
	public Itinerario toItinerario(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		Integer usuario_id = resultSet.getInt("usuario_id");
		List<Atraccion> lista_atracciones = new ArrayList<Atraccion>();
		List<Promocion> lista_promociones = new ArrayList<Promocion>();
	
		return new Itinerario(id, usuario_id, lista_atracciones, lista_promociones);
	}
}
