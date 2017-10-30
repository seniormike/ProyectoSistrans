
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
import vos.ProductoOfrecido;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 */
@Path("restaurante/{IdRestaurante: \\d+}/productosOfrecidos")
public class ProductoOfrecidoServices
{

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath()
	{
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e)
	{
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}

	//	/**
	//	 * Metodo que expone servicio REST usando GET que da todos los videos de la base de datos.
	//	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	//	 * @return Json con todos los videos de la base de datos o json con 
	//     * el error que se produjo
	//	 */
	//	@GET
	//	@Produces({ MediaType.APPLICATION_JSON })
	//	public Response getProductosOfrecidos()
	//	{
	//		RotondTM tm = new RotondTM(getPath());
	//		List<ProductoOfrecido> productos;
	//		try {
	//			productos = tm.darProductosOfrecidos();
	//		} catch (Exception e) {
	//			return Response.status(500).entity(doErrorMessage(e)).build();
	//		}
	//		return Response.status(200).entity(productos).build();
	//	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el video con el id que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/<<id>>" para la busqueda"
	 * @param name - Nombre del video a buscar que entra en la URL como parametro 
	 * @return Json con el/los videos encontrados con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getProductosPorIdRestaurante( @PathParam( "IdRestaurante" ) Long idRestaurante)
	{
		RotondTM tm = new RotondTM( getPath( ) );
		List<ProductoOfrecido> productosOfrecidos;
		try
		{
			tm.existeRestaurante(idRestaurante);
			productosOfrecidos = tm.buscarProductosOfrecidosPorIdRestaurante(idRestaurante);
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).entity( productosOfrecidos ).build( );			
	}


	/**
	 * Metodo que expone servicio REST usando POST que agrega el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
	 * @param video - video a agregar
	 * @return Json con el video que agrego o Json con el error que se produjo
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProducto(@PathParam("IdRestaurante") Long IdRestaurante, ProductoOfrecido productoOfrecido)
	{
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.existeRestaurante(IdRestaurante);
			if(IdRestaurante.equals(productoOfrecido.getIdRestaurante()))
				tm.addProductoOfrecido(productoOfrecido);
			else
				throw new Exception("El Restaurante y el idRestaurante no coinciden");
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productoOfrecido).build();
	}

	//    /**
	//     * Metodo que expone servicio REST usando POST que agrega los videos que recibe en Json
	//     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/varios
	//     * @param videos - videos a agregar. 
	//     * @return Json con el video que agrego o Json con el error que se produjo
	//     */
	//	@POST
	//	@Path("/productos")
	//	@Consumes(MediaType.APPLICATION_JSON)
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public Response addProductosOfrecidos(List<ProductoOfrecido> productos)
	//	{
	//		RotondTM tm = new RotondTM(getPath());
	//		try {
	//			tm.addProductosOfre(productos);
	//		} catch (Exception e) {
	//			return Response.status(500).entity(doErrorMessage(e)).build();
	//		}
	//		return Response.status(200).entity(productos).build();
	//	}

	/**
	 * Metodo que expone servicio REST usando PUT que actualiza el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @param video - video a actualizar. 
	 * @return Json con el video que actualizo o Json con el error que se produjo
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProductoOfrecido(@PathParam("IdRestaurante") Long IdRestaurante, ProductoOfrecido productoOfrecido)
	{
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.existeRestaurante(IdRestaurante);
			if(IdRestaurante.equals(productoOfrecido.getIdRestaurante()))
			tm.updateProductoOfrecido(productoOfrecido);
			else
				throw new Exception("No tiene permitida realizar esta accion");
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productoOfrecido).build();
	}

	/**
	 * Metodo que expone servicio REST usando DELETE que elimina el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @param video - video a aliminar. 
	 * @return Json con el video que elimino o Json con el error que se produjo
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProductoOfrecido(@PathParam("IdRestaurante") Long IdRestaurante, ProductoOfrecido productoOfrecido)
	{
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.existeRestaurante(IdRestaurante);
			if(IdRestaurante.equals(productoOfrecido.getIdRestaurante()))
			tm.deleteProductoOfrecido(productoOfrecido);
			else
				throw new Exception("No tiene permitida realizar esta accion");
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productoOfrecido).build();
	}

}