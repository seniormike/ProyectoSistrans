package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.*;

public class DAOTablaProductoOfrecido
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
	public DAOTablaProductoOfrecido()
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
public ArrayList<ProductoOfrecido> buscarProductosOfrecidosPorIdRestaurante(Long idRestaurante) throws SQLException
{
	ArrayList<ProductoOfrecido> productosOfrecidosRestaurante = new ArrayList<ProductoOfrecido>();

	String sql = "SELECT * FROM PRODUCTO_OFRECIDO WHERE IDRESTAURANTE =" + idRestaurante;

	PreparedStatement prepStmt = conn.prepareStatement(sql);
	recursos.add(prepStmt);
	ResultSet rs = prepStmt.executeQuery();

	while (rs.next())
	{
		Long idProducto = rs.getLong("IDPRODUCTO");
		Long idRestaurantew = rs.getLong("IDRESTAURANTE");
		Integer cantidad = rs.getInt("CANTIDAD");
		Integer cantidadMax = rs.getInt("CANTIDADMAX");
		ProductoOfrecido nuevo = new ProductoOfrecido(idProducto,idRestaurantew, cantidad,cantidadMax);
		productosOfrecidosRestaurante.add(nuevo);
	}

	return productosOfrecidosRestaurante;
}
	/**
	 * Metodo que, usando la conexion a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ProductoOfrecido> darProductosOfrecidos() throws SQLException, Exception
	{
		ArrayList<ProductoOfrecido> productoOfrecido = new ArrayList<ProductoOfrecido>();

		String sql = "SELECT * FROM PRODUCTO_OFRECIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long idProducto = rs.getLong("IDPRODUCTO");
			Long idRestaurante = rs.getLong("IDRESTAURANTE");
			Integer cantidad = rs.getInt("CANTIDAD");
			Integer cantidadMaxima = rs.getInt("CANTIDADMAX");
			productoOfrecido.add(new ProductoOfrecido(idProducto,idRestaurante,cantidad,cantidadMaxima));
		}
		return productoOfrecido;
	}


	
	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProductoOfrecido(ProductoOfrecido productoOfrecido) throws SQLException, Exception
	{
		String sql = "INSERT INTO PRODUCTO_OFRECIDO (IDPRODUCTO,IDRESTAURANTE,CANTIDAD,CANTIDADMAX) VALUES (";
		sql += productoOfrecido.getIdProducto() + ",";
		sql += productoOfrecido.getIdRestaurante() + ",";
		sql += productoOfrecido.getCantidad() + ",";
		sql += productoOfrecido.getCantidadMaxima() + ")";
		
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
	public void updateProductoOfrecido(ProductoOfrecido productoOfrecido) throws SQLException, Exception
	{

		String sql = "UPDATE PRODUCTO_OFRECIDO SET ";
		sql += "CANTIDAD=" + productoOfrecido.getCantidad();
		sql += "CANTIDADMAX = " + productoOfrecido.getCantidadMaxima();
		sql += " WHERE IDPRODUCTO = " + productoOfrecido.getIdProducto() + " AND IDRESTAURANTE =" + productoOfrecido.getIdRestaurante();

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
	public void deleteProductoOfrecido(ProductoOfrecido productoOfrecido) throws SQLException, Exception
	{
		String sql = "DELETE FROM PRODUCTO_OFRECIDO";
		sql += " WHERE IDPRODUCTO = " + productoOfrecido.getIdProducto();
		sql += "AND IDRESTAURANTE = " + productoOfrecido.getIdRestaurante();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}