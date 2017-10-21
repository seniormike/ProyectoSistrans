package vos;
import org.codehaus.jackson.annotate.*;

public class TipoComida
{

	

	/**
	 * Identificador del tipo Comida. 
	 */
	@JsonProperty(value="id")
	private Long id;
	
	/**
	 * Nombre del producto 
	 */
	@JsonProperty(value="idProducto")
	private Long idProducto;

	/**
	 * Tipo de comida.
	 */
	@JsonProperty(value="tipoComida")
	private String tipoComida;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public TipoComida(@JsonProperty(value="id")Long idTipoComida,@JsonProperty(value="idProducto")Long idProducto, @JsonProperty(value="tipoComida")String tipoComida)
	{
		super();
		this.id = idTipoComida;
		this.idProducto = idProducto;
		this.tipoComida = tipoComida;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	
}

