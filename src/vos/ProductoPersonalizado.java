package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoPersonalizado {
	/**
	 * Nombre del Ingrediente.
	 */
	@JsonProperty(value="idProductoPer")
	private long idProductoPer;

	/**
	 * Id del Producto.
	 */
	@JsonProperty(value="idProducto")
	private long idProducto;

	public ProductoPersonalizado(@JsonProperty(value="idProductoPer")long idProductoPer,
			                        @JsonProperty(value="idProducto") long idProducto) {
		super();
		this.idProductoPer = idProductoPer;
		this.idProducto = idProducto;
	}

	public long getIdProductoPer() {
		return idProductoPer;
	}

	public void setIdProductoPer(long idProductoPer) {
		this.idProductoPer = idProductoPer;
	}

	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

}
