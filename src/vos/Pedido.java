package vos;
import java.util.Date;

import org.codehaus.jackson.annotate.*;

public class Pedido
{

	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="id")
	private Long id;
	
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="fecha")
	private String fecha;
	
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="valorTotal")
	private Double valorTotal;
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="idUsuario")
	private Long idUsuario;
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="estado")
	private String estado;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Pedido(@JsonProperty(value="id")Long id,@JsonProperty(value="fecha")String fecha, @JsonProperty(value="valorTotal")Double valorTotal,@JsonProperty(value="idUsuario")Long idUsuario, @JsonProperty(value="estado")String estado)
	{
		super();
		this.id = id;
		this.fecha = fecha;
		this.valorTotal = valorTotal;
		this.idUsuario = idUsuario;
		this.estado = estado;
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

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	
	
}

