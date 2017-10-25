package vos;
import org.codehaus.jackson.annotate.*;

public class Restaurante
{
	/**
	 * ID del restaurante
	 */
	@JsonProperty(value="idRestaurante")
	private long idRestaurante;
	
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
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="nombreRepresentante")
	private String nombreRepresentante;

	/**
	 * Identificador de la zona que corresponde al restaurante.
	 */
	@JsonProperty(value="idZona")
	private Long idZona;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Restaurante(@JsonProperty(value="idRestaurante")long idRestaurante, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="tipo")String tipo,@JsonProperty(value="paginaWeb")String paginaWeb,@JsonProperty(value="nombreRepresentante")String nombreRepresentante, @JsonProperty(value="idZona")Long idZona)
	{
		super();
		this.idRestaurante = idRestaurante;
		this.nombre = nombre;
		this.tipo = tipo;
		this.paginaWeb = paginaWeb;
		this.nombreRepresentante = nombreRepresentante;
		this.idZona = idZona;
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

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

}
