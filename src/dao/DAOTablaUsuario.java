package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.*;

public class DAOTablaUsuario
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
	public DAOTablaUsuario()
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
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexi√≥n que entra como parametro.
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
	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception
	{
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long id = rs.getLong("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			usuarios.add(new Usuario(id,nombre,tipo));
		}
		return usuarios;
	}

	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Usuario buscarUsuarioPorId(Long id) throws SQLException, Exception
	{
		Usuario usuario = null;

		String sql = "SELECT * FROM USUARIO WHERE IDENTIFICACION =" + id ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			Long id1 = rs.getLong("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			usuario = new Usuario(id1,nombre,tipo);
		}

		return usuario;
	}


	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addUsuario(Usuario usuario) throws SQLException, Exception
	{
		String sql = "INSERT INTO USUARIO VALUES (";
		sql += usuario.getId() + ",'";
		sql += usuario.getNombre() + "','";
		sql += usuario.getTipo() + "')";


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
	public void updateUsuario(Usuario usuario) throws SQLException, Exception
	{

		String sql = "UPDATE USUARIO SET ";
		sql += "NOMBRE ='" + usuario.getNombre() + "',";
		sql += "TIPO='" + usuario.getTipo()+"'";
		sql += " WHERE IDENTIFICACION = " + usuario.getId();

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
	public void deleteUsuario(Usuario usuario) throws SQLException, Exception
	{
		String sql = "DELETE FROM USUARIO";
		sql += " WHERE IDENTIFICACION = '" + usuario.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public boolean esAdministrador (Long idUsuario) throws SQLException, Exception
	{
		boolean answ = false;
		Usuario usuario = buscarUsuarioPorId(idUsuario);
		if(usuario != null)
		{
			if (usuario.getTipo().equals("AdminGeneral"))
			{
				answ = true;
			}
		}
		return answ;
	}
	public AdministradorRestaurante buscarAdminRestaurantePorIds(Long idUsuario,Long idRestaurante) throws SQLException, Exception
	{
		AdministradorRestaurante adminRestaurante = null;

		String sql = "SELECT * FROM ADMIN_RESTAURANTE WHERE IDUSUARIO =" + idUsuario + "AND IDRESTAURANTE =" + idRestaurante  ;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			Long idUsuario1 = rs.getLong("IDUSUARIO");
			Long idRestaurante1 = rs.getLong("IDRESTAURANTE");
			adminRestaurante = new AdministradorRestaurante(idUsuario1,idRestaurante1);
		}
		return adminRestaurante;
	}
	/**
	 * MÈtodos Iteracion 4
	 * 
	 */

	public boolean esAdministradorDeRestaurantePorId(Long idUsuario, Long idRestaurante) throws SQLException, Exception
	{
		boolean answ = false;
		AdministradorRestaurante adminRestaurante = buscarAdminRestaurantePorIds(idUsuario, idRestaurante);
		if(adminRestaurante != null)
		{
			answ = true;
		}
		return answ;
	}

	/**
	 * Requerimiento 9 - Retorna los usuarios que han consumido al menos un producto de un determinado
	 * restaurante enn un rango de fechas.
	 * @param idUsuario
	 * @param idRestaurante
	 * @param fecha1
	 * @param fecha2
	 * @param orderBy
	 * @param groupBy
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<Usuario> darUsuariosConsumidoresPorFecha(Long idUsuario,Long idRestaurante,String fecha1, String fecha2, String orderBy, String groupBy) throws SQLException, Exception
	{
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		if ((esAdministradorDeRestaurantePorId(idUsuario, idRestaurante) || esAdministrador(idUsuario)) && !orderBy.equals("") && groupBy.equals(""))
		{
			String sql = "Select Usuario.Identificacion,Usuario.Nombre,Usuario.Tipo From usuario join ";
			sql += "(((menu join menu_personalizado on menu.idmenu = menu_personalizado.idMenu and menu.idrestaurante ="+idRestaurante +") ";
			sql += "join menu_pedido on menu_personalizado.idmenuper = menu_pedido.idmenuper) join pedido on pedido.idpedido = menu_pedido.idpedido ";
			sql += "and pedido.fecha between '"+fecha1+"' and '" + fecha2+"' and pedido.estado = 'Entregado') on Usuario.Identificacion = pedido.Idusuario";
			sql += " order by "+orderBy;
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next())
			{
				Long id = rs.getLong("IDENTIFICACION");
				String nombre = rs.getString("NOMBRE");
				String tipo = rs.getString("TIPO");
				usuarios.add(new Usuario(id,nombre,tipo));
			}
		}
		else if ((esAdministradorDeRestaurantePorId(idUsuario, idRestaurante) || esAdministrador(idUsuario)) && orderBy.equals("") && !groupBy.equals(""))
		{
			String sql = "Select Usuario.Identificacion,Usuario.Nombre,Usuario.Tipo From usuario join ";
			sql += "(((menu join menu_personalizado on menu.idmenu = menu_personalizado.idMenu and menu.idrestaurante ="+idRestaurante +") ";
			sql += "join menu_pedido on menu_personalizado.idmenuper = menu_pedido.idmenuper) join pedido on pedido.idpedido = menu_pedido.idpedido ";
			sql += "and pedido.fecha between '"+fecha1+"' and '" + fecha2+"' and pedido.estado = 'Entregado') on Usuario.Identificacion = pedido.Idusuario";
			sql += " group by " +groupBy;
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next())
			{
				Long id = rs.getLong("IDENTIFICACION");
				String nombre = rs.getString("NOMBRE");
				String tipo = rs.getString("TIPO");
				usuarios.add(new Usuario(id,nombre,tipo));
			}
			
		}
		else if ((esAdministradorDeRestaurantePorId(idUsuario, idRestaurante) || esAdministrador(idUsuario)) && orderBy.equals("") && groupBy.equals(""))
		{
			String sql = "Select Usuario.Identificacion,Usuario.Nombre,Usuario.Tipo From usuario join ";
			sql += "(((menu join menu_personalizado on menu.idmenu = menu_personalizado.idMenu and menu.idrestaurante ="+idRestaurante +") ";
			sql += "join menu_pedido on menu_personalizado.idmenuper = menu_pedido.idmenuper) join pedido on pedido.idpedido = menu_pedido.idpedido ";
			sql += "and pedido.fecha between '"+fecha1+"' and '" + fecha2+"' and pedido.estado = 'Entregado') on Usuario.Identificacion = pedido.Idusuario";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next())
			{
				Long id = rs.getLong("IDENTIFICACION");
				String nombre = rs.getString("NOMBRE");
				String tipo = rs.getString("TIPO");
				usuarios.add(new Usuario(id,nombre,tipo));
			}
			
		}
		return usuarios;
	}

	/**
	 * Requerimiento 10 - Retorna los usuarios que no han consumido ningun producto de un determinado
	 * restaurante en un rango de fechas.
	 * @param idUsuario
	 * @param idRestaurante
	 * @param fecha1
	 * @param fecha2
	 * @param orderBy
	 * @param groupBy
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<Usuario> darUsuariosNoConsumidoresPorFecha(Long idUsuario,Long idRestaurante,String fecha1, String fecha2, String orderBy, String groupBy) throws SQLException, Exception
	{
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		if ((esAdministradorDeRestaurantePorId(idUsuario, idRestaurante) || esAdministrador(idUsuario)) && !orderBy.equals("") && groupBy.equals(""))
		{
			String sql = "Select Usuario.Identificacion,Usuario.Nombre, usuario.tipo From usuario left join ";
			sql += "(((menu join menu_personalizado on menu.idmenu = menu_personalizado.idMenu and menu.idrestaurante = " +idRestaurante + " ) ";
			sql += "join menu_pedido on menu_personalizado.idmenuper = menu_pedido.idmenuper) join pedido on pedido.idpedido = menu_pedido.idpedido ";
			sql += " and pedido.fecha between'"+ fecha1 +"' and '" + fecha2 + "') on Usuario.Identificacion = pedido.Idusuario where pedido.Idusuario IS NULL ";
			sql += "order by" + orderBy;
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next())
			{
				Long id = rs.getLong("IDENTIFICACION");
				String nombre = rs.getString("NOMBRE");
				String tipo = rs.getString("TIPO");
				usuarios.add(new Usuario(id,nombre,tipo));
			}
		}
		else if ((esAdministradorDeRestaurantePorId(idUsuario, idRestaurante) || esAdministrador(idUsuario)) && orderBy.equals("") && !groupBy.equals(""))
		{
			String sql = "Select Usuario.Identificacion,Usuario.Nombre From usuario left join ";
			sql += "(((menu join menu_personalizado on menu.idmenu = menu_personalizado.idMenu and menu.idrestaurante = " +idRestaurante + " ) ";
			sql += "join menu_pedido on menu_personalizado.idmenuper = menu_pedido.idmenuper) join pedido on pedido.idpedido = menu_pedido.idpedido ";
			sql += " and pedido.fecha between'"+ fecha1 +"' and '" + fecha2 + "' ) on Usuario.Identificacion = pedido.Idusuario where pedido.Idusuario IS NULL ";
			sql += " group by " +groupBy;
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next())
			{
				Long id = rs.getLong("IDENTIFICACION");
				String nombre = rs.getString("NOMBRE");
				String tipo = rs.getString("TIPO");
				usuarios.add(new Usuario(id,nombre,tipo));
			}
			
		}
		else if ((esAdministradorDeRestaurantePorId(idUsuario, idRestaurante) || esAdministrador(idUsuario)) && orderBy.equals("") && groupBy.equals(""))
		{

			String sql = "Select Usuario.Identificacion,Usuario.Nombre,Usuario.tipo From usuario left join ";
			sql += "(((menu join menu_personalizado on menu.idmenu = menu_personalizado.idMenu and menu.idrestaurante = " +idRestaurante + " ) ";
			sql += "join menu_pedido on menu_personalizado.idmenuper = menu_pedido.idmenuper) join pedido on pedido.idpedido = menu_pedido.idpedido ";
			sql += " and pedido.fecha between'"+ fecha1 +"' and '" + fecha2 + "' ) on Usuario.Identificacion = pedido.Idusuario where pedido.Idusuario IS NULL ";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next())
			{
				Long id = rs.getLong("IDENTIFICACION");
				String nombre = rs.getString("NOMBRE");
				String tipo = rs.getString("TIPO");
				usuarios.add(new Usuario(id,nombre,tipo));
			}
			
		}
		return usuarios;
	}
	/**
	 * Requerimiento 11 - Retorna para cada uno de los dias de la semana el producto mas consumido, el menos consumido,
	 * el restaurante mas frecuentado y el restaurante menos frecuentado.
	 * restaurante en un rango de fechas.
	 * @param idUsuario
	 * @param idRestaurante
	 * @param fecha1
	 * @param fecha2
	 * @param orderBy
	 * @param groupBy
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<Object> darMasConsumidosYFrecuentados(Long idUsuario) throws SQLException, Exception
	{
		ArrayList<Object> usuarios = new ArrayList<Object>();

		if (esAdministrador(idUsuario))
		{
			String sql = "Select Usuario.Identificacion,Usuario.Nombre From usuario left join ";
			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			while (rs.next())
			{
				Long id = rs.getLong("IDENTIFICACION");
				String nombre = rs.getString("NOMBRE");
				String tipo = rs.getString("TIPO");
				usuarios.add(new Usuario(id,nombre,tipo));
			}
		}
		return usuarios;
	}
}