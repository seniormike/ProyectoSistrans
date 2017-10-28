package vos;
import org.codehaus.jackson.annotate.*;

public class PreferenciaZona
{

	/**
	 * Id de la zona.
	 */
	@JsonProperty(value="idUsuario")
	private Long idUsuario;
	
	@JsonProperty(value="idZona")
	private Long idZona;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public PreferenciaZona(@JsonProperty(value="id")Long idUsuario, @JsonProperty(value="idZona")Long idZona)
	{
		this.idUsuario = idUsuario;
		this.idZona = idZona;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	
}