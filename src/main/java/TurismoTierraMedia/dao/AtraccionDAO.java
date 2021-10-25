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
	
	//devuelve una lista con todas las atracciones que tienen cupo...
		public List<Atraccion> findAll() throws SQLException {
			List<Atraccion> atracciones = new ArrayList<Atraccion>();
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "select a.id,a.nombre,a.costo,a.tiempo,a.cupo, t.nombre as 'tipo atraccion' from atraccion a inner join tipo_de_atraccion t where a.tipo_atraccion_id=t.id and a.cupo>0 order by a.nombre";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Atraccion atraccion = toAtraccion(resultSet);
				atracciones.add(atraccion);
			}
			return atracciones;
		}
		
		//devuelve una lista con todas las atracciones sugeridas para el usuario...
		public List<Atraccion> findbyidUser(Integer id) throws SQLException {
			List<Atraccion> atracciones = new ArrayList<Atraccion>();
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "select e.id,e.nombre,e.costo,e.tiempo,e.cupo,e.tipoAtraccion from usuario  u join (SELECT a.*,ta.id,ta.nombre as \"tipoAtraccion\" from atraccion a join tipo_de_atraccion ta on a.tipo_atraccion_id=ta.id) e where u.tipo_atraccion_id=e.tipo_atraccion_id and e.costo<u.presupuesto and e.tiempo<u.tiempo and u.id=?";
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

			String query = "SELECT a.id,a.nombre,a.costo,a.tiempo,a.cupo,ta.nombre as \"tipo_atraccion\" from atraccion a join tipo_de_atraccion ta on a.tipo_atraccion_id=ta.id where a.id=?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);

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
