package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AdministradorRestaurante 
{
	/**
	 * Id del usuario administrador
	 */
	@JsonProperty(value="idUsuario")
	private Long idUsuario;
	/**
	 * Id del restaurante administrado
	 */
	@JsonProperty(value="idRestaurante")
	private Long idRestaurante;
	
	public AdministradorRestaurante(@JsonProperty(value="idUsuario")Long idUsuario, @JsonProperty(value="idRestaurante")Long idRestaurante)
	{
		this.idUsuario = idUsuario;
		this.idRestaurante = idRestaurante;
	}
	public Long getIdUsuario()
	{
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario)
	{
		this.idUsuario = idUsuario;
	}
	public Long getIdRestaurante()
	{
		return idRestaurante;
	}
	public void setIdRestaurante(Long idRestaurante)
	{
		this.idRestaurante = idRestaurante;
	}
	
}
