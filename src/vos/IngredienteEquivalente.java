package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngredienteEquivalente 
{
	/**
	 * Nombre del Ingrediente.
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	/**
	 * Descripcion Ingrediente.
	 */
	@JsonProperty(value="idRestaurante")
	private Long idRestaurante;
	
	/**
	 * Traduccion al ingles de la descripcion.
	 */
	@JsonProperty(value="nEquivalencia")
	private String nEquivalencia;

	public IngredienteEquivalente(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="idRestaurante")Long idRestaurante, @JsonProperty(value="nEquivalencia")String nEquivalencia) {
		super();
		this.nombre = nombre;
		this.idRestaurante = idRestaurante;
		this.nEquivalencia = nEquivalencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public String getnEquivalencia() {
		return nEquivalencia;
	}

	public void setnEquivalencia(String nEquivalencia) {
		this.nEquivalencia = nEquivalencia;
	}
	
}
