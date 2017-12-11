package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

public class DAOTablaRestaurante
{

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRestaurante()
	{
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos()
	{
		for(Object ob : recursos)
		{
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con)
	{
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexion a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Restaurante> darRestaurantes() throws SQLException, Exception
	{
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long id = rs.getLong("IDRESTAURANTE");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			String paginaWeb = rs.getString("PAGINA_WEB");
			String nombreRepresentante = rs.getString("NOMBRE_REPRESENTANTE");
			Long idZona = rs.getLong("IDZONA");
			restaurantes.add(new Restaurante(id, nombre, tipo, paginaWeb, nombreRepresentante, idZona));
		}
		return restaurantes;
	}

	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Restaurante buscarRestaurantePorNombre(String nombre) throws SQLException, Exception
	{
		Restaurante restaurante = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE NOMBRE ='" + nombre +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			Long id = rs.getLong("IDRESTAURANTE");
			String name = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			String paginaWeb = rs.getString("PAGINA_WEB");
			String nombreRepresentante = rs.getString("NOMBRE_REPRESENTANTE");
			Long idZona = rs.getLong("IDZONA");
			restaurante = new Restaurante(id, name, tipo, paginaWeb, nombreRepresentante, idZona);
		}

		return restaurante;
	}
	
	public Restaurante buscarRestaurantePorId(Long id) throws SQLException, Exception
	{
		Restaurante restaurante = null;
		
		String sql = "SELECT * FROM RESTAURANTE WHERE IDRESTAURANTE ='" + id +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			Long idd = rs.getLong("IDRESTAURANTE");
			String name = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			String paginaWeb = rs.getString("PAGINA_WEB");
			String nombreRepresentante = rs.getString("NOMBRE_REPRESENTANTE");
			Long idZona = rs.getLong("IDZONA");
			restaurante = new Restaurante(idd, name, tipo, paginaWeb, nombreRepresentante, idZona);
		}

		return restaurante;
	}

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRestaurante(Restaurante restaurante) throws SQLException, Exception
	{
		String sql = "INSERT INTO RESTAURANTE (IDRESTAURANTE,NOMBRE,TIPO,PAGINA_WEB,NOMBRE_REPRESENTANTE,IDZONA) VALUES (";
		sql += restaurante.getIdRestaurante() + ",'";
		sql += restaurante.getNombre() + "','";
		sql += restaurante.getTipo() + "','";
		sql += restaurante.getPaginaWeb() + "','";
		sql += restaurante.getNombreRepresentante() + "',";
		sql += restaurante.getIdZona() + ")";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza el video que entra como parametro en la base de datos.
	 * @param video - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRestaurante(Restaurante restaurante) throws SQLException, Exception
	{

		String sql = "UPDATE RESTAURANTE SET ";
		sql += "NOMBER" + restaurante.getNombre() + "','";
		sql += "TIPO='" + restaurante.getTipo() + "',";
		sql += "PAGINA_WEB='" + restaurante.getPaginaWeb() + "',";
		sql += "ID_REPRESENTANTE=" + restaurante.getNombreRepresentante() + ",";
		sql += "ID_ZONA=" + restaurante.getIdZona();
		sql += " WHERE IDRESTAURANTE = " + restaurante.getIdRestaurante();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el video que entra como parametro en la base de datos.
	 * @param video - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRestaurante(Restaurante restaurante) throws SQLException, Exception
	{
		String sql = "DELETE FROM RESTAURANTE";
		sql += " WHERE IDRESTAURANTE = " + restaurante.getIdRestaurante();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	
	public Rentabilidad rentabilidad(String cadena) throws SQLException, Exception
	{
		Rentabilidad restaurante = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE Cadena ='" + cadena +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

//		if(rs.next())
//		{
//			Long id = rs.getLong("IDRESTAURANTE");
//			String name = rs.getString("NOMBRE");
//			String tipo = rs.getString("TIPO");
//			String paginaWeb = rs.getString("PAGINA_WEB");
//			String nombreRepresentante = rs.getString("NOMBRE_REPRESENTANTE");
//			Long idZona = rs.getLong("IDZONA");
//			restaurante = new Restaurante(id, name, tipo, paginaWeb, nombreRepresentante, idZona);
//		}
		
		String cadenaR = rs.getString("Cadena");
		double ganancia = 0;
		
		restaurante = new Rentabilidad(cadenaR, ganancia);
		return restaurante;
	}
}
