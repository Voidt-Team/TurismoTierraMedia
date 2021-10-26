package TurismoTierraMedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TurismoTierraMedia.Atraccion;
import TurismoTierraMedia.Usuario;
import TurismoTierraMedia.db.ConnectionProvider;


public class AtraccionDAO {
	
		//Atracciones preferidas
		public List<Atraccion> atraccionesPreferidas(Integer id) throws SQLException {
			List<Atraccion> atracciones = new ArrayList<Atraccion>();
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "SELECT A.*, ta.nombre as\"tipo_atraccion\" "
					+ "FROM atraccion A INNER JOIN usuario U inner join tipo_de_atraccion ta "
					+ "WHERE A.tipo_atraccion_id = U.tipo_atraccion_id and ta.id = a.tipo_atraccion_id "
					+ "AND A.costo < U.presupuesto and A.tiempo < U.tiempo and U.id = ?";
			
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Atraccion atraccion = toAtraccion(resultSet);
				atracciones.add(atraccion);
			}
			return atracciones;
		}
		
		//Atracciones No preferidas
		public List<Atraccion> atraccionesNoPreferidas(Integer id) throws SQLException {
			List<Atraccion> atracciones = new ArrayList<Atraccion>();
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "SELECT A.*, ta.nombre as tipo_atraccion "
					+ "FROM atraccion A INNER JOIN usuario U inner join tipo_de_atraccion ta "
					+ "WHERE A.tipo_atraccion_id <> U.tipo_atraccion_id and ta.id = a.tipo_atraccion_id "
					+ "AND A.costo < U.presupuesto and A.tiempo < U.tiempo and U.id = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Atraccion atraccion = toAtraccion(resultSet);
				atracciones.add(atraccion);
			}
			return atracciones;
		}
		
		
		//devuelve la atraccion que corresponde al id pasado
		public Atraccion findById(Integer id) throws SQLException {
			Atraccion atraccion = null;

			Connection connection = ConnectionProvider.getConnection();
			String query = "SELECT a.id,a.nombre,a.costo,a.tiempo,a.cupo,ta.nombre as \"tipo_atraccion\" "
					+ "FROM atraccion a join tipo_de_atraccion ta "
					+ "ON a.tipo_atraccion_id = ta.id where a.id = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				atraccion = toAtraccion(resultSet);
			}
			return atraccion;
		}
		
		//este metodo se encarga de crear un objeto con los resultados de la consulta
		public Atraccion toAtraccion(ResultSet resultSet) throws SQLException {
			Integer id = resultSet.getInt("id");
			String nombre = resultSet.getString("nombre");
			Double costo = resultSet.getDouble("costo");
			Double tiempo = resultSet.getDouble("tiempo");
			Integer cupo = resultSet.getInt("cupo");
			String tipo_atraccion= resultSet.getString("tipo_atraccion");

			return new Atraccion(id, nombre, costo, tiempo,cupo, tipo_atraccion);
		}

		


}
