package com.krakedev.inventarios.entidades;

import java.util.Date;

public class HistorialStock {
	private int codigo;
	private Date fecha;
	private String referencia;
	private Producto producto_id;
	private int cantidad;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public Producto getProducto_id() {
		return producto_id;
	}
	public void setProducto_id(Producto producto_id) {
		this.producto_id = producto_id;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
