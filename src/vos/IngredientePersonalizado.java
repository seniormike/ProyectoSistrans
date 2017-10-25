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

	public IngredientePersonalizado(@JsonProperty(value="nombreIngrediente")String nombreIngrediente,
<<<<<<< HEAD:src/vos/IngredientePersonalizado.java
			                        @JsonProperty(value="idProducto") long idProducto) {
=======
			                                @JsonProperty(value="idProducto") long idProducto, @JsonProperty(value="id")long id) {
>>>>>>> b88f904597760b7185f50a6805a934b2d22c589c:src/vos/IngredientePersonalizado.java
		super();
		this.nombreIngrediente = nombreIngrediente;
		this.idProducto = idProducto;
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
}
