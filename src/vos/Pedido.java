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
	private Date fecha;
	
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="valorTotal")
	private Double valorTotal;
	
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="idMenu")
	private Long idMenu;
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="idUsuario")
	private Long idUsuario;


	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Pedido(@JsonProperty(value="id")Long id,@JsonProperty(value="fecha")Date fecha, @JsonProperty(value="valorTotal")Double valorTotal,@JsonProperty(value="idMenu")Long idMenu,@JsonProperty(value="idUsuario")Long idUsuario)
	{
		super();
		this.id = id;
		this.fecha = fecha;
		this.valorTotal = valorTotal;
		this.idMenu = idMenu;
		this.idUsuario = idUsuario;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Double getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}


	public Long getIdMenu() {
		return idMenu;
	}


	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}


	public Long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
}

