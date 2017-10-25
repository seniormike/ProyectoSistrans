package vos;
import org.codehaus.jackson.annotate.*;

public class Ingrediente
{

	/**
	 * Nombre del Ingrediente.
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	/**
	 * Descripcion Ingrediente.
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	/**
	 * Traduccion al ingles de la descripcion.
	 */
	@JsonProperty(value="idescription")
	private String idescription;

	/**
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param idescription
	 */
	public Ingrediente(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="descripcion")String descripcion,@JsonProperty(value="idescription")String idescription)
	{
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.idescription = idescription;
	}
	
	/**
	 * 
	 * @return idescription
	 */
	public String getIdescripcion() {
		return idescription;
	}
	
	/**
	 * 
	 * @param idescripcion
	 */
	public void setIdescripcion(String idescripcion) {
		this.idescription = idescripcion;
	}
	
	/**
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}





}

