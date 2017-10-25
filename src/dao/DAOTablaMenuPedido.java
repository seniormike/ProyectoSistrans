package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.MenuPedido;

public class DAOTablaMenuPedido 
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
	public DAOTablaMenuPedido()
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
	public ArrayList<MenuPedido> darMenuPedidos() throws SQLException, Exception
	{
		ArrayList<MenuPedido> menuPedido = new ArrayList<MenuPedido>();

		String sql = "SELECT * FROM MENU_PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long IDPEDIDO = rs.getLong("IDPEDIDO");
			Long IDMENUPER = rs.getLong("IDMENUPER");
			int CANTIDAD = rs.getInt("CANTIDAD");
			menuPedido.add(new MenuPedido(IDPEDIDO,IDMENUPER,CANTIDAD));
		}
		return menuPedido;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public MenuPedido buscarMenuPedidoPorIdPedido(Long idPedido) throws SQLException, Exception
	{
		MenuPedido menuPedido = null;

		String sql = "SELECT * FROM MENU_PEDIDO WHERE IDPEDIDO ='" + idPedido +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			Long IDPEDIDO = rs.getLong("IDPEDIDO");
			Long IDMENUPER = rs.getLong("IDMENUPER");
			int CANTIDAD = rs.getInt("CANTIDAD");
			menuPedido = new MenuPedido(IDPEDIDO,IDMENUPER,CANTIDAD);
		}

		return menuPedido;
	}


	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMenuPedido(MenuPedido menuPedido) throws SQLException, Exception
	{
		String sql = "INSERT INTO MENU_PEDIDO VALUES ('";
		sql += menuPedido.getIdPedido() + "','";
		sql += menuPedido.getIdMenuPer() + "','";
		sql += menuPedido.getCantidad() + "')";
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
	public void updateMenuPedido(MenuPedido menuPedido) throws SQLException, Exception
	{
		String sql = "UPDATE MENU_PEDIDO SET ";
		sql += "IDPEDIDO='" + menuPedido.getIdPedido() + "'";
		sql += "IDMENUPER='" + menuPedido.getIdMenuPer()+"'";
		sql += "CANTIDAD='" + menuPedido.getCantidad()+"'";
		sql += " WHERE IDPEDIDO = '" + menuPedido.getIdPedido()+"' AND IDMENUPER = '"+ menuPedido.getIdMenuPer()+"'";
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
	public void deleteMenuPedido(MenuPedido menuPedido) throws SQLException, Exception
	{
		String sql = "DELETE FROM MENU_PEDIDO";
		sql += " WHERE IDPEDIDO = '" + menuPedido.getIdPedido()+"' AND IDMENUPER = '"+ menuPedido.getIdMenuPer()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
}
