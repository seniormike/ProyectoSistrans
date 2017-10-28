package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 */
public class DAOTablaProducto
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
	public DAOTablaProducto()
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
	public ArrayList<Producto> darProductos() throws SQLException, Exception
	{
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long id = rs.getLong("IDPRODUCTO");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String idescripcion = rs.getString("IDESCRIPTION");
			Integer tiempoPreparacion = rs.getInt("TIEMPO_PREPARACION");
			Double costoProduccion = rs.getDouble("COSTO_PRODUCCION");
			Double precioVenta = rs.getDouble("PRECIO_VENTA");
			String disponible = rs.getString("DISPONIBLE");
			String clasificacion = rs.getString("CLASIFICACION");
			productos.add(new Producto(id,nombre, descripcion,idescripcion,tiempoPreparacion, costoProduccion, precioVenta, disponible, clasificacion));
		}
		return productos;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Producto buscarProductoPorNombre(String nombre) throws SQLException, Exception
	{
		Producto producto = null;

		String sql = "SELECT * FROM PRODUCTO WHERE NOMBRE ='" + nombre +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long id = rs.getLong("ID");
			String nombre1 = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String idescripcion = rs.getString("IDESCRIPTION");
			Integer tiempoPreparacion = rs.getInt("TIEMPO_PREPARACION");
			Double costoProduccion = rs.getDouble("COSTO_PRODUCCION");
			Double precioVenta = rs.getDouble("PRECIOVENTA");
			String disponible = rs.getString("DISPONIBLE");
			String clasificacion = rs.getString("CLASIFICACION");
			producto = new Producto(id,nombre1, descripcion,idescripcion,tiempoPreparacion, costoProduccion, precioVenta, disponible, clasificacion);
		}

		return producto;
	}

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProducto(Producto producto) throws SQLException, Exception
	{

		String sql = "INSERT INTO PRODUCTO (IDPRODUCTO,NOMBRE,DESCRIPCION,IDESCRIPTION,TIEMPO_PREPARACION,COSTO_PRODUCCION,PRECIO_VENTA,DISPONIBLE,CLASIFICACION,IDRESTAURANTE) VALUES (";
		sql += producto.getId() + ",'"; 
		sql += producto.getNombre() + "','";
		sql += producto.getDescripcion() + "','";
		sql += producto.getIdescripcion() + "',";
		sql += producto.getTiempoPreparacion() + ",";
		sql += producto.getCostoProduccion() + ",";
		sql += producto.getPrecioVenta() + ",'";
		sql += producto.getDisponible() + "','";
		sql += producto.getClasificacion() + "')";
		
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
	public void updateProducto(Producto producto) throws SQLException, Exception
	{
		String sql = "UPDATE PRODUCTO SET ";
		sql += "NOMBRE='" + producto.getNombre() + "',";
		sql += "DESCRIPCION='" + producto.getDescripcion() + "',";
		sql += "IDESCRIPTION='" + producto.getIdescripcion() + "',";
		sql += "TIEMPO_PREPARACION=" + producto.getTiempoPreparacion() + ",";
		sql += "COSTO_PRODUCCION=" + producto.getCostoProduccion() + ",";
		sql += "PRECIO_VENTA=" + producto.getPrecioVenta() + ",";
		sql += "DISPONIBLE='" + producto.getDisponible() + "','";
		sql += "CLASIFICACION='" + producto.getClasificacion()+ "'";
		sql += " WHERE IDPRODUCTO = " + producto.getId();

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
	public void deleteProducto(Producto producto) throws SQLException, Exception
	{
		String sql = "DELETE FROM PRODUCTO";
		sql += " WHERE IDPRODUCTO = " + producto.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}