package com.isban.javaapps.reporting.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isban.javaapps.reporting.dto.NodoDTO;
import com.isban.javaapps.reporting.util.DateUtil;

@Service
public class JerarquiaService{
    
	private static final Logger LOGGER = Logger.getLogger(JerarquiaService.class.getName());

	@Autowired
    private EntityManager entityManager;
	
	private String codigoPais = "54";

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Object saveNodo(NodoDTO dto) {
		LOGGER.info("Saving hierarchy node " + dto);
		StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_ODS_JERARQUIAS.Grabar_Nodo")
                .registerStoredProcedureParameter(1, String.class, 
                    ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)
                .registerStoredProcedureParameter(3, Long.class, 
                        ParameterMode.OUT)
                .registerStoredProcedureParameter(4, String.class, 
                        ParameterMode.OUT)
                .registerStoredProcedureParameter(5, String.class, 
                    ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, 
                        ParameterMode.IN)
                .registerStoredProcedureParameter(7, String.class, 
                        ParameterMode.IN)
                .registerStoredProcedureParameter(8, String.class, 
                        ParameterMode.IN)
                .registerStoredProcedureParameter(9, String.class, 
                        ParameterMode.IN)
                .registerStoredProcedureParameter(10, String.class, 
                        ParameterMode.IN)
                .registerStoredProcedureParameter(11, String.class, 
                        ParameterMode.IN)
                .registerStoredProcedureParameter(12, String.class, 
                        ParameterMode.IN)
                .registerStoredProcedureParameter(13, String.class, 
                        ParameterMode.IN)
                .registerStoredProcedureParameter(14, String.class, 
                        ParameterMode.IN)
                .registerStoredProcedureParameter(15, String.class, 
                        ParameterMode.IN)
                
                .setParameter(1, "")
                .setParameter(5, codigoPais)
                .setParameter(6, dto.getCodigoDimension())
                .setParameter(7, dto.getCodigoJerarquia())
                .setParameter(8, dto.getValor())
                .setParameter(9, dto.getValorPadre())
                .setParameter(10, dto.getNivelPadre())
                .setParameter(11, DateUtil.toDateString(DateUtil.toDateStringWithFormat(new Date(), DateUtil.SIMPLE_YEAR_FORMAT)))
				.setParameter(12, "")
				.setParameter(13, "P_ODS")
				.setParameter(14, "PCENTER")
				.setParameter(15, dto.getDescripcion());
		
        boolean isResult = query.execute();
        Object result = null;
        if(isResult) {
            result = query.getOutputParameterValue(2);
            LOGGER.info("Saving result " + result + " for hierarchy node " + dto);
        }
        entityManager.close();

        return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Object deleteNodo(NodoDTO dto) {
		LOGGER.info("Deleting hierarchy node " + dto);
		if(this.existeNodo(dto)) {
			StoredProcedureQuery query = entityManager
	                .createStoredProcedureQuery("PKG_ODS_JERARQUIAS.Eliminar_Nodo")
	                .registerStoredProcedureParameter(1, String.class, 
	                    ParameterMode.IN)
	                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)
	                .registerStoredProcedureParameter(3, Long.class, 
	                        ParameterMode.OUT)
	                .registerStoredProcedureParameter(4, String.class, 
	                        ParameterMode.OUT)
	                .registerStoredProcedureParameter(5, String.class, 
	                    ParameterMode.IN)
	                .registerStoredProcedureParameter(6, String.class, 
	                        ParameterMode.IN)
	                .registerStoredProcedureParameter(7, String.class, 
	                        ParameterMode.IN)
	                .registerStoredProcedureParameter(8, String.class, 
	                        ParameterMode.IN)
	                
	                .setParameter(1, "")
	                .setParameter(5, codigoPais)
	                .setParameter(6, dto.getCodigoDimension())
	                .setParameter(7, dto.getCodigoJerarquia())
	                .setParameter(8, dto.getValor());
			
	        boolean isResult = query.execute();
	        Object result = null;
	        if(isResult) {
	            result = query.getOutputParameterValue(3);
	            LOGGER.info("Deleting result " + result + " for hierarchy node " + dto);
	        }
	        entityManager.close();

	        if(result == null) {
	        	return true;
	        }
	        throw new RuntimeException("Ha ocurrido un error.");
		}else {
			throw new RuntimeException("Dicho nodo no existe");
		}
		
	}
	
	private boolean existeNodo(NodoDTO dto) {
		String sql = "SELECT * FROM HE0_DT_AU_JER_GLOBAL WHERE COD_PAIS = " + codigoPais + 
				" AND COD_DIMENSION = " + dto.getCodigoDimension() + 
				" AND COD_JERARQUIA = " + dto.getCodigoJerarquia() +  
				" AND COD_VALOR = " + dto.getValor();
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> nodos = query.getResultList();
		return !nodos.isEmpty();
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public List<String> getCodigosDimensiones() {
		String sql = "SELECT COD_DIMENSION AS dimension FROM HE0_DT_AU_DIMENSION";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> codigosDimensiones = query.getResultList();
		return codigosDimensiones;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public List<String> getCodigosJerarquias() {
		String sql = "SELECT COD_JERARQUIA AS jerarquia FROM HE0_DT_AU_JERARQUIA";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> codigosJerarquias = query.getResultList();
		return codigosJerarquias;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public List<String> getNivelesPadres() {
		String sql = "SELECT NUM_NIVEL FROM HE0_DT_AU_JER_GLOBAL";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> nivelesPadres = query.getResultList();
		nivelesPadres.add("NIVEL_0");
		nivelesPadres.add("NIVEL_1");
		return nivelesPadres;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public List<String> getCodigosValoresPadres() {
		String sql = "SELECT COD_VALOR_PADRE FROM HE0_DT_AU_JER_GLOBAL";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> codigosValoresPadres = query.getResultList();
		codigosValoresPadres.add("VALOR_PADRE_0");
		codigosValoresPadres.add("VALOR_PADRE_1");
		return codigosValoresPadres;
	}

}
