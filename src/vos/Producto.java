package vos;

import org.codehaus.jackson.annotate.*;
public class Producto
{
	

	/**
	 * Nombre del producto
	 */
	@JsonProperty(value="id")
	private Long id;
	
	/**
	 * Nombre del producto
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Tiempo de preparacion del producto dado en minutos.
	 */
	@JsonProperty(value="tiempoPreparacion")
	private Integer tiempoPreparacion;
	/**
	 * Descripcion del producto
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;
	/**
	 * Costo de produccion del producto.
	 */
	@JsonProperty(value="costoProduccion")
	private Double costoProduccion;
	/**
	 * Precio de venta del producto.
	 */
	@JsonProperty(value="precioVenta")
	private Double precioVenta;
	/**
	 * Disponibilidad del producto.
	 */
	@JsonProperty(value="disponible")
	private String disponible;
	/**
	 * Clasificacion del producto.
	 */
	@JsonProperty(value="clasificacion")
	private String clasificacion;
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="nombreRestaurante")
	private String nombreRestaurante;
	
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="idescripcion")
	private String idescripcion;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Producto(@JsonProperty(value="nombre")String nombre,@JsonProperty(value="tiempoPreparacion")Integer tiempoPreparacion, @JsonProperty(value="descripcion")String descripcion, @JsonProperty(value="costoProduccion")Double costoProduccion,@JsonProperty(value="precioVenta")Double precioVenta,@JsonProperty(value="disponible")String disponible,@JsonProperty(value="clasificacion")String clasificacion,@JsonProperty(value="nombreRestaurante")String nombreRestaurante,@JsonProperty(value="idescripcion")String idescripcion,@JsonProperty(value="id")Long id)
	{
		super();
		this.nombre = nombre;
		this.tiempoPreparacion = tiempoPreparacion;
		this.descripcion = descripcion;
		this.costoProduccion = costoProduccion;
		this.precioVenta = precioVenta;
		this.disponible = disponible;
		this.clasificacion = clasificacion;
		this.nombreRestaurante = nombreRestaurante;
		this.idescripcion = idescripcion;
		this.id = id;
		
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getIdescripcion() {
		return idescripcion;
	}



	public void setIdescripcion(String idescripcion) {
		this.idescripcion = idescripcion;
	}



	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}


	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Double getCostoProduccion() {
		return costoProduccion;
	}


	public void setCostoProduccion(Double costoProduccion) {
		this.costoProduccion = costoProduccion;
	}


	public Double getPrecioVenta() {
		return precioVenta;
	}


	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}


	public String getDisponible() {
		return disponible;
	}


	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}

	public String getClasificacion() {
		return clasificacion;
	}


	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}


	public String getNombreRestaurante() {
		return nombreRestaurante;
	}


	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	
	
}
