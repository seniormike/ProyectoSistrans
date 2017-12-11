package vos;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class ListaProductos {
	
	/**
	 * Lista de productos.
	 */
	@JsonProperty(value="productos")
	private List<Producto> productos;
	
	/**
	 * Constructor.
	 * @param productos
	 */
	public ListaProductos( @JsonProperty(value="productos")List<Producto> productos)
	{
		this.productos = productos;
	}

	/**
	 * Dar productos
	 * @return
	 */
	public List<Producto> getProductos()
	{
		return productos;
	}

	/**
	 * Set productos
	 * @param productos
	 */
	public void setProductos(List<Producto> productos)
	{
		this.productos = productos;
	}
	
}
