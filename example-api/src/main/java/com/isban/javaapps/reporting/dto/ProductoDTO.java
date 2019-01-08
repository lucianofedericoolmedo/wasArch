package com.isban.javaapps.reporting.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductoDTO implements Serializable {

    private static final long serialVersionUID = -5223849039413378944L;
    
    private String codigoPais; 
    private String codigoProducto; 
    private String codigoSubproducto;
    private String codigoProductoNormalizado;
    private String codigoSubproductoNormalizado;
    private String fechaAlta;
    private String fechaBaja;
    private String usuario;
    private String descripcion;
    private String codigoSistemaOrigen;
    private String productoDestinatario;
    private String codigoAplicativo;
    
    public ProductoDTO() {
        
    }

    public ProductoDTO(Object[] baseObject, boolean isOperacional) {
        if(isOperacional) {
            this.setCodigoPais(baseObject[5].toString());
            this.setCodigoSistemaOrigen((String)baseObject[4]);
            this.setCodigoProducto((String)baseObject[0]);
            this.setCodigoSubproducto((String)baseObject[1]);
            this.setCodigoProductoNormalizado((String)baseObject[2]);
            this.setCodigoSubproductoNormalizado((String)baseObject[3]);
            this.setFechaAlta(baseObject[7].toString());
            this.setFechaBaja(baseObject[8] != null ? baseObject[8].toString(): "");
            this.setDescripcion((String)baseObject[6]);
        }else {
            this.codigoPais = baseObject[0] == null ? null : baseObject[0].toString();
            this.setCodigoAplicativo((String)baseObject[1]);
            this.setCodigoProducto((String)baseObject[2]);
            this.setCodigoSubproducto((String)baseObject[3]);
            this.setCodigoProductoNormalizado((String)baseObject[4]);
            this.setCodigoSubproductoNormalizado((String)baseObject[5]);
            this.setFechaAlta(baseObject[6].toString());
            this.setFechaBaja(baseObject[7] != null ? baseObject[7].toString(): "");
            this.setDescripcion((String)baseObject[8]);
        }
        
    }

    public ProductoDTO(Object[] baseObject) {
    	this.setCodigoPais(baseObject[5].toString());
    	this.setCodigoSistemaOrigen((String)baseObject[4]);
    	this.setCodigoProducto((String)baseObject[1]);
    	this.setCodigoSubproducto((String)baseObject[0]);
    	this.setCodigoProductoNormalizado((String)baseObject[2]);
    	this.setCodigoSubproductoNormalizado((String)baseObject[3]);
    	this.setFechaAlta(baseObject[7].toString());
    	this.setFechaBaja(baseObject[8] != null ? baseObject[8].toString() : "");
    	this.setDescripcion((String)baseObject[6]);
    }

    public String getCodigoAplicativo() {
        return codigoAplicativo;
    }
    
    public void setCodigoAplicativo(String codigoAplicativo) {
        this.codigoAplicativo = codigoAplicativo;
    }

    public String getCodigoPais() {
        return codigoPais;
    }
    
    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }
    
    public String getCodigoProducto() {
        return codigoProducto;
    }
    
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    public void setCodigoProductoOperacional(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    public String getCodigoSubproducto() {
        return codigoSubproducto;
    }
    
    public void setCodigoSubproducto(String codigoSubproducto) {
        this.codigoSubproducto = codigoSubproducto;
    }
    
    public void setCodigoSubproductoOperacional(String codigoSubproducto) {
        this.codigoSubproducto = codigoSubproducto;
    }
    
    public String getFechaAlta() {
        return fechaAlta;
    }
    
    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    public String getFechaBaja() {
        return fechaBaja;
    }
    
    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getCodigoSubproductoNormalizado() {
        return codigoSubproductoNormalizado;
    }

    public void setCodigoSubproductoNormalizado(String codigoSubproductoNormalizado) {
        this.codigoSubproductoNormalizado = codigoSubproductoNormalizado;
    }
    
    public String getCodigoProductoNormalizado() {
        return codigoProductoNormalizado;
    }
    
    public void setCodigoProductoNormalizado(String codigoProductoNormalizado) {
        this.codigoProductoNormalizado = codigoProductoNormalizado;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getCodigoSistemaOrigen() {
        return codigoSistemaOrigen;
    }

    public void setCodigoSistemaOrigen(String codigoSistemaOrigen) {
        this.codigoSistemaOrigen = codigoSistemaOrigen;
    }

    public String getProductoDestinatario() {
        return productoDestinatario;
    }

    public void setProductoDestinatario(String productoDestinatario) {
        this.productoDestinatario = productoDestinatario;
    }

}
