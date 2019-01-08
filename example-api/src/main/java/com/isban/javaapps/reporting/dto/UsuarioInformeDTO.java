package com.isban.javaapps.reporting.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UsuarioInformeDTO implements Serializable {

	public static final long serialVersionUID = -5223849039413378944L;
    
	public String identificador;
	public String codigoPais;
	public String codigoEntidad;
	public String numeroPersona;
	public String TIP_DOC_ID;
	public String numeroDocumento;
	public String SEC_NUM_DOC;
	public String nombre;
    public String apellidoPaterno;
    public String apellidoMaterno;    
	public String pais;
	public String tipoPersona;	
	public String codCondicionesCli;
	public String codSituacionCli;	
	public String estadoCliente;
	public String codVinculaCliente;	
	public String codCentroContable;
	public String codOficinaCliente;
    public String sexo;    
    public String codigoSegmento;
    public String codigoSegmentoNegocio;
    public String codigoSegmentoRiesgo;
    public String codSectorContable;
    public String codSectorActividad;    
    public String indEmpleado;
    public String indBanca;
    public String indConfidencialidad;
    public String indCarterizado;    
    public String fechaAlta;
    public String fechaBaja;
    public String fechaData;
    public String fechaAltaODS;
    public String codigoProceso;
    public String usuario;
    public String TIMEST_UMO;
    public String codigoCartera;
	    
    
    
    public UsuarioInformeDTO() {
        
    }



	public UsuarioInformeDTO(Object[] baseProduct, boolean b) {
		// TODO Auto-generated constructor stub
	}
    

}
