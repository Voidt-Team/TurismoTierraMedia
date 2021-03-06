package TurismoTierraMedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import TurismoTierraMedia.Atraccion;
import TurismoTierraMedia.Promocion;
import TurismoTierraMedia.db.ConnectionProvider;

public class PromocionDAO {
	
	//Promociones preferidas
	public List<Promocion> promocionesPreferidas(Integer id) throws SQLException {
		List<Promocion> lpromociones = new ArrayList<Promocion>();
		Connection connection = ConnectionProvider.getConnection();

		//aca nos faltaria filtrar que no muestre las promociones que tienen una atraccion que ya se compro
		//tambien filtrar teniendo en cuenta el presupuesto y tiempo del usuario
		String query = "SELECT DISTINCT p.* FROM usuario u "
				+ "JOIN atraccion a "
				+ "JOIN promocion_tiene_atraccion pa "
				+ "JOIN promocion p "
				+ "WHERE u.tipo_atraccion_id = a.tipo_atraccion_id "
				+ "AND p.id = pa.promocion_id "
				+ "AND pa.atraccion_id = a.id "
				+ "AND a.cupo > 0 "
				+ "AND u.id = ? "
				+ "EXCEPT "
				+ "SELECT p.* from usuario u "
				+ "JOIN itinerario i "
				+ "JOIN itinerario_tiene_promocion ip "
				+ "JOIN promocion p "
				+ "WHERE p.id = ip.promocion_id "
				+ "AND u.id = ? "
				+ "AND u.id = i.usuario_id "
				+ "AND i.id = ip.itinerario_id";
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		preparedStatement.setInt(2, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Promocion promocion = toPromocion(resultSet);
			lpromociones.add(promocion);
		}
		return lpromociones;
	}
	
	//Promociones No Preferidas
	public List<Promocion> promocionesNoPreferidas(Integer id) throws SQLException {
		List<Promocion> lpromociones = new ArrayList<Promocion>();
		Connection connection = ConnectionProvider.getConnection();

		//aca nos faltaria filtrar que no muestre las promociones que tienen una atraccion que ya se compro
		//tambien filtrar teniendo en cuenta el presupuesto y tiempo del usuario
		String query = "SELECT DISTINCT p.* FROM usuario u "
				+ "JOIN atraccion a "
				+ "JOIN promocion_tiene_atraccion pa "
				+ "JOIN promocion p "
				+ "WHERE u.tipo_atraccion_id <> a.tipo_atraccion_id "
				+ "AND p.id = pa.promocion_id "
				+ "AND pa.atraccion_id = a.id "
				+ "AND a.cupo > 0 "
				+ "AND u.id = ? "
				+ "EXCEPT "
				+ "SELECT p.* FROM usuario u "
				+ "JOIN itinerario i "
				+ "JOIN itinerario_tiene_promocion ip "
				+ "JOIN promocion p "
				+ "WHERE p.id = ip.promocion_id "
				+ "AND u.id = ? "
				+ "AND u.id = i.usuario_id "
				+ "AND i.id = ip.itinerario_id";
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		preparedStatement.setInt(2, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Promocion promocion = toPromocion(resultSet);
			lpromociones.add(promocion);
		}
		return lpromociones;
	}
	//devuelve una promocion correspondiente al id pasado
	public Promocion findById(Integer id) throws SQLException {
		Promocion promo = null;

		Connection connection = ConnectionProvider.getConnection();

		String query = "select * from promocion WHERE id=?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			promo = toPromocion(resultSet);
		}

		return promo;
	}
	
	// devuelve una lista con las atracciones que contiene una promocion solicitada
	public List<Atraccion> findAllAttractionsByPromoId(Integer id) throws SQLException {
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		AtraccionDAO atraccionDAO = new AtraccionDAO();
		Connection connection = ConnectionProvider.getConnection();

		//esta consulta trae la lista de atracciones de una promo buscada por id...
		String query = "SELECT z.id,z.nombre,z.costo,z.tiempo,z.cupo,z.TipoAtraccion "
				+ "FROM (select A.*,ta.nombre as \"TipoAtraccion\" "
				+ "FROM atraccion A join tipo_de_atraccion ta on A.tipo_atraccion_id = ta.id) z "
				+ "INNER JOIN  promocion_tiene_atraccion PA "
				+ "INNER JOIN promocion P "
				+ "ON PA.promocion_id = P.id AND z.id = PA.atraccion_id and P.id = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Atraccion atraccion = atraccionDAO.toAtraccion(resultSet);//devuelve una atraccion...
			atracciones.add(atraccion);
		}
		return atracciones;
	}

	// este metodo se encarga de llamar al constructor con los resultados de la consulta
		public Promocion toPromocion(ResultSet resultSet) throws SQLException {
			List<Atraccion> lista_atracciones = new ArrayList<Atraccion>();
			AtraccionDAO atraccionExtraDAO = new AtraccionDAO();

			Integer id = resultSet.getInt("id");
			String nombre = resultSet.getString("nombre");
			Integer absoluta = resultSet.getInt("absoluta");
			Integer axb = resultSet.getInt("axb");
			
			//la promo si es axb en el objeto necesitamos que nos guarde la atraccion
			//entonces promocion en su campo axb va a ser de tipo Atraccion
			//con finbyid se obtiene la atraccion y se le pasa por parametro el id 
			//que se trae de la base de datos
			Atraccion extra = atraccionExtraDAO.findById(axb);
			
			Double porcentual = resultSet.getDouble("porcentual");
			
			// Aca le asignamos el resultado de findAllAttractionsByPromoId a la lista de
			// atracciones de la promo que se esta construyendo
			lista_atracciones = findAllAttractionsByPromoId(id);
			return new Promocion(id, nombre, absoluta, extra, porcentual,axb, lista_atracciones);
		
		}
		
}
