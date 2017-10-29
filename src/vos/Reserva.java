package vos;

import org.codehaus.jackson.annotate.*;


public class Reserva
{

	/**
	 * Id de la reserva.
	 */
	@JsonProperty(value="id")
	private Long id;
	/**
	 * Id de la reserva.
	 */
	@JsonProperty(value="fecha")
	private String fecha;
	/**
	 * Id de la reserva.
	 */
	@JsonProperty(value="hora")
	private String hora;
	/**
	 * Id de la reserva.
	 */
	@JsonProperty(value="numComensales")
	private Integer numComensales;
	/**
	 * Id de la reserva.
	 */
	@JsonProperty(value="estado")
	private String estado;
	/**
	 * Id de la reserva.
	 */
	@JsonProperty(value="idZona")
	private Long idZona;
	/**
	 * Id de la reserva.
	 */
	@JsonProperty(value="idUsuario")
	private Long idUsuario;
	/**
	 * Id de la reserva.
	 */
	@JsonProperty(value="idMenu")
	private Long idMenu;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Reserva(@JsonProperty(value="id")Long id, @JsonProperty(value="fecha")String fecha, @JsonProperty(value="hora")String hora, @JsonProperty(value="numComensales")Integer numComensales, @JsonProperty(value="estado")String estado, @JsonProperty(value="idZona")Long idZona, @JsonProperty(value="idUsuario")Long idUsuario, @JsonProperty(value="idMenu")Long idMenu)
	{
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.numComensales = numComensales;
		this.estado = estado;
		this.idZona = idZona;
		this.idUsuario = idUsuario;
		this.idMenu = idMenu;
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Integer getNumComensales() {
		return numComensales;
	}

	public void setNumComensales(Integer numComensales) {
		this.numComensales = numComensales;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}

}

