package TurismoTierraMedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TurismoTierraMedia.Atraccion;
import TurismoTierraMedia.db.ConnectionProvider;

public class AtraccionDAO {
	
	//esto actualiza el cupo de una atraccion...
	public void actualizarAtraccion(Atraccion atra) throws SQLException {
		Connection connection = ConnectionProvider.getConnection();
		
		String query = "UPDATE atraccion set cupo = ? where id = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		if(atra.getCupo() > 0) {
			preparedStatement.setInt(1, atra.getCupo() - 1);
			preparedStatement.setInt(2, atra.getId());
			preparedStatement.executeUpdate();
		}
	}

	// Atracciones preferidas
	public List<Atraccion> atraccionesPreferidas(Integer id) throws SQLException {
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		Connection connection = ConnectionProvider.getConnection();
		
		
		//tenemos que tener en cuenta que no muestre atracciones que ya estan en una promo que compro
		//tenemos una tabla promocion tiene atraccion
		String query = "SELECT DISTINCT A.*, TA.nombre as tipo_atraccion "
				+ "FROM atraccion A INNER JOIN usuario U "
				+ "INNER JOIN tipo_de_atraccion TA "
				+ "WHERE A.tipo_atraccion_id = U.tipo_atraccion_id "
				+ "AND TA.id = A.tipo_atraccion_id "
				+ "AND A.costo <= U.presupuesto "
				+ "AND A.tiempo <= U.tiempo "
				+ "AND U.id = ? "
				+ "EXCEPT "
				+ "SELECT DISTINCT A.*, TA.nombre as tipo_atraccion "
				+ "FROM atraccion A "
				+ "INNER JOIN usuario U "
				+ "INNER JOIN tipo_de_atraccion TA "
				+ "INNER JOIN itinerario_tiene_atraccion ia "
				+ "INNER JOIN itinerario it "
				+ "WHERE U.id = it.usuario_id "
				+ "AND it.id=ia.itinerario_id  "
				+ "AND A.tipo_atraccion_id = U.tipo_atraccion_id "
				+ "AND TA.id = A.tipo_atraccion_id "
				+ "AND ia.atraccion_id = A.id "
				+ "AND U.id = ? ";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		preparedStatement.setInt(2, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Atraccion atraccion = toAtraccion(resultSet);
			atracciones.add(atraccion);
		}
		return atracciones;
	}

	// Atracciones No preferidas
	public List<Atraccion> atraccionesNoPreferidas(Integer id) throws SQLException {
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		Connection connection = ConnectionProvider.getConnection();

		//tenemos que tener en cuenta que no muestre atracciones que ya estan en una promo que compro
		//tenemos una tabla promocion tiene atraccion
		String query = "SELECT DISTINCT A.*, TA.nombre as tipo_atraccion "
				+ "FROM atraccion A "
				+ "INNER JOIN usuario U "
				+ "INNER JOIN tipo_de_atraccion TA "
				+ "INNER JOIN itinerario_tiene_atraccion IA "
				+ "WHERE A.tipo_atraccion_id <> U.tipo_atraccion_id "
				+ "AND TA.id = A.tipo_atraccion_id "
				+ "AND A.id <> IA.atraccion_id "
				+ "AND A.costo <= U.presupuesto "
				+ "AND A.tiempo <= U.tiempo "
				+ "AND U.id = ? "
				+ "EXCEPT  "
				+ "SELECT DISTINCT A.*, TA.nombre as tipo_atraccion "
				+ "FROM atraccion A INNER JOIN usuario U "
				+ "INNER JOIN tipo_de_atraccion TA "
				+ "INNER JOIN itinerario_tiene_atraccion ia "
				+ "INNER JOIN itinerario it "
				+ "WHERE U.id = it.usuario_id "
				+ "AND it.id = ia.itinerario_id "
				+ "AND TA.id = A.tipo_atraccion_id "
				+ "AND ia.atraccion_id = A.id "
				+ "AND U.id= ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		preparedStatement.setInt(2, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Atraccion atraccion = toAtraccion(resultSet);
			atracciones.add(atraccion);
		}
		return atracciones;
	}

	// devuelve la atraccion que corresponde al id pasado
	public Atraccion findById(Integer id) throws SQLException {
		Atraccion atraccion = null;

		Connection connection = ConnectionProvider.getConnection();

		String query = "SELECT a.id,a.nombre,a.costo,a.tiempo,a.cupo,ta.nombre as \"tipo_atraccion\" "
				+ "FROM atraccion a join tipo_de_atraccion ta " 
				+ "ON a.tipo_atraccion_id = ta.id " 
				+ "WHERE a.id = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			atraccion = toAtraccion(resultSet);
		}

		return atraccion;
	}

	// este metodo se encarga de crear un objeto con los resultados de la consulta
	public Atraccion toAtraccion(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		Double costo = resultSet.getDouble("costo");
		Double tiempo = resultSet.getDouble("tiempo");
		Integer cupo = resultSet.getInt("cupo");
		String tipo_atraccion = resultSet.getString("nombre");

		return new Atraccion(id, nombre, costo, tiempo, cupo, tipo_atraccion);
	}

}
