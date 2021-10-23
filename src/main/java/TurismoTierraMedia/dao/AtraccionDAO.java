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
	
	//este metodo devuelve una lista con todos los usuarios...
		public List<Atraccion> findAll() throws SQLException {
			List<Atraccion> atracciones = new ArrayList<Atraccion>();
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "select * from atraccion";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Atraccion atraccion = toAtraccion(resultSet);
				atracciones.add(atraccion);
			}
			return atracciones;
		}
		
		//este metodo se encarga de llamar al constructor con los resultados de la consulta
		public Atraccion toAtraccion(ResultSet resultSet) throws SQLException {
			String nombre = resultSet.getString("nombre");
			Double costo = resultSet.getDouble("costo");
			Double tiempo = resultSet.getDouble("tiempo");
			Integer cupo = resultSet.getInt("cupo");
			Integer tipo_atraccion_id = resultSet.getInt("tipo_atraccion_id");

			return new Atraccion(nombre, costo, tiempo,cupo, tipo_atraccion_id);
		}

}
