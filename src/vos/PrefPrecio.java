package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PrefPrecio 
{
	@JsonProperty(value="idUsuario")
	private Long udUsuario;
	
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="Max")
	private Double PrecioMax;
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="Min")
	private Double PrecioMin;

	public PrefPrecio(@JsonProperty(value="idUsuario")Long udUsuario, @JsonProperty(value="Max")Double precioMax, @JsonProperty(value="Min")Double precioMin) {
		super();
		this.udUsuario = udUsuario;
		this.PrecioMax = precioMax;
		this.PrecioMin = precioMin;
	}

	public Long getUdUsuario() {
		return udUsuario;
	}

	public void setUdUsuario(Long udUsuario) {
		this.udUsuario = udUsuario;
	}

	public Double getPrecioMax() {
		return PrecioMax;
	}

	public void setPrecioMax(Double precioMax) {
		PrecioMax = precioMax;
	}

	public Double getPrecioMin() {
		return PrecioMin;
	}

	public void setPrecioMin(Double precioMin) {
		PrecioMin = precioMin;
	}
	
}
