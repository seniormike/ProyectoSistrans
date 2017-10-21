package vos;
import org.codehaus.jackson.annotate.*;

public class Restaurante
{

	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="nombre")
	private String nombre;


	/**
	 * Tipo de restaurante.
	 */
	@JsonProperty(value="tipo")
	private String tipo;

	/**
	 * Pagina web del restaurante.
	 */
	@JsonProperty(value="paginaWeb")
	private String paginaWeb;

	/**
	 * Identificador del representante.
	 */
	@JsonProperty(value="idRepresentante")
	private Long idRepresentante;

	/**
	 * Identificador de la zona que corresponde al restaurante.
	 */
	@JsonProperty(value="idZona")
	private Long idZona;

	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="nombreRepresentante")
	private String nombreRepresentante;


	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Restaurante(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="tipo")String tipo,@JsonProperty(value="paginaWeb")String paginaWeb, @JsonProperty(value="idRepresentante")Long idRepresentante, @JsonProperty(value="idZona")Long idZona,@JsonProperty(value="nombreRepresentante")String nombreRepresentante)
	{
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.paginaWeb = paginaWeb;
		this.idRepresentante = idRepresentante;
		this.idZona = idZona;
		this.nombreRepresentante = nombreRepresentante;
	}




	public String getNombreRepresentante() {
		return nombreRepresentante;
	}




	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getPaginaWeb() {
		return paginaWeb;
	}


	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}


	public Long getIdRepresentante() {
		return idRepresentante;
	}


	public void setIdRepresentante(Long idRepresentante) {
		this.idRepresentante = idRepresentante;
	}


	public Long getIdZona() {
		return idZona;
	}


	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}




}
