package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngredientePersonalizado 
{
	/**
	 * Nombre del Ingrediente.
	 */
	@JsonProperty(value="nombreIngrediente")
	private String nombreIngrediente;
	


	/**
	 * Id del Producto.
	 */
	@JsonProperty(value="idProducto")
	private long idProducto;
	
	/**
	 * id
	 */
	@JsonProperty(value="id")
	private long id;
	

	public IngredientePersonalizado(@JsonProperty(value="nombreIngrediente")String nombreIngrediente,
			                                @JsonProperty(value="idProducto") long idProducto, @JsonProperty(value="id")long id) {
		super();
		this.nombreIngrediente = nombreIngrediente;
		this.idProducto = idProducto;
		this.id = id;
	}

	public String getNombreIngrediente() {
		return nombreIngrediente;
	}

	public void setNombreIngrediente(String nombreIngrediente) {
		this.nombreIngrediente = nombreIngrediente;
	}

	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
