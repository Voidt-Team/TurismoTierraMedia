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
	
	// este metodo devuelve una lista con todas las promociones
	public List<Promocion> findAllbyIdUser() throws SQLException {
		List<Promocion> promociones = new ArrayList<Promocion>();
		Connection connection = ConnectionProvider.getConnection();

		String query = "SELECT * from usuario u join atraccion a join promocion_tiene_atraccion pa join promocion p WHERE u.tipo_atraccion_id = a.id and p.id = pa.promocion_id and pa.atraccion_id = a.id and u.nombre=\"Bilbo\"";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Promocion promocion = toPromocion(resultSet);
			promociones.add(promocion);
		}
		return promociones;
	}

	
	// devuelve una lista con las atracciones que contiene una promocion solicitada
	public List<Atraccion> findAllAttractionsByPromoId(Integer id) throws SQLException {
		List<Atraccion> atracciones = new ArrayList<Atraccion>();
		AtraccionDAO atraccionDAO = new AtraccionDAO();
		Connection connection = ConnectionProvider.getConnection();

		//esta consulta trae la lista de atracciones de una promo buscada por id...
		String query = "SELECT z.id,z.nombre,z.costo,z.tiempo,z.cupo,z.TipoAtraccion FROM (select A.*,ta.nombre as \"TipoAtraccion\" from atraccion A join tipo_de_atraccion ta on A.tipo_atraccion_id=ta.id) z INNER JOIN  promocion_tiene_atraccion PA INNER JOIN promocion P ON PA.promocion_id = P.id and z.id = PA.atraccion_id and P.id=?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Atraccion atraccion = atraccionDAO.toAtraccion(resultSet);//devuelve una atraccion...
			//Atraccion atraccion = toAtraccionesPromo(resultSet);
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
			//con finbyid obtengo mi atraccion y le paso por parametro el id 
			//que traigo de la base de datos
			Atraccion extra = atraccionExtraDAO.findById(axb);
			
			Double porcentual = resultSet.getDouble("porcentual");
			
			// Aca le asignamos el resultado de findAllAttractionsByPromoId a la lista de
			// atracciones de la promo que se esta construyendo
			lista_atracciones = findAllAttractionsByPromoId(id);
			return new Promocion(id, nombre, absoluta, extra, porcentual, lista_atracciones);
		
		}
		
}
