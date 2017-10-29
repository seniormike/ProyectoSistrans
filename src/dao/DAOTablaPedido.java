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
public class DAOTablaPedido
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
	public DAOTablaPedido()
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
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexiÃ³n que entra como parametro.
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
	public ArrayList<Pedido> darPedidos() throws SQLException, Exception
	{
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long id = rs.getLong("IDPEDIDO");
			String fecha = ""+rs.getDate("FECHA");
			Double valorTotal = rs.getDouble("VALOR_TOTAL");
			Long idUsuario = rs.getLong("IDUSUARIO");
			String estado = rs.getString("ESTADO");
		
			pedidos.add(new Pedido(id,fecha,valorTotal,idUsuario,estado));
		}
		return pedidos;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Pedido buscarPedidoPorId(Long id) throws SQLException, Exception
	{
		Pedido pedido = null;

		String sql = "SELECT * FROM PEDIDO WHERE IDPEDIDO =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long id1 = rs.getLong("IDPEDIDO");
			String fecha = ""+rs.getDate("FECHA");
			Double valorTotal = rs.getDouble("VALOR_TOTAL");
			Long idUsuario = rs.getLong("IDUSUARIO");
			String estado = rs.getString("ESTADO");
			pedido = new Pedido(id1,fecha,valorTotal,idUsuario,estado);
		}

		return pedido;
	}
	

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedido(Pedido pedido) throws SQLException, Exception
	{
		String[] fech = pedido.getFecha().split("-");
		String dia = fech[0];
		String mes = fech[1];
		String año = fech[2];
		String fecha = dia+"/"+mes+"/"+año;
		
		String sql = "INSERT INTO PEDIDO VALUES (";
		sql += pedido.getId() + ",'";
		sql += fecha + "',";
		sql += pedido.getValorTotal() + ",";
		sql += pedido.getIdUsuario() + ",";
		sql += pedido.getEstado() + ")";
		
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
	public void updatePedido(Pedido pedido) throws SQLException, Exception
	{
		String[] fech = pedido.getFecha().split("-");
		String dia = fech[0];
		String mes = fech[1];
		String año = fech[2];
		String fecha = dia+"/"+mes+"/"+año;
		
		String sql = "UPDATE PEDIDO SET ";
		sql += "FECHA='" + fecha + "',";
		sql += "VALOR_TOTAL=" + pedido.getValorTotal() + ",";
		sql += "IDUSUARIO=" + pedido.getIdUsuario() + ",";
		sql += "ESTADO=" + pedido.getEstado();
		sql += " WHERE IDPEDIDO =" + pedido.getId();

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
	public void deletePedido(Pedido pedido) throws SQLException, Exception
	{
		String sql = "DELETE FROM PEDIDO";
		sql += " WHERE IDPEDIDO = " + pedido.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
