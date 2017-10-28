package vos;
import org.codehaus.jackson.annotate.*;

public class Zona
{

	/**
	 * Id de la zona.
	 */
	@JsonProperty(value="id")
	private Long id;

	/**
	 * Tipo de zona.
	 */
	@JsonProperty(value="tipoEspacio")
	private String tipoEspacio;
	/**
	 * Capacidad de la zona.
	 */
	@JsonProperty(value="capacidad")
	private Integer capacidad;
	/**
	 * Tipo de restaurante.
	 */
	@JsonProperty(value="discapacitados")
	private String discapacitados;

	
	@JsonProperty(value="condicionesTecnicas")
	private String condicionesTecnicas;
	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Zona(@JsonProperty(value="id")Long id, @JsonProperty(value="tipoEspacio")String tipoEspacio, @JsonProperty(value="capacidad")Integer capacidad, @JsonProperty(value="discapacitados")String discapacitados,@JsonProperty(value="condicionesTecnicas")String condicionesTecnicas)
	{
		this.id = id;
		this.tipoEspacio = tipoEspacio;
		this.capacidad = capacidad;
		this.discapacitados = discapacitados;
		this.condicionesTecnicas = condicionesTecnicas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoEspacio() {
		return tipoEspacio;
	}

	public void setTipoEspacio(String tipoEspacio) {
		this.tipoEspacio = tipoEspacio;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public String getDiscapacitados() {
		return discapacitados;
	}

	public void setDiscapacitados(String discapacitados) {
		this.discapacitados = discapacitados;
	}

	public String getCondicionesTecnicas() {
		return condicionesTecnicas;
	}

	public void setCondicionesTecnicas(String condicionesTecnicas) {
		this.condicionesTecnicas = condicionesTecnicas;
	}
	
}