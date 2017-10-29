package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import dao.DAOTablaIngrediente;
import dao.DAOTablaIngredienteEquivalente;
import dao.DAOTablaIngredienteProducto;
import dao.DAOTablaIngredientePersonalizado;
import dao.DAOTablaMenu;
import dao.DAOTablaMenuPedido;
import dao.DAOTablaMenuPersonalizado;
import dao.DAOTablaPedido;
import dao.DAOTablaPrefCategoria;
import dao.DAOTablaPrefPrecio;
import dao.DAOTablaProducto;
import dao.DAOTablaProductoOfrecido;
import dao.DAOTablaProductoPersonalizado;
import dao.DAOTablaReserva;
import dao.DAOTablaRestaurante;
import dao.DAOTablaTipoComida;
import dao.DAOTablaUsuario;
import dao.DAOTablaZona;
import vos.Ingrediente;
import vos.IngredienteEquivalente;
import vos.IngredienteProducto;
import vos.IngredientePersonalizado;
import vos.Menu;
import vos.MenuPedido;
import vos.MenuPersonalizado;
import vos.Pedido;
import vos.PrefCategoria;
import vos.PrefPrecio;
import vos.Producto;
import vos.ProductoOfrecido;
import vos.ProductoPersonalizado;
import vos.Reserva;
import vos.Restaurante;
import vos.TipoComida;
import vos.Usuario;
import vos.Zona;

/**
 * Transaction Manager de la aplicacion (TM)
 * Fachada en patron singleton de la aplicacion
 * @author Monitores 2017-20
 */
public class RotondTM {


	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;


	/**
	 * Metodo constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////

	/**
	 * TRANSACCIONES PARA PRODUCTOS
	 */
	public List<Producto> darProductos() throws Exception
	{
		List<Producto> productos;
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.darProductos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}

	

	public Producto buscarProductoPorNombre(String nombre) throws Exception
	{
		Producto producto;
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			producto = daoProductos.buscarProductoPorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return producto;
	}

	public void addProducto(Producto producto) throws Exception
	{
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoProductos.setConn(conn);
			daoProductos.addProducto(producto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addProductos(List<Producto> productos) throws Exception
	{
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoProductos.setConn(conn);
			Iterator<Producto> it = productos.iterator();
			while(it.hasNext())
			{
				daoProductos.addProducto(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateProducto(Producto producto) throws Exception
	{
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoProductos.setConn(conn);
			daoProductos.updateProducto(producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteProducto(Producto producto) throws Exception
	{
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoProductos.setConn(conn);
			daoProductos.deleteProducto(producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * TRANSACCIONES PARA RESTAURANTES
	 */

	public List<Restaurante> darRestaurantes() throws Exception
	{
		List<Restaurante> restaurantes;
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurantes = daoRestaurantes.darRestaurantes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurantes;
	}

	public Restaurante buscarRestaurantePorNombre(String name) throws Exception
	{
		Restaurante restaurante;
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurante = daoRestaurantes.buscarRestaurantePorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurante;
	}
	public void addRestaurante(Restaurante restaurante) throws Exception
	{
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoRestaurantes.setConn(conn);
			daoRestaurantes.addRestaurante(restaurante);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addRestaurantes(List<Restaurante> restaurantes) throws Exception
	{
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoRestaurantes.setConn(conn);
			Iterator<Restaurante> it = restaurantes.iterator();
			while(it.hasNext())
			{
				daoRestaurantes.addRestaurante(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateRestaurante(Restaurante restaurante) throws Exception
	{
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoRestaurantes.setConn(conn);
			daoRestaurantes.updateRestaurante(restaurante);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteRestaurante(Restaurante restaurante) throws Exception
	{
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoRestaurantes.setConn(conn);
			daoRestaurantes.deleteRestaurante(restaurante);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * TRANSACCIONES PARA INGREDIENTES
	 */

	public List<Ingrediente> darIngredientes() throws Exception
	{
		List<Ingrediente> ingredientes;
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.darIngredientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}

	public Ingrediente buscarIngredientePorNombre(String name) throws Exception
	{
		Ingrediente ingrediente;
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingrediente = daoIngredientes.buscarIngredientePorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingrediente;
	}
	public void addIngrediente(Ingrediente ingrediente) throws Exception
	{
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredientes.setConn(conn);
			daoIngredientes.addIngrediente(ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addIngredientes(List<Ingrediente> ingredientes) throws Exception
	{
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoIngredientes.setConn(conn);
			Iterator<Ingrediente> it = ingredientes.iterator();
			while(it.hasNext())
			{
				daoIngredientes.addIngrediente(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateIngrediente(Ingrediente ingrediente) throws Exception
	{
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredientes.setConn(conn);
			daoIngredientes.updateIngrediente(ingrediente);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteIngrediente(Ingrediente ingrediente) throws Exception
	{
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredientes.setConn(conn);
			daoIngredientes.deleteIngrediente(ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * TRANSACCIONES PARA TIPOCOMIDA
	 */

	public List<TipoComida> darTiposComida() throws Exception
	{
		List<TipoComida> tiposComida;
		DAOTablaTipoComida daoTiposComida = new DAOTablaTipoComida();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoTiposComida.setConn(conn);
			tiposComida = daoTiposComida.darTiposComida();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTiposComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return tiposComida;
	}

	public TipoComida buscarTipoComidaPorId(Long id) throws Exception
	{
		TipoComida tipoComida;
		DAOTablaTipoComida daoTiposComida = new DAOTablaTipoComida();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoTiposComida.setConn(conn);
			tipoComida = daoTiposComida.buscarTipoComidaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTiposComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return tipoComida;
	}
	public void addTipoComida(TipoComida tipoComida) throws Exception
	{
		DAOTablaTipoComida daoTiposComida = new DAOTablaTipoComida();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoTiposComida.setConn(conn);
			daoTiposComida.addTipoComida(tipoComida);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTiposComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addTiposComida(List<TipoComida> tiposComida) throws Exception
	{
		DAOTablaTipoComida daoTiposComida = new DAOTablaTipoComida();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoTiposComida.setConn(conn);
			Iterator<TipoComida> it = tiposComida.iterator();
			while(it.hasNext())
			{
				daoTiposComida.addTipoComida(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTiposComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateTipoComida (TipoComida tipoComida) throws Exception
	{
		DAOTablaTipoComida daoTiposComida = new DAOTablaTipoComida();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoTiposComida.setConn(conn);
			daoTiposComida.updateTipoComida(tipoComida);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTiposComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteTipoComida(TipoComida tipoComida) throws Exception
	{
		DAOTablaTipoComida daoTiposComida = new DAOTablaTipoComida();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoTiposComida.setConn(conn);
			daoTiposComida.deleteTipoComida(tipoComida);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTiposComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * TRANSACCIONES PARA ZONAS
	 */

	public List<Zona> darZonas() throws Exception
	{
		List<Zona> zonas;
		DAOTablaZona daoZonas = new DAOTablaZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			zonas = daoZonas.darZonas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}

	public Zona buscarZonaPorId(Long id) throws Exception
	{
		Zona zona;
		DAOTablaZona daoZonas = new DAOTablaZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			zona = daoZonas.buscarIngredientePorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zona;
	}
	public void addZona(Zona zona) throws Exception
	{
		DAOTablaZona daoZonas = new DAOTablaZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoZonas.setConn(conn);
			daoZonas.addZona(zona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addZonas(List<Zona> zonas) throws Exception
	{
		DAOTablaZona daoZonas = new DAOTablaZona();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoZonas.setConn(conn);
			Iterator<Zona> it = zonas.iterator();
			while(it.hasNext())
			{
				daoZonas.addZona(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateZona (Zona zona) throws Exception
	{
		DAOTablaZona daoZonas = new DAOTablaZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoZonas.setConn(conn);
			daoZonas.updateZona(zona);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteZona(Zona zona) throws Exception
	{
		DAOTablaZona daoZonas = new DAOTablaZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoZonas.setConn(conn);
			daoZonas.deleteZona(zona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * TRANSACCIONES PARA PEDIDOS
	 */

	public List<Pedido> darPedidos() throws Exception
	{
		List<Pedido> pedidos;
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			pedidos = daoPedidos.darPedidos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedidos;
	}

	public Pedido buscarPedidoPorId(Long id) throws Exception
	{
		Pedido pedido;
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			pedido = daoPedidos.buscarPedidoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedido;
	}
	public void addPedido(Pedido pedido) throws Exception
	{
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoPedidos.setConn(conn);
			daoPedidos.addPedido(pedido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addPedidos(List<Pedido> pedidos) throws Exception
	{
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoPedidos.setConn(conn);
			Iterator<Pedido> it = pedidos.iterator();
			while(it.hasNext())
			{
				daoPedidos.addPedido(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updatePedido(Pedido pedido) throws Exception
	{
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoPedidos.setConn(conn);
			daoPedidos.updatePedido(pedido);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deletePedido(Pedido pedido) throws Exception
	{
		DAOTablaPedido daoPedidos = new DAOTablaPedido();

		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoPedidos.setConn(conn);
			daoPedidos.deletePedido(pedido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	/**
	 * TRANSACCIONES PARA USUARIOS
	 */

	public List<Usuario> darUsuarios() throws Exception
	{
		List<Usuario> usuarios;
		DAOTablaUsuario daoUsuarios = new DAOTablaUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuarios = daoUsuarios.darUsuarios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	public Usuario buscarUsuarioPorId(Long id) throws Exception
	{
		Usuario usuario;
		DAOTablaUsuario daoUsuarios = new DAOTablaUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuario = daoUsuarios.buscarUsuarioPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuario;
	}
	public void addUsuario(Usuario usuario) throws Exception
	{
		DAOTablaUsuario daoUsuarios = new DAOTablaUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoUsuarios.setConn(conn);
			daoUsuarios.addUsuario(usuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addUsuarios(List<Usuario> usuarios) throws Exception
	{
		DAOTablaUsuario daoUsuarios = new DAOTablaUsuario();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoUsuarios.setConn(conn);
			Iterator<Usuario> it = usuarios.iterator();
			while(it.hasNext())
			{
				daoUsuarios.addUsuario(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateUsuario(Usuario usuario) throws Exception
	{
		DAOTablaUsuario daoUsuarios = new DAOTablaUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoUsuarios.setConn(conn);
			daoUsuarios.updateUsuario(usuario);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteUsuario(Usuario usuario) throws Exception
	{
		DAOTablaUsuario daoUsuarios = new DAOTablaUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoUsuarios.setConn(conn);
			daoUsuarios.deleteUsuario(usuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	/**
	 * TRANSACCIONES PARA INGREDIENTE_PRODUCTO
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<IngredienteProducto> darIngredienteProductos() throws Exception
	{
		List<IngredienteProducto> ingredienteProducto;
		DAOTablaIngredienteProducto daoIngredienteProductos = new DAOTablaIngredienteProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredienteProductos.setConn(conn);
			ingredienteProducto = daoIngredienteProductos.darIngredienteProductos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredienteProducto;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ArrayList<IngredienteProducto> buscarIngredienteProductoPorIdProducto(long id) throws Exception
	{
		ArrayList<IngredienteProducto> ingredienteProducto;
		DAOTablaIngredienteProducto daoIngredienteProductos = new DAOTablaIngredienteProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredienteProductos.setConn(conn);
			ingredienteProducto = daoIngredienteProductos.buscarIngredienteProductoPorIdProducto(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredienteProducto;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addIngredienteProducto(IngredienteProducto ingredienteProducto) throws Exception
	{
		DAOTablaIngredienteProducto daoIngredienteProductos = new DAOTablaIngredienteProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredienteProductos.setConn(conn);
			daoIngredienteProductos.addIngredienteProducto(ingredienteProducto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updateIngredienteProducto(IngredienteProducto ingredienteProducto) throws Exception
	{
		DAOTablaIngredienteProducto daoIngredienteProductos = new DAOTablaIngredienteProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredienteProductos.setConn(conn);
			daoIngredienteProductos.updateIngredienteProducto(ingredienteProducto);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteIngredienteProducto(IngredienteProducto ingredienteProducto) throws Exception
	{
		DAOTablaIngredienteProducto daoIngredienteProductos = new DAOTablaIngredienteProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredienteProductos.setConn(conn);
			daoIngredienteProductos.deleteIngredienteProducto(ingredienteProducto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * TRANSACCIONES PARA MENU
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Menu> darMenus() throws Exception
	{
		List<Menu> menu;
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menu = daoMenus.darMenus();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menu;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Menu buscarMenuPorId(long id) throws Exception
	{
		Menu menu;
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menu = daoMenus.buscarMenuPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menu;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addMenu(Menu menu) throws Exception
	{
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoMenus.setConn(conn);
			daoMenus.addMenu(menu);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updateMenu(Menu menu) throws Exception
	{
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoMenus.setConn(conn);
			daoMenus.updateMenu(menu);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteMenu(Menu menu) throws Exception
	{
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoMenus.setConn(conn);
			daoMenus.deletemenu(menu);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * TRANSACCIONES PARA INGREDIENTE_PRODUCTO_PERZONALIZADO
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<IngredientePersonalizado> darIngredienteProductoPersonalizados() throws Exception
	{
		List<IngredientePersonalizado> ingredienteProducto;
		DAOTablaIngredientePersonalizado daoIngredienteProductos = new DAOTablaIngredientePersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredienteProductos.setConn(conn);
			ingredienteProducto = daoIngredienteProductos.darIngredienteProductoPersonalizadovs();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredienteProducto;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ArrayList<IngredientePersonalizado> buscarIngredienteProductoPorIdProductoPersonalizado(long id) throws Exception
	{
		ArrayList<IngredientePersonalizado> ingredienteProducto;
		DAOTablaIngredientePersonalizado daoIngredienteProductos = new DAOTablaIngredientePersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredienteProductos.setConn(conn);
			ingredienteProducto = daoIngredienteProductos.buscarIngredienteProductoPersonalizadoPorIdProducto(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredienteProducto;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addIngredienteProductoPersonalizado(IngredientePersonalizado ingredienteProducto) throws Exception
	{
		DAOTablaIngredientePersonalizado daoIngredienteProductos = new DAOTablaIngredientePersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredienteProductos.setConn(conn);
			daoIngredienteProductos.addIngredienteProductoPersonalizado(ingredienteProducto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updateIngredienteProductoPersonalizado(IngredientePersonalizado ingredienteProducto) throws Exception
	{
		DAOTablaIngredientePersonalizado daoIngredienteProductos = new DAOTablaIngredientePersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredienteProductos.setConn(conn);
			daoIngredienteProductos.updateIngredienteProductoPersonalizado(ingredienteProducto);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteIngredienteProductoPersonalizado(IngredientePersonalizado ingredienteProducto) throws Exception
	{
		DAOTablaIngredientePersonalizado daoIngredienteProductos = new DAOTablaIngredientePersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredienteProductos.setConn(conn);
			daoIngredienteProductos.deleteIngredienteProductoPersonalizado(ingredienteProducto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredienteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * TRANSACCIONES PARA RESERVA
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Reserva> darReservas() throws Exception
	{
		List<Reserva> reservas;
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoReservas.setConn(conn);
			reservas = daoReservas.darReservas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reservas;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Reserva buscarReservaPorId(long id) throws Exception
	{
		Reserva reserva;
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoReservas.setConn(conn);
			reserva = daoReservas.buscarReservaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reserva;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addReserva(Reserva reserva) throws Exception
	{
		DAOTablaReserva daoTablaReserva = new DAOTablaReserva();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoTablaReserva.setConn(conn);
			daoTablaReserva.addReserva(reserva);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updateReserva(Reserva reserva) throws Exception
	{
		DAOTablaReserva daoTablaReserva = new DAOTablaReserva();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoTablaReserva.setConn(conn);
			daoTablaReserva.updateReserva(reserva);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addReservas(List<Reserva> reservas) throws Exception
	{
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoReservas.setConn(conn);
			Iterator<Reserva> it = reservas.iterator();
			while(it.hasNext())
			{
				daoReservas.addReserva(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteReserva(Reserva reserva) throws Exception
	{
		DAOTablaReserva daoTablaReserva = new DAOTablaReserva();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoTablaReserva.setConn(conn);
			daoTablaReserva.deleteReserva(reserva);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * TRANSACCIONES PARA MENU_PEDIDO
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<MenuPedido> darMenuPedido() throws Exception
	{
		List<MenuPedido> menuPedido;
		DAOTablaMenuPedido daoMenuPedido = new DAOTablaMenuPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenuPedido.setConn(conn);
			menuPedido = daoMenuPedido.darMenuPedidos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenuPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menuPedido;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MenuPedido buscarMenuPedidoPorIdPedido(long id) throws Exception
	{
		MenuPedido menuPedido;
		DAOTablaMenuPedido daoMenuPedidos = new DAOTablaMenuPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenuPedidos.setConn(conn);
			menuPedido = daoMenuPedidos.buscarMenuPedidoPorIdPedido(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenuPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menuPedido;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addMenuPedido(MenuPedido menuPedido) throws Exception
	{
		DAOTablaMenuPedido daoTablaMenuPedido = new DAOTablaMenuPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoTablaMenuPedido.setConn(conn);
			daoTablaMenuPedido.addMenuPedido(menuPedido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaMenuPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updateMenuPedido(MenuPedido menuPedido) throws Exception
	{
		DAOTablaMenuPedido daoTablaMenuPedido = new DAOTablaMenuPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoTablaMenuPedido.setConn(conn);
			daoTablaMenuPedido.updateMenuPedido(menuPedido);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaMenuPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addMenuPedidos(List<MenuPedido> menuPedido) throws Exception
	{
		DAOTablaMenuPedido daoMenuPedidos = new DAOTablaMenuPedido();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoMenuPedidos.setConn(conn);
			Iterator<MenuPedido> it = menuPedido.iterator();
			while(it.hasNext())
			{
				daoMenuPedidos.addMenuPedido(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoMenuPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteMenuPedido(MenuPedido menuPedido) throws Exception
	{
		DAOTablaMenuPedido daoTablaMenuPedido = new DAOTablaMenuPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoTablaMenuPedido.setConn(conn);
			daoTablaMenuPedido.deleteMenuPedido(menuPedido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaMenuPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * TRANSACCIONES PARA MENU_PERSONALIZADO
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<MenuPersonalizado> darMenuPersonalizados() throws Exception
	{
		List<MenuPersonalizado> menuPersonalizado;
		DAOTablaMenuPersonalizado daoMenuPersonalizados = new DAOTablaMenuPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenuPersonalizados.setConn(conn);
			menuPersonalizado = daoMenuPersonalizados.darMenuPersonalizados();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenuPersonalizados.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menuPersonalizado;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MenuPersonalizado buscarMenuPersonalizadoPorId(long id) throws Exception
	{
		MenuPersonalizado menuPersonalizado;
		DAOTablaMenuPersonalizado daoMenuPersonalizados = new DAOTablaMenuPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenuPersonalizados.setConn(conn);
			menuPersonalizado = daoMenuPersonalizados.buscarMenuPersonalizadoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenuPersonalizados.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menuPersonalizado;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addMenuPersonalizado(MenuPersonalizado menuPersonalizado) throws Exception
	{
		DAOTablaMenuPersonalizado daoMenuPersonalizados = new DAOTablaMenuPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoMenuPersonalizados.setConn(conn);
			daoMenuPersonalizados.addMenuPersonalizado(menuPersonalizado);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoMenuPersonalizados.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updateMenuPersonalizado(MenuPersonalizado menuPersonalizado) throws Exception
	{
		DAOTablaMenuPersonalizado daoMenuPersonalizados = new DAOTablaMenuPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoMenuPersonalizados.setConn(conn);
			daoMenuPersonalizados.updateMenuPersonalizado(menuPersonalizado);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoMenuPersonalizados.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteMenuPersonalizado(MenuPersonalizado menuPersonalizado) throws Exception
	{
		DAOTablaMenuPersonalizado daoMenuPersonalizados = new DAOTablaMenuPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoMenuPersonalizados.setConn(conn);
			daoMenuPersonalizados.deletemenuPersonalizado(menuPersonalizado);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoMenuPersonalizados.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	/**
	 * TRANSACCIONES PARA PREFERENCIA_PRECIO
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PrefPrecio> darPrefPrecios() throws Exception
	{
		List<PrefPrecio> PrefPrecio;
		DAOTablaPrefPrecio daoPrefPrecio = new DAOTablaPrefPrecio();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPrefPrecio.setConn(conn);
			PrefPrecio = daoPrefPrecio.darPrefPrecios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPrefPrecio.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return PrefPrecio;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PrefPrecio buscarPrefPrecioPorIdUsuario(long id) throws Exception
	{
		PrefPrecio prefPrecio;
		DAOTablaPrefPrecio daoPrefPrecio = new DAOTablaPrefPrecio();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPrefPrecio.setConn(conn);
			prefPrecio = daoPrefPrecio.buscarrefPrecioPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPrefPrecio.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return prefPrecio;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addPrefPrecio(PrefPrecio prefPrecio) throws Exception
	{
		DAOTablaPrefPrecio daoPrefPrecio = new DAOTablaPrefPrecio();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoPrefPrecio.setConn(conn);
			daoPrefPrecio.addPrefPrecio(prefPrecio);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPrefPrecio.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updatePrefPrecio(PrefPrecio prefPrecio) throws Exception
	{
		DAOTablaPrefPrecio daoPrefPrecio = new DAOTablaPrefPrecio();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoPrefPrecio.setConn(conn);
			daoPrefPrecio.updatePrefPrecio(prefPrecio);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPrefPrecio.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deletePrefPrecio(PrefPrecio prefPrecio) throws Exception
	{
		DAOTablaPrefPrecio daoPrefPrecio = new DAOTablaPrefPrecio();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoPrefPrecio.setConn(conn);
			daoPrefPrecio.deletePrefPrecio(prefPrecio);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPrefPrecio.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * TRANSACCIONES PARA INGREDIENTE_EQUIVALENTE
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<IngredienteEquivalente> darIngredienteEquivalentes() throws Exception
	{
		List<IngredienteEquivalente> ingredienteEquivalente;
		DAOTablaIngredienteEquivalente daoIngredienteEquivalente = new DAOTablaIngredienteEquivalente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredienteEquivalente.setConn(conn);
			ingredienteEquivalente = daoIngredienteEquivalente.darIngredientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredienteEquivalente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredienteEquivalente;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ArrayList<IngredienteEquivalente> buscarIngredienteEquivalentePorNombreIngrediente(String nombre) throws Exception
	{
		ArrayList<IngredienteEquivalente> ingredienteEquivalente;
		DAOTablaIngredienteEquivalente daoIngredienteEquivalente = new DAOTablaIngredienteEquivalente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredienteEquivalente.setConn(conn);
			ingredienteEquivalente = daoIngredienteEquivalente.buscarIngredienteEquivalentePorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredienteEquivalente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredienteEquivalente;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addIngredienteEquivalente(IngredienteEquivalente ingredienteEquivalente) throws Exception
	{
		DAOTablaIngredienteEquivalente daoIngredienteEquivalente = new DAOTablaIngredienteEquivalente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredienteEquivalente.setConn(conn);
			daoIngredienteEquivalente.addIngredienteEquivalente(ingredienteEquivalente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredienteEquivalente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updateIngredienteEquivalente(IngredienteEquivalente ingredienteEquivalente) throws Exception
	{
		DAOTablaIngredienteEquivalente daoIngredienteEquivalente = new DAOTablaIngredienteEquivalente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredienteEquivalente.setConn(conn);
			daoIngredienteEquivalente.updateIngredienteEquivalente(ingredienteEquivalente);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredienteEquivalente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteIngredienteEquivalente(IngredienteEquivalente ingredienteEquivalente) throws Exception
	{
		DAOTablaIngredienteEquivalente daoIngredienteEquivalente = new DAOTablaIngredienteEquivalente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoIngredienteEquivalente.setConn(conn);
			daoIngredienteEquivalente.deleteIngredienteEquivalente(ingredienteEquivalente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredienteEquivalente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * TRANSACCIONES PARA PRODUCTO_PERZONALIZADO
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ProductoPersonalizado> darProductoPersonalizados() throws Exception
	{
		List<ProductoPersonalizado> productoPersonalizado;
		DAOTablaProductoPersonalizado daoProductoPersonalizado = new DAOTablaProductoPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductoPersonalizado.setConn(conn);
			productoPersonalizado = daoProductoPersonalizado.darProductoPersonalizadovs();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoPersonalizado.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productoPersonalizado;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ProductoPersonalizado> buscarProductoPorIdProductoPersonalizado(long id) throws Exception
	{
		ArrayList<ProductoPersonalizado> productoPersonalizado;
		DAOTablaProductoPersonalizado daoProductoPersonalizado = new DAOTablaProductoPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductoPersonalizado.setConn(conn);
			productoPersonalizado = daoProductoPersonalizado.buscarProductoPersonalizadoPorIdProductoPer(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoPersonalizado.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productoPersonalizado;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addProductoPersonalizado(ProductoPersonalizado productoPersonalizado) throws Exception
	{
		DAOTablaProductoPersonalizado daoProductoPersonalizado = new DAOTablaProductoPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoProductoPersonalizado.setConn(conn);
			daoProductoPersonalizado.addProductoPersonalizado(productoPersonalizado);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductoPersonalizado.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updateProductoPersonalizado(ProductoPersonalizado productoPersonalizado) throws Exception
	{
		DAOTablaProductoPersonalizado daoProductoPersonalizado = new DAOTablaProductoPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoProductoPersonalizado.setConn(conn);
			daoProductoPersonalizado.updateProductoPersonalizado(productoPersonalizado);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductoPersonalizado.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteProductoPersonalizado(ProductoPersonalizado productoPersonalizado) throws Exception
	{
		DAOTablaProductoPersonalizado daoProductoPersonalizado = new DAOTablaProductoPersonalizado();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoProductoPersonalizado.setConn(conn);
			daoProductoPersonalizado.deleteProductoPersonalizado(productoPersonalizado);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductoPersonalizado.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * TRANSACCIONES PARA PREFERENCIA_CATEGORIA
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PrefCategoria> darPrefCategorias() throws Exception
	{
		List<PrefCategoria> PrefCategoria;
		DAOTablaPrefCategoria daoPrefCategoria = new DAOTablaPrefCategoria();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPrefCategoria.setConn(conn);
			PrefCategoria = daoPrefCategoria.darPrefCategoria();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPrefCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return PrefCategoria;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PrefCategoria> buscarPrefCategoriaPorIdUsuario(long id) throws Exception
	{
		ArrayList<PrefCategoria> PrefCategoria;
		DAOTablaPrefCategoria daoPrefCategoria = new DAOTablaPrefCategoria();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPrefCategoria.setConn(conn);
			PrefCategoria = daoPrefCategoria.buscarrefCategoriaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPrefCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return PrefCategoria;
	}
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void addPrefCategoria(PrefCategoria PrefCategoria) throws Exception
	{
		DAOTablaPrefCategoria daoPrefCategoria = new DAOTablaPrefCategoria();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoPrefCategoria.setConn(conn);
			daoPrefCategoria.addPrefCategoria(PrefCategoria);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPrefCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updatePrefCategoria(PrefCategoria PrefCategoria) throws Exception
	{
		DAOTablaPrefCategoria daoPrefCategoria = new DAOTablaPrefCategoria();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoPrefCategoria.setConn(conn);
			daoPrefCategoria.updatePrefCategoria(PrefCategoria);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPrefCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deletePrefCategoria(PrefCategoria PrefCategoria) throws Exception
	{
		DAOTablaPrefCategoria daoPrefCategoria = new DAOTablaPrefCategoria();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoPrefCategoria.setConn(conn);
			daoPrefCategoria.deletePrefCategoria(PrefCategoria);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPrefCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * TRANSACCIONES PARA PRODUCTO_OFRECIDO
	 */

	public List<ProductoOfrecido> darProductosOfrecidos() throws Exception
	{
		List<ProductoOfrecido> productosOfrecidos;
		DAOTablaProductoOfrecido daoTablaProductoOfrecido = new DAOTablaProductoOfrecido();
		try 
		{
			this.conn = darConexion();
			daoTablaProductoOfrecido.setConn(conn);
			productosOfrecidos = daoTablaProductoOfrecido.darProductosOfrecidos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaProductoOfrecido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productosOfrecidos;
	}

	/**
	 *	Metodo pendiente, buscar producto ofrecido por dos ids
	 */
	
	//---
	
	
	
	public void addProductoOfrecido(ProductoOfrecido productoOfrecido) throws Exception
	{
		DAOTablaProductoOfrecido daoProductoOfrecido = new DAOTablaProductoOfrecido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoProductoOfrecido.setConn(conn);
			daoProductoOfrecido.addProductoOfrecido(productoOfrecido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductoOfrecido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void updateProductoOfrecido(ProductoOfrecido productoOfrecido) throws Exception
	{
		DAOTablaProductoOfrecido daoProductoOfrecido = new DAOTablaProductoOfrecido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoProductoOfrecido.setConn(conn);
			daoProductoOfrecido.updateProductoOfrecido(productoOfrecido);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductoOfrecido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @throws Exception
	 */
	public void deleteProductoOfrecido(ProductoOfrecido productoOfrecido) throws Exception
	{
		DAOTablaProductoOfrecido daoProductoOfrecido = new DAOTablaProductoOfrecido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
			daoProductoOfrecido.setConn(conn);
			daoProductoOfrecido.deleteProductoOfrecido(productoOfrecido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductoOfrecido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

}
