package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.IngredientePersonalizado;
import vos.ProductoPersonalizado;

public class DAOTablaProductoPersonalizado {
	
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
	public DAOTablaProductoPersonalizado()
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
	public ArrayList<ProductoPersonalizado> darProductoPersonalizadovs() throws SQLException, Exception
	{
		ArrayList<ProductoPersonalizado> productoPersonalizado = new ArrayList<ProductoPersonalizado>();

		String sql = "SELECT * FROM Producto_PERSONALIZADO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			long idProductoPer = rs.getLong("IDPRODUCTOPER");
			long idProducto = rs.getLong("ID_PRODUCTO");
			productoPersonalizado.add(new ProductoPersonalizado(idProductoPer,idProducto));
		}
		return productoPersonalizado;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ProductoPersonalizado> buscarProductoPersonalizadoPorIdProductoPer(long id) throws SQLException, Exception
	{
		ArrayList<ProductoPersonalizado> productoPersonalizado = new ArrayList<ProductoPersonalizado>();

		String sql = "SELECT * FROM Producto_Personalizado WHERE IDPRODUCTOPER ='" + id +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			long  idPer = rs.getLong("IDPRODUCTOPER");
			long idProducto = rs.getLong("ID_PRODUCTO");
			productoPersonalizado.add(new ProductoPersonalizado(idPer,idProducto));
		}

		return productoPersonalizado;
	}


	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProductoPersonalizado(ProductoPersonalizado productoPersonalizado) throws SQLException, Exception
	{
		String sql = "INSERT INTO PRODUCTO_PERSONALIZADO VALUES (";
		sql += productoPersonalizado.getIdProductoPer() + ",";
		sql += productoPersonalizado.getIdProducto() + ")";
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
	public void updateProductoPersonalizado(ProductoPersonalizado productoPersonalizado) throws SQLException, Exception
	{
		
		String sql = "UPDATE Producto_Personalizado SET ";
		sql += "IDPRODUCTOPER='" + productoPersonalizado.getIdProductoPer() + "'";
		sql += "ID_PRODUCTO='" + productoPersonalizado.getIdProducto() + "'";
		sql += " WHERE IDPRODUCTOPER = '" + productoPersonalizado.getIdProductoPer()+"'";

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
	public void deleteProductoPersonalizado(ProductoPersonalizado productoPersonalizado) throws SQLException, Exception
	{
		String sql = "DELETE FROM Producto_Personalizado";
		sql += " WHERE IDPRODUCTOPER = '" + productoPersonalizado.getIdProductoPer()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
