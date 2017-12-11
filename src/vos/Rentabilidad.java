package vos;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class Rentabilidad {
	
	/**
	 * Lista de productos.
	 */
	@JsonProperty(value="productos")
	private String productos;
	
	@JsonProperty(value="ganancia")
	private double ganancia;
	
	/**
	 * Constructor.
	 * @param productos
	 */
	public Rentabilidad( @JsonProperty(value="productos")String productos, @JsonProperty(value="ganancia") double ganancia)
	{
		this.productos = productos;
		this.ganancia = ganancia;
	}

	/**
	 * Dar productos
	 * @return
	 */
	public String getProductos()
	{
		return productos;
	}

	/**
	 * Set productos
	 * @param productos
	 */
	public void setProductos(String productos)
	{
		this.productos = productos;
	}
	
	public double getGanancia()
	{
		return ganancia;
	}

	/**
	 * Set productos
	 * @param productos
	 */
	public void setGanancia(double ga)
	{
		this.ganancia = ga;
	}
	
}
