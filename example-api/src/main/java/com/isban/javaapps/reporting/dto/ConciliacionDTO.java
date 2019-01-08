package com.isban.javaapps.reporting.dto;

import java.util.Date;

public class ConciliacionDTO {

	// Instance Variables
	private Date fecha;
	private String codigoContenido;
	private String entidad;
	private String codigoCuentaContable;
	private String descripcionCuentaContable;
	private String saldoContable;
	private String saldoOperacional;
	private String codigoDivisa;

	// Mensuales
	private String importeDesviacionMis;
	private String importeDesviacionCoc;

	// Diaria con centro
	private String codigoSistemaOrigen;
	private String codigoCentroContable;
	// Diaria sin centro
//	private String importeDesviacionMis;
	
	// Constructors
	public ConciliacionDTO() {
		
	}

	public ConciliacionDTO(Date fecha, String codigoContenido, String entidad, String codigoCuentaContable, String descripcionCuentaContable, String saldoContable, String saldoOperacional, String codigoDivisa, String importeDesviacionMis, String importeDesviacionCoc, String codigoSistemaOrigen, String codigoCentroContable) {
		this.fecha                     = fecha;
		this.codigoContenido           = codigoContenido;
		this.entidad                   = entidad;
		this.codigoCuentaContable      = codigoCuentaContable;
		this.descripcionCuentaContable = descripcionCuentaContable;
		this.saldoContable             = saldoContable;
		this.saldoOperacional          = saldoOperacional;
		this.codigoDivisa              = codigoDivisa;
		this.importeDesviacionMis      = importeDesviacionMis;
		this.importeDesviacionCoc      = importeDesviacionCoc;
		this.codigoSistemaOrigen       = codigoSistemaOrigen;
		this.codigoCentroContable      = codigoCentroContable;
	}

	// Accessors
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCodigoContenido() {
		return codigoContenido;
	}

	public void setCodigoContenido(String codigoContenido) {
		this.codigoContenido = codigoContenido;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getCodigoCuentaContable() {
		return codigoCuentaContable;
	}

	public void setCodigoCuentaContable(String codigoCuentaContable) {
		this.codigoCuentaContable = codigoCuentaContable;
	}

	public String getDescripcionCuentaContable() {
		return descripcionCuentaContable;
	}

	public void setDescripcionCuentaContable(String descripcionCuentaContable) {
		this.descripcionCuentaContable = descripcionCuentaContable;
	}

	public String getSaldoContable() {
		return saldoContable;
	}

	public void setSaldoContable(String saldoContable) {
		this.saldoContable = saldoContable;
	}

	public String getSaldoOperacional() {
		return saldoOperacional;
	}

	public void setSaldoOperacional(String saldoOperacional) {
		this.saldoOperacional = saldoOperacional;
	}

	public String getCodigoDivisa() {
		return codigoDivisa;
	}

	public void setCodigoDivisa(String codigoDivisa) {
		this.codigoDivisa = codigoDivisa;
	}

	public String getImporteDesviacionMis() {
		return importeDesviacionMis;
	}

	public void setImporteDesviacionMis(String importeDesviacionMis) {
		this.importeDesviacionMis = importeDesviacionMis;
	}

	public String getImporteDesviacionCoc() {
		return importeDesviacionCoc;
	}

	public void setImporteDesviacionCoc(String importeDesviacionCoc) {
		this.importeDesviacionCoc = importeDesviacionCoc;
	}

	public String getCodigoSistemaOrigen() {
		return codigoSistemaOrigen;
	}

	public void setCodigoSistemaOrigen(String codigoSistemaOrigen) {
		this.codigoSistemaOrigen = codigoSistemaOrigen;
	}

	public String getCodigoCentroContable() {
		return codigoCentroContable;
	}

	public void setCodigoCentroContable(String codigoCentroContable) {
		this.codigoCentroContable = codigoCentroContable;
	}

}
