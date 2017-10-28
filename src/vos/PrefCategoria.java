package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PrefCategoria 
{
	@JsonProperty(value="idUsuario")
	private Long idUsuario;
	
	
	/**
	 * Nombre del Restaurante.
	 */
	@JsonProperty(value="Categoria")
	private String categoria;


	public PrefCategoria(Long idUsuario, String categoria) {
		super();
		this.idUsuario = idUsuario;
		this.categoria = categoria;
	}


	public Long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
