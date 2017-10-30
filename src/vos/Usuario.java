package vos;
import org.codehaus.jackson.annotate.*;

public class Usuario
{

	/**
	 * Id del usuario.
	 */
	@JsonProperty(value="id")
	private Long id;

	/**
	 * Nombre del usuario.
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	/**
	 * Capacidad de la zona.
	 */
	@JsonProperty(value="tipo")
	private String tipo;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Usuario(@JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="tipo")String tipo)
	{
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public boolean esAdministrador()
	{
		if (tipo.equals("Administrador"))
		{
			return true;
		}
		return false;
	}

}