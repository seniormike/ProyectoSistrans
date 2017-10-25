package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MenuPedido 
{

	/**
	 * Nombre del Ingrediente.
	 */
	@JsonProperty(value="idPedido")
	private Long idPedido;
	/**
	 * Descripcion Ingrediente.
	 */
	@JsonProperty(value="idMenuPer")
	private Long idMenuPer;
	
	/**
	 * Traduccion al ingles de la descripcion.
	 */
	@JsonProperty(value="cantidad")
	private int cantidad;

	public MenuPedido(@JsonProperty(value="idPedido")Long idPedido, 
			@JsonProperty(value="idMenuPer")Long idMenuPer, @JsonProperty(value="cantidad")int cantidad) {
		super();
		this.idPedido = idPedido;
		this.idMenuPer = idMenuPer;
		this.cantidad = cantidad;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Long getIdMenuPer() {
		return idMenuPer;
	}

	public void setIdMenuPer(Long idMenuPer) {
		this.idMenuPer = idMenuPer;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	

}
