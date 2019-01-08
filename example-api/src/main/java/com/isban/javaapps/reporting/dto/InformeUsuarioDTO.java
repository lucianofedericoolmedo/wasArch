package com.isban.javaapps.reporting.dto;

import java.math.BigDecimal;

public class InformeUsuarioDTO {

	private String identificador;
	private String codigoPais;
	private String codigoEntidad;
    private String numeroPersona;
    private String TIP_DOC_ID;
    private String numeroDocumento;
    private String SEC_NUM_DOC;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String pais;
    private Character tipoPersona;
    private String codCondicionesCli;
    private String codSituacionCli;
    private String estadoCliente;
    private String codVinculaCliente;
    private String codCentroContable;
    private String codOficinaCliente;
    private Character sexo;
    private String codigoSegmento;
    private String codigoSegmentoNegocio;
    private String codigoSegmentoRiesgo;
    private String codSectorContable;
    private String codSectorActividad;
    private Character indEmpleado;
    private Character indBanca;
    private String indConfidencialidad;
    private String indCarterizado;
    private String fechaAlta;
    private String fechaBaja;
    private String fechaData;
    private String fechaAltaODS;
    private String codigoProceso;
    private String usuario;
    private String TIMEST_UMO;
    private String codigoCartera;
    
    // Constructors
    public InformeUsuarioDTO() {

	}
    
    public InformeUsuarioDTO(Object[] rawInformeUsuario) {
    	this.setIdentificador((String) rawInformeUsuario[0]);
    	this.setCodigoPais((BigDecimal) rawInformeUsuario[1]);
    	this.setCodigoEntidad((String) rawInformeUsuario[2]);
    	this.setNumeroPersona((String) rawInformeUsuario[3]);
    	this.setTIP_DOC_ID((String) rawInformeUsuario[4]);
    	this.setNumeroDocumento((String) rawInformeUsuario[5]);
    	this.setSEC_NUM_DOC((String) rawInformeUsuario[6]);
    	this.setNombre((String) rawInformeUsuario[7]);
    	this.setApellidoPaterno((String) rawInformeUsuario[8]);
    	this.setApellidoMaterno((String) rawInformeUsuario[9]);
    	this.setPais((String) rawInformeUsuario[10]);
    	this.setTipoPersona((Character) rawInformeUsuario[11]);
    	this.setCodCondicionesCli((String) rawInformeUsuario[12]);
    	this.setCodSituacionCli((String) rawInformeUsuario[13]);
    	this.setEstadoCliente((String) rawInformeUsuario[14]);
    	this.setCodVinculaCliente((String) rawInformeUsuario[15]);
    	this.setCodCentroContable((String) rawInformeUsuario[16]);
    	this.setCodOficinaCliente((String) rawInformeUsuario[17]);
    	this.setSexo((Character) rawInformeUsuario[18]);
    	this.setCodigoSegmento((String) rawInformeUsuario[19]);
    	this.setCodigoSegmentoNegocio((String) rawInformeUsuario[20]);
    	this.setCodigoSegmentoRiesgo((String) rawInformeUsuario[21]);
    	this.setCodSectorContable((String) rawInformeUsuario[22]);
    	this.setCodSectorActividad((String) rawInformeUsuario[23]);
    	this.setIndEmpleado((Character) rawInformeUsuario[24]);
    	this.setIndBanca((Character) rawInformeUsuario[25]);
    	this.setIndConfidencialidad((String) rawInformeUsuario[26]);
    	this.setIndCarterizado((String) rawInformeUsuario[27]);
    	this.setFechaAlta((String) rawInformeUsuario[28]);
    	this.setFechaBaja((String) rawInformeUsuario[29]);
    	this.setFechaData((String) rawInformeUsuario[30]);
    	this.setFechaAltaODS((String) rawInformeUsuario[31]);
    	this.setCodigoProceso((String) rawInformeUsuario[32]);
    	this.setUsuario((String) rawInformeUsuario[33]);
    	this.setTIMEST_UMO((String) rawInformeUsuario[34]);
    	this.setCodigoCartera((String) rawInformeUsuario[35]);
	}
    
    // Accessors
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getCodigoPais() {
		return codigoPais;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}
	public void setCodigoPais(BigDecimal codigoPais) {
		this.codigoPais = codigoPais != null ? codigoPais.toString() : null;
	}
	public void setCodigoPais(Integer codigoPais) {
		this.codigoPais = codigoPais.toString();
	}
	public String getCodigoEntidad() {
		return codigoEntidad;
	}
	public void setCodigoEntidad(String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}
	public String getNumeroPersona() {
		return numeroPersona;
	}
	public void setNumeroPersona(String numeroPersona) {
		this.numeroPersona = numeroPersona;
	}
	public String getTIP_DOC_ID() {
		return TIP_DOC_ID;
	}
	public void setTIP_DOC_ID(String tIP_DOC_ID) {
		TIP_DOC_ID = tIP_DOC_ID;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getSEC_NUM_DOC() {
		return SEC_NUM_DOC;
	}
	public void setSEC_NUM_DOC(String sEC_NUM_DOC) {
		SEC_NUM_DOC = sEC_NUM_DOC;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Character getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(Character tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getCodCondicionesCli() {
		return codCondicionesCli;
	}
	public void setCodCondicionesCli(String codCondicionesCli) {
		this.codCondicionesCli = codCondicionesCli;
	}
	public String getCodSituacionCli() {
		return codSituacionCli;
	}
	public void setCodSituacionCli(String codSituacionCli) {
		this.codSituacionCli = codSituacionCli;
	}
	public String getEstadoCliente() {
		return estadoCliente;
	}
	public void setEstadoCliente(String estadoCliente) {
		this.estadoCliente = estadoCliente;
	}
	public String getCodVinculaCliente() {
		return codVinculaCliente;
	}
	public void setCodVinculaCliente(String codVinculaCliente) {
		this.codVinculaCliente = codVinculaCliente;
	}
	public String getCodCentroContable() {
		return codCentroContable;
	}
	public void setCodCentroContable(String codCentroContable) {
		this.codCentroContable = codCentroContable;
	}
	public String getCodOficinaCliente() {
		return codOficinaCliente;
	}
	public void setCodOficinaCliente(String codOficinaCliente) {
		this.codOficinaCliente = codOficinaCliente;
	}
	public Character getSexo() {
		return sexo;
	}
	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}
	public String getCodigoSegmento() {
		return codigoSegmento;
	}
	public void setCodigoSegmento(String codigoSegmento) {
		this.codigoSegmento = codigoSegmento;
	}
	public String getCodigoSegmentoNegocio() {
		return codigoSegmentoNegocio;
	}
	public void setCodigoSegmentoNegocio(String codigoSegmentoNegocio) {
		this.codigoSegmentoNegocio = codigoSegmentoNegocio;
	}
	public String getCodigoSegmentoRiesgo() {
		return codigoSegmentoRiesgo;
	}
	public void setCodigoSegmentoRiesgo(String codigoSegmentoRiesgo) {
		this.codigoSegmentoRiesgo = codigoSegmentoRiesgo;
	}
	public String getCodSectorActividad() {
		return codSectorActividad;
	}
	public void setCodSectorActividad(String codSectorActividad) {
		this.codSectorActividad = codSectorActividad;
	}
	public Character getIndEmpleado() {
		return indEmpleado;
	}
	public void setIndEmpleado(Character indEmpleado) {
		this.indEmpleado = indEmpleado;
	}
	public Character getIndBanca() {
		return indBanca;
	}
	public void setIndBanca(Character indBanca) {
		this.indBanca = indBanca;
	}
	public String getIndConfidencialidad() {
		return indConfidencialidad;
	}
	public void setIndConfidencialidad(String indConfidencialidad) {
		this.indConfidencialidad = indConfidencialidad;
	}
	public String getIndCarterizado() {
		return indCarterizado;
	}
	public void setIndCarterizado(String indCarterizado) {
		this.indCarterizado = indCarterizado;
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
	public String getFechaData() {
		return fechaData;
	}
	public void setFechaData(String fechaData) {
		this.fechaData = fechaData;
	}
	public String getFechaAltaODS() {
		return fechaAltaODS;
	}
	public void setFechaAltaODS(String fechaAltaODS) {
		this.fechaAltaODS = fechaAltaODS;
	}
	public String getCodigoProceso() {
		return codigoProceso;
	}
	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTIMEST_UMO() {
		return TIMEST_UMO;
	}
	public void setTIMEST_UMO(String tIMEST_UMO) {
		TIMEST_UMO = tIMEST_UMO;
	}
	public String getCodigoCartera() {
		return codigoCartera;
	}
	public void setCodigoCartera(String codigoCartera) {
		this.codigoCartera = codigoCartera;
	}
	public String getCodSectorContable() {
		return codSectorContable;
	}
	public void setCodSectorContable(String codSectorContable) {
		this.codSectorContable = codSectorContable;
	}

}
