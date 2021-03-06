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
			long id = rs.getLong("IDMENU");
			String medioPago = rs.getString("MEDIO_PAGO");
			double precio = rs.getDouble("PRECIO");
			Long idRestaurante = rs.getLong("IDRESTAURANTE");
			long entrada = rs.getLong("ENTRADA");
			long fuerte = rs.getLong("PLATO_FUERTE");
			long postre = rs.getLong("POSTRE");
			long bebida = rs.getLong("BEBIDA");
			long acompanamiento = rs.getLong("ACOMPANAMIENTO");
			menus.add(new Menu(id,medioPago,precio,idRestaurante,entrada,fuerte,postre,bebida,acompanamiento));
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

		String sql = "SELECT * FROM MENU WHERE IDMENU =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			long idd = rs.getLong("IDMENU");
			String medioPago = rs.getString("MEDIO_PAGO");
			double precio = rs.getDouble("PRECIO");
			Long idRestaurante = rs.getLong("IDRESTAURANTE");
			long entrada = rs.getLong("ENTRADA");
			long fuerte = rs.getLong("PLATO_FUERTE");
			long postre = rs.getLong("POSTRE");
			long bebida = rs.getLong("BEBIDA");
			long acompanamiento = rs.getLong("ACOMPANAMIENTO");
			menu = new Menu(idd,medioPago,precio,idRestaurante,entrada,fuerte,postre,bebida,acompanamiento);
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
		String sql = "INSERT INTO MENU (IDMENU, MEDIO_PAGO,PRECIO,IDRESTAURANTE,ENTRADA,PLATO_FUERTE,POSTRE,BEBIDA,ACOMPANAMIENTO) VALUES (";
		sql += menu.getId() + ",'";
		sql += menu.getMedioPago() + "',";
		sql += menu.getPrecio() + ",";
		sql += menu.getIdRestaurante() + ",";

		if(menu.getEntrada() != -1 || menu.getPlatoFuerte() != -1 || menu.getPostre() != -1 ||  menu.getBebida() != -1 || menu.getAcompanamiento() != -1)
		{
			if(menu.getEntrada() != -1)
			{
				if(darClasificacionProducto(menu.getEntrada()).equals("Entrada"))
				sql += menu.getEntrada() + ",";
				else
					throw new Exception("La Categoria de este producto no corresponde");
			}
			else {
				sql += "null,";
			}
			if(menu.getPlatoFuerte() != -1)
			{
				if(darClasificacionProducto(menu.getPlatoFuerte()).equals("Fuerte"))
				sql += menu.getPlatoFuerte() + ",";
				else
					throw new Exception("La Categoria de este producto no corresponde");
			}
			else {
				sql += "null,";
			}
			if(menu.getPostre() != -1)
			{
				if(darClasificacionProducto(menu.getPostre()).equals("Postre"))
				sql += menu.getPostre() + ",";
				else
					throw new Exception("La Categoria de este producto no corresponde");
			}
			else {
				sql += "null,";
			}
			if(menu.getBebida() != -1)
			{
				if(darClasificacionProducto(menu.getBebida()).equals("Bebida"))
				sql += menu.getBebida() + ",";
				else
					throw new Exception("La Categoria de este producto no corresponde");
			}
			else {
				sql += "null,";
			}
			if(menu.getAcompanamiento() != -1)
			{
				if(darClasificacionProducto(menu.getAcompanamiento()).equals("Acompanamiento"))
				sql += menu.getAcompanamiento() + ")";
				else
					throw new Exception("La Categoria de este producto no corresponde");
			}
			else {
				sql += "null)";
			}
		}

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * 
	 * @param id
	 * @return Clasificacion Producto
	 * @throws SQLException
	 * @throws Exception
	 */
	public String darClasificacionProducto(long id) throws SQLException, Exception
	{
		String sql = "SELECT * FROM PRODUCTO WHERE IDPRODUCTO =" + id ;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		return rs.getString("CLASIFICACION");
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
		sql += "IDRESTAURANTE='" + menu.getIdRestaurante() + "'";
		sql += "ENTRADA='" + menu.getEntrada() + "'";
		sql += "PLATO_FUERTE='" + menu.getPlatoFuerte() + "'";
		sql += "POSTRE='" + menu.getPostre() + "'";
		sql += "BEBIDA='" + menu.getBebida() + "'";
		sql += "ACOMPANAMIENTO='" + menu.getAcompanamiento() + "'";
		sql += " WHERE IDMENU = '" + menu.getId()+"'";

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
		sql += " WHERE IDMENU = '" + menu.getId()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
