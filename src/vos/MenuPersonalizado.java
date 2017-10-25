package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MenuPersonalizado {
	@JsonProperty(value="id")
	private long id;
	
	@JsonProperty(value="precio")
	private double precio;
	
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
	
	@JsonProperty(value="idMenu")
	private long idMenu;

	/**
	 * 
	 * @param id
	 * @param precio
	 * @param entrada
	 * @param platoFuerte
	 * @param postre
	 * @param bebida
	 * @param acompanamiento
	 */
	public MenuPersonalizado(@JsonProperty(value="id")long id,@JsonProperty(value="precio")double precio,
			    @JsonProperty(value="entrada") long entrada,@JsonProperty(value="platoFuerte") long platoFuerte,
	            @JsonProperty(value="postre")long postre, @JsonProperty(value="bebida") long bebida,
	            @JsonProperty(value="acompanamiento") long acompanamiento,	@JsonProperty(value="idMenu")long idMenu)
	{
		super();
		this.id = id;
		this.precio = precio;
		this.entrada = entrada;
		this.platoFuerte = platoFuerte;
		this.postre = postre;
		this.bebida = bebida;
		this.acompanamiento = acompanamiento;
		this.idMenu = idMenu;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
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

	public long getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(long idMenu) {
		this.idMenu = idMenu;
	}
	
}