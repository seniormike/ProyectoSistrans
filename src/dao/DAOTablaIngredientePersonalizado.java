package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.IngredientePersonalizado;

public class DAOTablaIngredientePersonalizado
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
	public DAOTablaIngredientePersonalizado()
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
				} catch (Exception ex)
				{
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
	public ArrayList<IngredientePersonalizado> darIngredienteProductoPersonalizadovs() throws SQLException, Exception
	{
		ArrayList<IngredientePersonalizado> ingredienteProductoPersonalizado = new ArrayList<IngredientePersonalizado>();

		String sql = "SELECT * FROM INGREDIENTE_PERSONALIZADO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			String nombre = rs.getString("NOMBRE_INGREDIENTE");
			long idProducto = rs.getLong("ID_PRODUCTO");
		}
		return ingredienteProductoPersonalizado;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<IngredientePersonalizado> buscarIngredienteProductoPersonalizadoPorIdProducto(long id) throws SQLException, Exception
	{
		ArrayList<IngredientePersonalizado> ingredienteProducto = new ArrayList<IngredientePersonalizado>();

		String sql = "SELECT * FROM INGREDIENTE_PERSONALIZADO WHERE ID_PRODUCTO ='" + id +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			String name = rs.getString("NOMBRE_INGREDIENTE");
			long idProducto = rs.getLong("ID_PRODUCTO");
			ingredienteProducto.add(new IngredientePersonalizado(name,idProducto));
		}

		return ingredienteProducto;
	}


	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addIngredienteProductoPersonalizado(IngredientePersonalizado ingredienteProducto) throws SQLException, Exception
	{
		String sql = "INSERT INTO INGREDIENTE_PERSONALIZADO VALUES ('";
		sql += ingredienteProducto.getNombreIngrediente() + "','";
		sql += ingredienteProducto.getIdProducto() + "','";
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
	public void updateIngredienteProductoPersonalizado(IngredientePersonalizado ingredienteProducto) throws SQLException, Exception
	{
		
		String sql = "UPDATE INGREDIENTE_PERSONALIZADO SET ";
		sql += "NOMBRE_INGREDIENTE='" + ingredienteProducto.getNombreIngrediente() + "'";
		sql += "ID_PRODUCTO='" + ingredienteProducto.getIdProducto() + "'";
		sql += " WHERE NOMBRE_INGREDIENTE = '" + ingredienteProducto.getNombreIngrediente()+"' AND ID_PRODUCTO = '" + ingredienteProducto.getIdProducto()+"'";

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
	public void deleteIngredienteProductoPersonalizado(IngredientePersonalizado ingredienteProducto) throws SQLException, Exception
	{
		String sql = "DELETE FROM INGREDIENTE_PERSONALIZADO";
		sql += " WHERE NOMBRE_INGREDIENTE = '" + ingredienteProducto.getNombreIngrediente()+"' AND ID_PRODUCTO = '" + ingredienteProducto.getIdProducto()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
