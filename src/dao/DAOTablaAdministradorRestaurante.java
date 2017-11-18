package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.*;

public class DAOTablaAdministradorRestaurante
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
	public DAOTablaAdministradorRestaurante()
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

	public void setConn(Connection con)
	{
		this.conn = con;
	}


	public ArrayList<AdministradorRestaurante> darAdministradoresRestaurantes() throws SQLException, Exception
	{
		ArrayList<AdministradorRestaurante> adminsRestaurante= new ArrayList<AdministradorRestaurante>();

		String sql = "SELECT * FROM ADMIN_RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long idUsuario = rs.getLong("IDUSUARIO");
			Long idRestaurante = rs.getLong("IDRESTAURANTE");
			adminsRestaurante.add(new AdministradorRestaurante(idUsuario,idRestaurante));
		}
		return adminsRestaurante;
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
	

	public void addAdminUsuarioRestaurante(AdministradorRestaurante adminRestaurante) throws SQLException, Exception
	{
		String sql = "INSERT INTO ADMIN_RESTAURANTE VALUES (";
		sql += adminRestaurante.getIdUsuario() + ",";
		sql += adminRestaurante.getIdRestaurante() +")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void updateAdministradorRestaurante(AdministradorRestaurante adminRestaurante) throws SQLException, Exception
	{

		String sql = "UPDATE ADMIN_RESTAURANTE SET ";
		sql += "IDUSUARIO=" + adminRestaurante.getIdUsuario();
		sql += " WHERE IDRESTAURANTE = " + adminRestaurante.getIdRestaurante();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deleteAdminRestaurante(AdministradorRestaurante adminRestaurante) throws SQLException, Exception
	{
		String sql = "DELETE FROM ADMIN_RESTAURANTE";
		sql += " WHERE IDUSUARIO =" + adminRestaurante.getIdUsuario() + "AND IDRESTAURANTE =" + adminRestaurante.getIdRestaurante();
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


}