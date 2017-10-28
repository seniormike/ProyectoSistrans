package vos;

import org.codehaus.jackson.annotate.*;
public class ProductoOfrecido
{
	/**
	 * Nombre del producto
	 */
	@JsonProperty(value="idProducto")
	private Long idProducto;
	
	/**
	 * Nombre del producto
	 */
	@JsonProperty(value="idRestaurante")
	private Long idRestaurante;
	
	/**
	 * Nombre del producto
	 */
	@JsonProperty(value="cantidad")
	private Integer cantidad;
	
	
	@JsonProperty(value="cantidadMaxima")
	private Integer cantidadMaxima;
	

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public ProductoOfrecido(@JsonProperty(value="idProducto")Long idProducto,@JsonProperty(value="idRestaurante")Long idRestaurante, @JsonProperty(value="cantidad")Integer cantidad, @JsonProperty(value="cantidadMaxima")Integer cantidadMaxima)
	{
		this.idProducto = idProducto;
		this.idRestaurante = idRestaurante;
		this.cantidad = cantidad;
		this.cantidadMaxima = cantidadMaxima;
		
	}


	public Integer getCantidadMaxima() {
		return cantidadMaxima;
	}


	public void setCantidadMaxima(Integer cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}


	public Long getIdProducto() {
		return idProducto;
	}


	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}


	public Long getIdRestaurante() {
		return idRestaurante;
	}


	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}


	public Integer getCantidad() {
		return cantidad;
	}


	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
}