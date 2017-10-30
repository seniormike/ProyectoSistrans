package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.*;

public class DAOTablaProductoEquivalente
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
	public DAOTablaProductoEquivalente()
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
	public ArrayList<ProductoEquivalente> darProductosEquivalentes() throws SQLException, Exception
	{
		ArrayList<ProductoEquivalente> productoEquivalentes = new ArrayList<ProductoEquivalente>();

		String sql = "SELECT * FROM PRODUCTO_EQUIVALENTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long idProducto = rs.getLong("IDPRODUCTO");
			Long idRestaurante= rs.getLong("IDRESTAURANTE");
			Long idProdEquivalente = rs.getLong("IDPRODEQUIVALENTE");
			productoEquivalentes.add(new ProductoEquivalente(idProducto, idRestaurante,idProdEquivalente));
		}
		return productoEquivalentes;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public List<ProductoEquivalente> buscarProductoEquivalentePorId(Long idProductoEquivalente) throws SQLException, Exception
	{
		List<ProductoEquivalente> productoEquivalente = null;

		String sql = "SELECT * FROM PRODUCTO_EQUIVALENTE WHERE IDPRODEQUIVALENTE =" + idProductoEquivalente;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			Long idProducto = rs.getLong("IDPRODUCTO");
			Long idRestaurante= rs.getLong("IDRESTAURANTE");
			Long idProdEquivalente = rs.getLong("IDPRODEQUIVALENTE");
			productoEquivalente.add(new ProductoEquivalente(idProducto, idRestaurante,idProdEquivalente));
		}

		return productoEquivalente;
	}

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProductoEquivalente(ProductoEquivalente productoEquiv) throws SQLException, Exception
	{
		String sql = "INSERT INTO PRODUCTO_EQUIVALENTE (IDPRODUCTO,IDRESTAURANTE,IDPRODEQUIVALENTE) VALUES (";
		sql += productoEquiv.getIdProducto() + ",";
		sql += productoEquiv.getIdRestaurante() + ",";
		sql += productoEquiv.getIdProdEquivalente() + ")";
		
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
	public void updateProductoEquivalente(ProductoEquivalente productoEquivalente) throws SQLException, Exception
	{

		String sql = "UPDATE PRODUCTO_EQUIVALENTE SET ";
		sql += "IDPRODEQUIVALENTE=" + productoEquivalente.getIdProdEquivalente();
		sql += " WHERE IDPRODUCTO = " + productoEquivalente.getIdProducto() + "AND ";
		sql += " IDRESTAURANTE = " + productoEquivalente.getIdRestaurante(); 

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
	public void deleteProductoEquivalente(ProductoEquivalente productoEquivalente) throws SQLException, Exception
	{
		String sql = "DELETE FROM PRODUCTO_EQUIVALENTE";
		sql += " WHERE IDPRODUCTO = " + productoEquivalente.getIdProducto() + "AND ";
		sql += " IDRESTAURANTE = " + productoEquivalente.getIdRestaurante();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}