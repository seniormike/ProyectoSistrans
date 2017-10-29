package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import vos.*;

public class DAOTablaReserva
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
	public DAOTablaReserva()
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
	public ArrayList<Reserva> darReservas() throws SQLException, Exception
	{
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();

		String sql = "SELECT * FROM RESERVA";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			Long id = rs.getLong("IDRESERVA");
			String fecha = "" +  rs.getDate("FECHA");
			String hora = rs.getString("HORA");
			Integer numComensales = rs.getInt("NUMERO_COMENSALES");
			String estado = rs.getString("ESTADO");
			Long idZona = rs.getLong("IDZONA");
			Long idUsuario = rs.getLong("IDUSUARIO");
			Long idMenu = rs.getLong("IDMENU");
						
			reservas.add(new Reserva(id,fecha,hora,numComensales,estado,idZona,idUsuario,idMenu));
		}
		return reservas;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Reserva buscarReservaPorId(Long id) throws SQLException, Exception
	{
		Reserva reserva = null;

		String sql = "SELECT * FROM RESERVA WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			Long id1 = rs.getLong("ID");
			String fecha = "" + rs.getDate("FECHA");
			String hora = rs.getString("HORA");
			Integer numComensales = rs.getInt("NUMERO_COMENSALES");
			String estado = rs.getString("ESTADO");
			Long idZona = rs.getLong("IDZONA");
			Long idUsuario = rs.getLong("IDUSUARIO");
			Long idMenu = rs.getLong("IDMENU");
			reserva = new Reserva(id1,fecha,hora,numComensales,estado,idZona,idUsuario,idMenu);
		}

		return reserva;
	}

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addReserva(Reserva reserva) throws SQLException, Exception
	{
		String[] fech = reserva.getFecha().split("-");
		String dia = fech[0];
		String mes = fech[1];
		String año = fech[2];
		String fecha= dia+"/"+mes+"/"+año;
		
		String sql = "INSERT INTO RESERVA VALUES (";
		sql += reserva.getId() + ",'";
		sql += fecha + "','";
		sql += reserva.getHora() + "',";
		sql += reserva.getNumComensales() + ",'";
		sql += reserva.getEstado() + "',";
		sql += reserva.getIdZona() + ",";
		sql += reserva.getIdUsuario() + ",";
		sql += reserva.getIdMenu() +")";
		
		
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
	public void updateReserva(Reserva reserva) throws SQLException, Exception
	{
		String[] fech = reserva.getFecha().split("-");
		String dia = fech[0];
		String mes = fech[1];
		String año = fech[2];
		String fecha= dia+"/"+mes+"/"+año;
		
		String sql = "UPDATE RESERVA SET ";
		sql += "FECHA='" + fecha + "',";
		sql += "HORA='" + reserva.getHora() + "',";
		sql += "NUMERO_COMENSALES=" + reserva.getNumComensales() + ",";
		sql += "ESTADO='" + reserva.getEstado() + "',";
		sql += "IDZONA=" +reserva.getIdZona() + ",";
		sql += "IDUSUARIO=" +reserva.getIdUsuario() + ",";
		sql += "IDMENU=" +reserva.getIdMenu() +")";
		sql += " WHERE IDRESERVA = " + reserva.getId();

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
	public void deleteReserva(Reserva reserva) throws SQLException, Exception
	{
		String sql = "DELETE FROM RESERVA";
		sql += " WHERE IDRESERVA = " + reserva.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}


