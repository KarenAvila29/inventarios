package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class Producto {
	private String codigo_pro;
	private String nombre;
	private UnidadDeMedida unidadMedida;
	private BigDecimal precioVenta;
	private boolean tieneIva;
	private BigDecimal coste;
	private Categoria categoria;
	private int stock;
	
	
	public Producto() {
		super();
	}


	public Producto(String codigo_pro, String nombre, UnidadDeMedida unidadMedida, BigDecimal precioVenta,
			boolean tieneIva, BigDecimal coste, Categoria categoria, int stock) {
		super();
		this.codigo_pro = codigo_pro;
		this.nombre = nombre;
		this.unidadMedida = unidadMedida;
		this.precioVenta = precioVenta;
		this.tieneIva = tieneIva;
		this.coste = coste;
		this.categoria = categoria;
		this.stock = stock;
	}



	public String getCodigo_pro() {
		return codigo_pro;
	}


	public void setCodigo_pro(String codigo_pro) {
		this.codigo_pro = codigo_pro;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public UnidadDeMedida getUnidadMedida() {
		return unidadMedida;
	}


	public void setUnidadMedida(UnidadDeMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}


	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}


	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}


	public boolean isTieneIva() {
		return tieneIva;
	}


	public void setTieneIva(boolean tieneIva) {
		this.tieneIva = tieneIva;
	}


	public BigDecimal getCoste() {
		return coste;
	}


	public void setCoste(BigDecimal coste) {
		this.coste = coste;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	@Override
	public String toString() {
		return "Producto [codigo_pro=" + codigo_pro + ", nombre=" + nombre + ", unidadMedida=" + unidadMedida
				+ ", precioVenta=" + precioVenta + ", tieneIva=" + tieneIva + ", coste=" + coste + ", categoria="
				+ categoria + ", stock=" + stock + "]";
	}
	
	

}
