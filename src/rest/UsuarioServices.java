package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tm.RotondTM;
import vos.Ingrediente;
import vos.IngredienteEquivalente;
import vos.MenuPedido;
import vos.PrefCategoria;
import vos.PrefPrecio;
import vos.PreferenciaZona;
import vos.Producto;
import vos.ProductoEquivalente;
import vos.ProductoOfrecido;
import vos.Restaurante;
import vos.Usuario;
import vos.Zona;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de
 * host":8080/VideoAndes/rest/videos/...
 */
@Path("usuarios")
public class UsuarioServices {

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la
	 * conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy
	 * actual dentro del servidor.
	 * 
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}

	private String doErrorMessage(Exception e) {
		return "{ \"ERROR\": \"" + e.getMessage() + "\"}";
	}

	/**
	 * Metodo que expone servicio REST usando GET que da todos los videos de la base
	 * de datos. <b>URL: </b> http://"ip o nombre de
	 * host":8080/VideoAndes/rest/videos
	 * 
	 * @return Json con todos los videos de la base de datos o json con el error que
	 *         se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsuarios() {
		RotondTM tm = new RotondTM(getPath());
		List<Usuario> usuarios;
		try {
			usuarios = tm.darUsuarios();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el video con el id que
	 * entra como parametro <b>URL: </b> http://"ip o nombre de
	 * host":8080/VideoAndes/rest/videos/<<id>>" para la busqueda"
	 * 
	 * @param name
	 *            - Nombre del video a buscar que entra en la URL como parametro
	 * @return Json con el/los videos encontrados con el nombre que entra como
	 *         parametro o json con el error que se produjo
	 */

	// Revisar parámetros.!!!

	@GET
	@Path("{id: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsuario(@PathParam("id") Long id) {
		RotondTM tm = new RotondTM(getPath());
		try {
			Usuario u = tm.buscarUsuarioPorId(id);
			return Response.status(200).entity(u).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo que expone servicio REST usando POST que agrega el video que recibe en
	 * Json <b>URL: </b> http://"ip o nombre de
	 * host":8080/VideoAndes/rest/videos/video
	 * 
	 * @param video
	 *            - video a agregar
	 * @return Json con el video que agrego o Json con el error que se produjo
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUsuario(Usuario usuario) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.addUsuario(usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}

	/**
	 * Metodo que expone servicio REST usando POST que agrega los videos que recibe
	 * en Json <b>URL: </b> http://"ip o nombre de
	 * host":8080/VideoAndes/rest/videos/varios
	 * 
	 * @param videos
	 *            - videos a agregar.
	 * @return Json con el video que agrego o Json con el error que se produjo
	 */
	@POST
	@Path("/usuarios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUsuarios(List<Usuario> usuarios) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.addUsuarios(usuarios);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}

	@POST
	@Path("{idAdmin: \\d+}/clientes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClientePorAdministrador(@PathParam("idAdmin") Long idAdmin, Usuario usuario) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.addClientePorAdministrador(idAdmin, usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}

	@POST
	@Path("{idAdmin: \\d+}/restaurantes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestaurantePorAdministrador(@PathParam("idAdmin") Long idAdmin, Restaurante restaurante) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.addRestaurantePorAdministrador(idAdmin, restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}

	@POST
	@Path("{idCliente: \\d+}/preferenciaZona")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferenciaZonaPorCliente(@PathParam("idCliente") Long idCliente, PreferenciaZona prefZona) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.addPreferenciaZonaPorCliente(idCliente, prefZona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(prefZona).build();
	}

	@POST
	@Path("{idCliente: \\d+}/preferenciaCategoria")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferenciaCategoriaPorCliente(@PathParam("idCliente") Long idCliente,
			PrefCategoria prefCategoria) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.addPreferenciaCategoriaPorCliente(idCliente, prefCategoria);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(prefCategoria).build();
	}

	@POST
	@Path("{idCliente: \\d+}/preferenciaPrecio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferenciaPrecioPorCliente(@PathParam("idCliente") Long idCliente, PrefPrecio prefPrecio) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.addPreferenciaPrecioPorCliente(idCliente, prefPrecio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(prefPrecio).build();
	}
	
	@POST
	@Path("{idCliente: \\d+}/menuPedidos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenuPedidoPorCliente(@PathParam("idCLiente") Long idCliente, MenuPedido menuPedido) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.addMenuPedidoPorCliente(idCliente, menuPedido);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menuPedido).build();
	}

	@POST
	@Path("{idAdmin: \\d+}/zonas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addZonaPorAdministrador(@PathParam("idAdmin") Long idAdmin, Zona zona) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.addZonaPorAdministrador(idAdmin, zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}

	@GET
	@Path("{idUsuario: \\d+}/Ingrediente")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darIngredientes() {
		RotondTM tm = new RotondTM(getPath());
		List<Ingrediente> ingredientes;
		try {
			ingredientes = tm.darIngredientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingredientes).build();
	}

	@GET
	@Path("{idUsuario: \\d+}/Prodcuto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darProductos() {
		RotondTM tm = new RotondTM(getPath());
		List<Producto> productos;
		try {
			productos = tm.darProductos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
	}

	@GET
	@Path("{idUsuario: \\d+}/IngEquivalente/{nombre: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darIngEquivalentes(@PathParam("nombre") String nombre) 
	{
		String nom = nombre.replace("-", " ");

		RotondTM tm = new RotondTM(getPath());
		try {
			List<IngredienteEquivalente> i = tm.buscarIngredienteEquivalentePorNombreIngrediente(nom);

			return Response.status(200).entity(i).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path("{idUsuario: \\d+}/ProdEquivalente/{id: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darProdEquivalentes(@PathParam("id") Long id) 
	{
		RotondTM tm = new RotondTM(getPath());
		try {
			List<ProductoEquivalente> i = tm.buscarProductoEquivalentePorId(id);

			return Response.status(200).entity(i).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path("{idUsuario: \\d+}/ProdOfrecido/{idRestaurante: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darProdOfrecido(@PathParam("idRestaurante") Long id) 
	{
		RotondTM tm = new RotondTM(getPath());
		try {
			List<ProductoOfrecido> i = tm.buscarProductosOfrecidosPorIdRestaurante(id);

			return Response.status(200).entity(i).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo que expone servicio REST usando PUT que actualiza el video que recibe
	 * en Json <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * 
	 * @param video
	 *            - video a actualizar.
	 * @return Json con el video que actualizo o Json con el error que se produjo
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(Usuario usuario) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.updateUsuario(usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}

	/**
	 * Metodo que expone servicio REST usando DELETE que elimina el video que recibe
	 * en Json <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * 
	 * @param video
	 *            - video a aliminar.
	 * @return Json con el video que elimino o Json con el error que se produjo
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(Usuario usuario) {
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.deleteUsuario(usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}

}
