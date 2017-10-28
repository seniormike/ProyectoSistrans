package vos;

import org.codehaus.jackson.annotate.*;
public class ProductoEquivalente
{
	@JsonProperty(value="idProducto")
	private Long idProducto;
	
	@JsonProperty(value="idRestaurante")
	private Long idRestaurante;
	
	@JsonProperty(value="idProdEquivalente")
	private Long idProdEquivalente;
	
	

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public ProductoEquivalente(@JsonProperty(value="idProducto")Long idProducto,@JsonProperty(value="idRestaurante")Long idRestaurante, @JsonProperty(value="idProdEquivalente")Long idProdEquivalente)

	{
		this.idProdEquivalente = idProdEquivalente;
		this.idProducto = idProducto;
		this.idRestaurante = idRestaurante;
	}



	public Long getIdProducto() {
		return idProducto;
	}



	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}



	public Long getIdRestaurante() {
		return idRestaurante;
	}



	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}



	public Long getIdProdEquivalente() {
		return idProdEquivalente;
	}



	public void setIdProdEquivalente(Long idProdEquivalente) {
		this.idProdEquivalente = idProdEquivalente;
	}

	
}
