package vos;
import org.codehaus.jackson.annotate.*;

public class Ingrediente
{

	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	


	/**
	 * Tipo de restaurante.
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	/**
	 * Traduccion al ingles de la descripcion.
	 */
	@JsonProperty(value="idescripcion")
	private String idescripcion;




	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Ingrediente(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="descripcion")String descripcion,@JsonProperty(value="idescripcion")String idescripcion)
	{
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.idescripcion = idescripcion;
	}




	public String getIdescripcion() {
		return idescripcion;
	}




	public void setIdescripcion(String idescripcion) {
		this.idescripcion = idescripcion;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public String getDescripcion() {
		return descripcion;
	}




	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}





}

