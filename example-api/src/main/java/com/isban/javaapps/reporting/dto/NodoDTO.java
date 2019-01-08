package com.isban.javaapps.reporting.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NodoDTO implements Serializable {

	private static final long serialVersionUID = -6905450304433367797L;

	public NodoDTO() {
        
    }
	
	private String descripcion;
	private String valorPadre;
	private String nivelPadre;
	private String valor;
	private String codigoJerarquia;
	private String codigoDimension;

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getValorPadre() {
		return valorPadre;
	}
	public void setValorPadre(String valorPadre) {
		this.valorPadre = valorPadre;
	}
	public String getNivelPadre() {
		return nivelPadre;
	}
	public void setNivelPadre(String nivelPadre) {
		this.nivelPadre = nivelPadre;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public void setValorNodo(String valor) {
		this.valor = valor;
	}
	public String getCodigoJerarquia() {
		return codigoJerarquia;
	}
	public void setCodigoJerarquia(String codigoJerarquia) {
		this.codigoJerarquia = codigoJerarquia;
	}
	public String getCodigoDimension() {
		return codigoDimension;
	}
	public void setCodigoDimension(String codigoDimension) {
		this.codigoDimension = codigoDimension;
	}

}
