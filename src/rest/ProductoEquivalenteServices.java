
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
import vos.ProductoEquivalente;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 */
@Path("restaurante/{IdRestaurante: \\d+}/productosEquivalentes")
public class ProductoEquivalenteServices
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


	/**
	 * Metodo que expone servicio REST usando GET que da todos los videos de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @return Json con todos los videos de la base de datos o json con 
	 * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProductosEquivalentes()
	{
		RotondTM tm = new RotondTM(getPath());
		List<ProductoEquivalente> productosEquivalentes;
		try {
			productosEquivalentes = tm.darProductosEquivalentes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productosEquivalentes).build();
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el video con el id que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/<<id>>" para la busqueda"
	 * @param name - Nombre del video a buscar que entra en la URL como parametro 
	 * @return Json con el/los videos encontrados con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{id}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getProductoEquivalenteId( @PathParam( "id" ) Long idProductoEquivalente )
	{
		RotondTM tm = new RotondTM( getPath( ) );
		try
		{
			ProductoEquivalente p = tm.buscarProductoEquivalentePorId(idProductoEquivalente);
			return Response.status( 200 ).entity( p ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
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
	public Response addProductoEquivalente(@PathParam("IdRestaurante") Long IdRestaurante, ProductoEquivalente productoEquivalente)
	{
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.existeRestaurante(IdRestaurante);
			if(IdRestaurante.equals(productoEquivalente.getIdRestaurante()) && !productoEquivalente.getIdProducto().equals(productoEquivalente.getIdProdEquivalente()))
				tm.addProductoEquivalente(productoEquivalente);
			else
				throw new Exception("Los id de restaurante no coinciden o el producto es el mismo");
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productoEquivalente).build();
	}


	/**
	 * Metodo que expone servicio REST usando PUT que actualiza el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @param video - video a actualizar. 
	 * @return Json con el video que actualizo o Json con el error que se produjo
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProductoEquivalente(@PathParam("IdRestaurante") Long IdRestaurante, ProductoEquivalente productoEquivalente)
	{
		RotondTM tm = new RotondTM(getPath());
		try {
			tm.existeRestaurante(IdRestaurante);
			if(IdRestaurante.equals(productoEquivalente.getIdRestaurante()) && !productoEquivalente.getIdProducto().equals(productoEquivalente.getIdProdEquivalente()))
				tm.updateProductoEquivalente(productoEquivalente);
			else
				throw new Exception("Los id de restaurante no coinciden o el producto es el mismo");
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productoEquivalente).build();
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
	public Response deleteProductoEquivalente(@PathParam("IdRestaurante") Long IdRestaurante, ProductoEquivalente productoEquivalente)
	{
		RotondTM tm = new RotondTM(getPath());
		try {
			if(IdRestaurante.equals(productoEquivalente.getIdRestaurante()))
				tm.deleteProductoEquivalente(productoEquivalente);
			else
				throw new Exception("No tiene permitida esta accion");
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productoEquivalente).build();
	}


}

