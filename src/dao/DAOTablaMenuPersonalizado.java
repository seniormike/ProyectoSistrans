package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.MenuPersonalizado;

public class DAOTablaMenuPersonalizado {
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
	public DAOTablaMenuPersonalizado()
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
	public ArrayList<MenuPersonalizado> darMenuPersonalizados() throws SQLException, Exception
	{
		ArrayList<MenuPersonalizado> menuPersonalizados = new ArrayList<MenuPersonalizado>();

		String sql = "SELECT * FROM MENU_PERSONALIZADO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			long id = rs.getLong("IDMENUPER");
			double precio = rs.getDouble("PRECIO");
			long entrada = rs.getLong("ENTRADA");
			long fuerte = rs.getLong("PLATO_FUERTE");
			long postre = rs.getLong("POSTRE");
			long bebida = rs.getLong("BEBIDA");
			long acompanamiento = rs.getLong("ACOMPANAMIENTO");
			long idMenu = rs.getLong("IDMENU");
			menuPersonalizados.add(new MenuPersonalizado(id,precio,entrada,fuerte,postre,bebida,acompanamiento,idMenu));
		}
		return menuPersonalizados;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public MenuPersonalizado buscarMenuPersonalizadoPorId(long id) throws SQLException, Exception
	{
		MenuPersonalizado menuPersonalizado = null;

		String sql = "SELECT * FROM MENU_Personalizado WHERE IDMENUPER ='" + id +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			long idd = rs.getLong("IDMENUPER");
			double precio = rs.getDouble("PRECIO");
			long entrada = rs.getLong("ENTRADA");
			long fuerte = rs.getLong("PLATO_FUERTE");
			long postre = rs.getLong("POSTRE");
			long bebida = rs.getLong("BEBIDA");
			long acompanamiento = rs.getLong("ACOMPANAMIENTO");
			long idMenu = rs.getLong("IDMENU");
			menuPersonalizado = new MenuPersonalizado(idd,precio,entrada,fuerte,postre,bebida,acompanamiento,idMenu);
		}

		return menuPersonalizado;
	}


	/**
	 * 
	 * @param menu
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addMenuPersonalizado(MenuPersonalizado menuPersonalizado) throws SQLException, Exception
	{
		String sql = "INSERT INTO MENU_PERSONALIZADO (IDMENUPER,PRECIO,ENTRADA,PLATO_FUERTE,POSTRE,BEBIDA,ACOMPANAMIENTO,IDMENU) VALUES (";
		sql += menuPersonalizado.getId() + ",";
		sql += menuPersonalizado.getPrecio() + ",";

		if(menuPersonalizado.getEntrada() != -1 || menuPersonalizado.getPlatoFuerte() != -1 || menuPersonalizado.getPostre() != -1 ||  menuPersonalizado.getBebida() != -1 || menuPersonalizado.getAcompanamiento() != -1)
		{

			if(menuPersonalizado.getEntrada() != -1)
			{
				sql += menuPersonalizado.getEntrada() + ",";
			}
			else {
				sql += "null,";
			}
			if(menuPersonalizado.getPlatoFuerte() != -1)
			{
				sql += menuPersonalizado.getPlatoFuerte() + ",";
			}
			else {
				sql += "null,";
			}
			if(menuPersonalizado.getPostre() != -1)
			{
				sql += menuPersonalizado.getPostre() + ",";
			}
			else {
				sql += "null,";
			}
			if(menuPersonalizado.getBebida() != -1)
			{
				sql += menuPersonalizado.getBebida() + ",";
			}
			else {
				sql += "null,";
			}
			if(menuPersonalizado.getAcompanamiento() != -1)
			{
				sql += menuPersonalizado.getAcompanamiento() + ",";
			}
			else {
				sql += "null,";
			}
		}
		sql += menuPersonalizado.getIdMenu() + ")";
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
	public void updateMenuPersonalizado(MenuPersonalizado menuPersonalizado) throws SQLException, Exception
	{		
		String sql = "UPDATE MENU_Personalizado SET ";
		sql += "PRECIO='" + menuPersonalizado.getPrecio() + "'";
		sql += "ENTRADA='" + menuPersonalizado.getEntrada() + "'";
		sql += "PLATO_FUERTE='" + menuPersonalizado.getPlatoFuerte() + "'";
		sql += "POSTRE='" + menuPersonalizado.getPostre() + "'";
		sql += "BEBIDA='" + menuPersonalizado.getBebida() + "'";
		sql += "ACOMPANAMIENTO='" + menuPersonalizado.getAcompanamiento() + "'";
		sql += "IDMENU='" + menuPersonalizado.getAcompanamiento() + "'";
		sql += " WHERE IDMENUPER = '" + menuPersonalizado.getId()+"'";

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
	public void deletemenuPersonalizado(MenuPersonalizado menuPersonalizado) throws SQLException, Exception
	{
		String sql = "DELETE FROM MENU_Personalizado";
		sql += " WHERE IDMENUPER = '" + menuPersonalizado.getId()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}