package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu 
{

	@JsonProperty(value="id")
	private long id;
	
	@JsonProperty(value="medioPago")
	private String medioPago;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="idRestaurante")
	private long idRestaurante;
	
	@JsonProperty(value="entrada")
	private long entrada;
	
	@JsonProperty(value="platoFuerte")
	private long platoFuerte;
	
	@JsonProperty(value="postre")
	private long postre;

	@JsonProperty(value="bebida")
	private long bebida;
	
	@JsonProperty(value="acompanamiento")
	private long acompanamiento;

	/**
	 * 
	 * @param id
	 * @param medioPago
	 * @param precio
	 * @param idRestaurante
	 * @param entrada
	 * @param platoFuerte
	 * @param postre
	 * @param bebida
	 * @param acompanamiento
	 */
	public Menu(@JsonProperty(value="id")long id, @JsonProperty(value="medioPago")String medioPago,@JsonProperty(value="precio")double precio,
			    @JsonProperty(value="idRestaurante") long idRestaurante,@JsonProperty(value="entrada") long entrada,@JsonProperty(value="platoFuerte") long platoFuerte,
	            @JsonProperty(value="postre")long postre, @JsonProperty(value="bebida") long bebida,@JsonProperty(value="acompanamiento") long acompanamiento)
	{
		this.id = id;
		this.medioPago = medioPago;
		this.precio = precio;
		this.idRestaurante = idRestaurante;
		this.entrada = entrada;
		this.platoFuerte = platoFuerte;
		this.postre = postre;
		this.bebida = bebida;
		this.acompanamiento = acompanamiento;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public void setNomRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public long getEntrada() {
		return entrada;
	}

	public void setEntrada(long entrada) {
		this.entrada = entrada;
	}

	public long getPlatoFuerte() {
		return platoFuerte;
	}

	public void setPlatoFuerte(long platoFuerte) {
		this.platoFuerte = platoFuerte;
	}

	public long getPostre() {
		return postre;
	}

	public void setPostre(long postre) {
		this.postre = postre;
	}

	public long getBebida() {
		return bebida;
	}

	public void setBebida(long bebida) {
		this.bebida = bebida;
	}

	public long getAcompanamiento() {
		return acompanamiento;
	}

	public void setAcompanamiento(long acompanamiento) {
		this.acompanamiento = acompanamiento;
	}


	
}
