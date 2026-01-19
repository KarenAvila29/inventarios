package com.krakedev.inventarios.entidades;

public class TipoDato {
	private String codigo;
	private String descripcion;
	
	
	public TipoDato() {
		super();
	}
	public TipoDato(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "TiposDatos [codigo=" + codigo + ", descripcion=" + descripcion + "]";
	}

	
}
