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
	@JsonProperty(value="idRestaurante")
	private Long idRestaurante;
	
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="idescription")
	private String idescription;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Producto(@JsonProperty(value="id")Long id,@JsonProperty(value="nombre")String nombre, @JsonProperty(value="descripcion")String descripcion,@JsonProperty(value="idescription")String idescripcion,@JsonProperty(value="tiempoPreparacion")Integer tiempoPreparacion, @JsonProperty(value="costoProduccion")Double costoProduccion,@JsonProperty(value="precioVenta")Double precioVenta,@JsonProperty(value="disponible")String disponible,@JsonProperty(value="clasificacion")String clasificacion,@JsonProperty(value="idRestaurante")Long idRestaurante)
	{
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.idescription = idescripcion;
		this.tiempoPreparacion = tiempoPreparacion;
		this.costoProduccion = costoProduccion;
		this.precioVenta = precioVenta;
		this.disponible = disponible;
		this.clasificacion = clasificacion;
		this.idRestaurante = idRestaurante;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdescripcion() {
		return idescription;
	}

	public void setIdescripcion(String idescripcion) {
		this.idescription = idescripcion;
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

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public void setNombreRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}	
	
}
