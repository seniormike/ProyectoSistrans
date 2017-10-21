package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Menu;

public class DAOTablaMenu 
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
	 * 
	 */
	public DAOTablaMenu()
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
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<Menu> darMenus() throws SQLException, Exception
	{
		ArrayList<Menu> menus = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENU";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			long id = rs.getLong("ID");
			String medioPago = rs.getString("MEDIO_PAGO");
			double precio = rs.getDouble("PRECIO");
			String nomRestaurante = rs.getString("NOMBRE_RESTAURANTE");
			long entrada = rs.getLong("ENTRADA");
			long fuerte = rs.getLong("PLATO_FUERTE");
			long postre = rs.getLong("POSTRE");
			long bebida = rs.getLong("BEBIDA");
			long acompanamiento = rs.getLong("ACOMPANAMIENTO");
			menus.add(new Menu(id,medioPago,precio,nomRestaurante,entrada,fuerte,postre,bebida,acompanamiento));
		}
		return menus;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Menu buscarMenuPorId(long id) throws SQLException, Exception
	{
		Menu menu = null;

		String sql = "SELECT * FROM MENU WHERE ID ='" + id +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			long idd = rs.getLong("ID");
			String medioPago = rs.getString("MEDIO_PAGO");
			double precio = rs.getDouble("PRECIO");
			String nomRestaurante = rs.getString("NOMBRE_RESTAURANTE");
			long entrada = rs.getLong("ENTRADA");
			long fuerte = rs.getLong("PLATO_FUERTE");
			long postre = rs.getLong("POSTRE");
			long bebida = rs.getLong("BEBIDA");
			long acompanamiento = rs.getLong("ACOMPANAMIENTO");
			menu = new Menu(idd,medioPago,precio,nomRestaurante,entrada,fuerte,postre,bebida,acompanamiento);
		}

		return menu;
	}


	/**
	 * 
	 * @param menu
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addMenu(Menu menu) throws SQLException, Exception
	{
		String sql = "INSERT INTO MENU VALUES ('";
		sql += menu.getId() + "','";
		sql += menu.getMedioPago() + "','";
		sql += menu.getPrecio() + "','";
		sql += menu.getNomRestaurante() + "','";
		sql += menu.getEntrada() + "','";
		sql += menu.getPlatoFuerte() + "','";
		sql += menu.getPostre() + "','";
		sql += menu.getBebida() + "','";
		sql += menu.getAcompanamiento() + "')";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * 
	 * @param menu
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateMenu(Menu menu) throws SQLException, Exception
	{		
		String sql = "UPDATE MENU SET ";
		sql += "MEDIO_PAGO='" + menu.getMedioPago() + "'";
		sql += "PRECIO='" + menu.getPrecio() + "'";
		sql += "MEDIO_PAGO='" + menu.getMedioPago() + "'";
		sql += "NOMBRE_RESTAURANTE='" + menu.getNomRestaurante() + "'";
		sql += "ENTRADA='" + menu.getEntrada() + "'";
		sql += "PLATO_FUERTE='" + menu.getPlatoFuerte() + "'";
		sql += "POSTRE='" + menu.getPostre() + "'";
		sql += "BEBIDA='" + menu.getBebida() + "'";
		sql += "ACOMPANAMIENTO='" + menu.getAcompanamiento() + "'";
		sql += " WHERE ID = '" + menu.getId()+"'";

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
	public void deletemenu(Menu menu) throws SQLException, Exception
	{
		String sql = "DELETE FROM MENU";
		sql += " WHERE ID = '" + menu.getId()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
