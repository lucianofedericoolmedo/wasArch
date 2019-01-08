package com.isban.javaapps.reporting.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.dto.ProductoDTO;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.util.DateUtil;
import com.isban.javaapps.reporting.util.ExcelFileUtil;
import com.isban.javaapps.reporting.util.Object2ObjectNodeMapping;

@Service
public class ProductoService extends PaginatedQueryService {
    
	private static final Logger LOGGER = Logger.getLogger(ProductoService.class.getName());

	@Autowired
    private EntityManager entityManager;
	
	private String codigoPais = "54";
	
	/***************************** Producto *******************************/
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    public Object getRecuperaProductosGest() {
	    String serie = "NULL";
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Recupera_Productos_Gest")
                .registerStoredProcedureParameter(1, String.class, 
                    ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)
                .registerStoredProcedureParameter(3, Long.class, 
                        ParameterMode.OUT)
                .registerStoredProcedureParameter(4, String.class, 
                        ParameterMode.OUT)
                .registerStoredProcedureParameter(5, String.class, 
                    ParameterMode.IN)
                
                .setParameter(1, serie)
                .setParameter(5, codigoPais);
        
        return getResult(query);
    }

    private Object getResult(StoredProcedureQuery query) {
        boolean isResult = query.execute();
        Object result = null;
        if(isResult) {
            result = query.getResultList();
        }
        entityManager.close();

        return result;
    }

	@Transactional(propagation=Propagation.REQUIRES_NEW)
    public Object getRecuperaSubproductosGest(String codigoProducto) {
	    String serie = "NULL";
        codigoProducto = codigoProducto == null ? "NULL" : codigoProducto;
          StoredProcedureQuery query = entityManager
          .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Recupera_Subproductos_Gest")
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

          .setParameter(1, serie)
          .setParameter(5, codigoPais)
          .setParameter(6, codigoProducto);

        Object result = getResult(query);
        return result;
    }

	public Object getRecuperaProductosNormalizado() {
        String serie = "NULL";
        String codigoAplicativo = "NULL";
        String codigoSubproducto = "NULL";
        
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Recupera_Productos_Norm")
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
                
                .setParameter(1, serie)
                .setParameter(5, codigoPais)
                .setParameter(6, codigoAplicativo)
                .setParameter(7, codigoSubproducto);
        
        return getResult(query);
    }
	
	public Object getRecuperaSubproductosNormalizado(String codigoProducto) {
	    String serie = "NULL";
        String codigoAplicativo = "NULL";
        codigoProducto = codigoProducto == null ? "NULL" : codigoProducto;
        
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Recupera_Subproductos_Norm")
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
                
                .setParameter(1, serie)
                .setParameter(5, codigoPais)
                .setParameter(6, codigoAplicativo)
                .setParameter(7, codigoProducto);
        
        return getResult(query);
    }
	
	
	
	@SuppressWarnings("unchecked")
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public PageResponse<ProductoDTO> getRecuperaProdGestion(
            String codigoProducto, 
            String codigoSubproducto,
            String rowPage,
            String page) {
	    String serie = "NULL";
        codigoProducto = codigoProducto == null ? "NULL" : codigoProducto;
        codigoSubproducto = codigoSubproducto == null ? "NULL" : codigoSubproducto;
        rowPage = rowPage == null ? "10" : rowPage;
        page = page == null ? "1" : page;
        
       StoredProcedureQuery query = entityManager
          .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Recupera_Prod_Gestion")
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
                  ParameterMode.OUT)
          
          .setParameter(1, serie)
          .setParameter(5, codigoPais)
          .setParameter(6, codigoProducto)
          .setParameter(7, codigoSubproducto)
          .setParameter(8, rowPage)
          .setParameter(9, page);
        
        boolean isResult = query.execute();
        List<Object[]> result = null;
        if(isResult) {
            result = query.getResultList();
        }
        Long totalSize = Long.parseLong(query.getOutputParameterValue(10).toString());
        entityManager.close();

        List<ProductoDTO> productList = new LinkedList<ProductoDTO>();
        for (Object[] baseProduct : result) {
			productList.add(new ProductoDTO(baseProduct, true));
		}
        
        return new PageResponse<ProductoDTO>(productList, new Integer(page), 10, totalSize);
    }
	
	@SuppressWarnings("unchecked")
    @Transactional(propagation=Propagation.REQUIRES_NEW)
	public PageResponse<ProductoDTO> getRecuperaProdNormalizado(
	        String codigoApli,
            String codigoProducto, 
            String codigoSubproducto, 
            String rowPage, 
            String page) {
	    String serie = "NULL";
        codigoApli = codigoApli == null ? "NULL" : codigoApli;
        codigoProducto = codigoProducto == null ? "NULL" : codigoProducto;
        codigoSubproducto = codigoSubproducto == null ? "NULL" : codigoSubproducto;
        rowPage = rowPage == null ? "0" : rowPage;
        page = page == null ? "10" : page;
        
       StoredProcedureQuery query = entityManager
          .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Recupera_Prod_Normalizado")
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
                  ParameterMode.OUT)
          
          .setParameter(1, serie)
          .setParameter(5, codigoPais)
          .setParameter(6, codigoApli)
          .setParameter(7, codigoProducto)
          .setParameter(8, codigoSubproducto)
          .setParameter(9, rowPage)
          .setParameter(10, page);
        
        boolean isResult = query.execute();
        List<Object[]> result = null;
        if(isResult) {
            result = query.getResultList();
        }
        Long totalSize = Long.parseLong(query.getOutputParameterValue(11).toString());
        entityManager.close();

        List<ProductoDTO> productList = new LinkedList<ProductoDTO>();
        for (Object[] baseProduct : result) {
            productList.add(new ProductoDTO(baseProduct, false));
        }
        
        return new PageResponse<ProductoDTO>(productList, new Integer(page), 10, totalSize);
    }

	@Transactional(propagation=Propagation.REQUIRES_NEW)
    public Object getRecuperaAplicativosNorm(
            String codigoProducto, 
            String codigoSubproducto,
            String page,
            String pageSize
        ) {
	    String serie = "NULL";
        codigoProducto = codigoProducto == null ? "NULL" : codigoProducto;
        codigoSubproducto = codigoSubproducto == null ? "NULL" : codigoSubproducto;
        page = page == null ? "1" : page;
        pageSize = pageSize == null ? "10" : pageSize;
        StoredProcedureQuery query = this.getStoredProcedureQueryAplicativosNorm(serie, page, pageSize, codigoProducto, codigoSubproducto);
        Object2ObjectNodeMapping mapping = new Object2ObjectNodeMapping() {
			@Override
			public ObjectNode map(Object[] object) {
				ObjectNode jsonObject = factory.objectNode();
				Integer index = 0;
				jsonObject.put("codigoProductoOperacional",    (String) object[index++]);
				jsonObject.put("codigoSubproductoOperacional", (String) object[index++]);
				jsonObject.put("codigoProductoNormalizado",    (String) object[index++]);
				jsonObject.put("codigoSubproductoNormalizado", (String) object[index++]);
				jsonObject.put("aplicativoFuente",             (String) object[index++]);
				jsonObject.put("codigoPais",                   (BigDecimal) object[index++]);
				jsonObject.put("descripcion",                  (String) object[index++]);
				String fechaAlta = object[index++] != null ? String.valueOf(object[index++]) : "";
				jsonObject.put("fechaAlta",                    fechaAlta.split(" ")[0]);
				String fechaBaja = object[index++] != null ? String.valueOf((BigDecimal) object[index++]) : "";
				jsonObject.put("fechaBaja",                    fechaBaja);
				return jsonObject;
			}
		};
        return super.getPaginatedResult(query, 9, 8, 10, mapping, entityManager);
    }

	public StoredProcedureQuery getStoredProcedureQueryAplicativosNorm(String serie, String page, String pageSize, String codigoProducto, String codigoSubproducto) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Recupera_Prod_Gestion")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(10, String.class, ParameterMode.OUT)
                .setParameter(1, serie)
                .setParameter(5, codigoPais)
                .setParameter(6, codigoProducto)
                .setParameter(7, codigoSubproducto)
                .setParameter(8, pageSize)
                .setParameter(9, page);
        return query;
	}

	public List<Object> getRecuperaAplicativosNormAux(String codigoProducto, String codigoSubproducto) {
		String sql = "SELECT DISTINCT A.COD_APLICATIVO_FUENTE " + 
	       "FROM HE0_DT_AU_APLICATIVO_FUENTE A, HE0_DT_AU_NORMALIZ_PRODUCTOS NP " +
	       "WHERE A.COD_PAIS = '54' " + 
	       "AND A.FEC_BAJA IS NULL " +
	       "AND NP.COD_PAIS=A.COD_PAIS " +
	       "AND NP.COD_APLICATIVO_FUENTE=A.COD_APLICATIVO_FUENTE"; 
		if(codigoSubproducto != null)
			sql += " AND NP.COD_PRODUCTO_OPERAC = '" + codigoProducto + "'";
		if(codigoSubproducto != null)
			sql += " AND NP.COD_SUBPRODU_OPERAC = '" + codigoSubproducto + "'";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object> lista = query.getResultList();

		return lista;
	}

	public File generarArchivoRecuperaAplicativosNorm(String codigoProducto, String codigoSubproducto) {
        codigoProducto = codigoProducto == null ? "NULL" : codigoProducto;
        codigoSubproducto = codigoSubproducto == null ? "NULL" : codigoSubproducto;
		StoredProcedureQuery storedProcedureQueryAplicativosNorm = this.getStoredProcedureQueryAplicativosNorm("NULL", "1", "1048576", codigoProducto, codigoSubproducto);
		return super.getFile(storedProcedureQueryAplicativosNorm, entityManager);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
    public Object insertaProducto(ProductoDTO dto) {
		String sql = "SELECT * FROM HE0_DM_PRODUCTO WHERE COD_PAIS = '54' AND COD_PRODUCTO = '" + dto.getCodigoProducto()+ 
				"' AND COD_SUBPRODU = '" +  dto.getCodigoSubproducto() + "'";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object> lista = query.getResultList();
		if(lista.isEmpty()) {
			return this.insertarProducto(dto);
		}else {
			throw new RuntimeException("Ya existe un producto con ese código");
		}
    }
	
    private Object insertarProducto(ProductoDTO dto) {
    	String serie = "NULL";
        String codigoProducto = dto.getCodigoProducto() == null ? "NULL" : dto.getCodigoProducto();
        String codigoSubproducto = dto.getCodigoSubproducto() == null ? "NULL" : dto.getCodigoSubproducto();
        String fechaAlta = dto.getFechaAlta() == null ? "NULL" : dto.getFechaAlta();
        String fechaBaja = dto.getFechaBaja() == null ? "NULL" : dto.getFechaBaja();
        String usuario = dto.getUsuario() == null ? "NULL" : dto.getUsuario();
        String descripcion = dto.getDescripcion() == null ? "NULL" : dto.getDescripcion();
        
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Inserta_Producto")
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
                
                .setParameter(1, serie)
                .setParameter(5, codigoPais)
                .setParameter(6, codigoProducto)
                .setParameter(7, codigoSubproducto)
                .setParameter(8, DateUtil.toDateString(fechaAlta))
                .setParameter(9, fechaBaja)
                .setParameter(10, usuario)
                .setParameter(11, descripcion);
        
        boolean isResult = query.execute();
        Object result = null;
        if(isResult) {
            result = query.getOutputParameterValue(2);
        }
        entityManager.close();

        return result;
		
	}

    protected Logger getLogger() {
        return LOGGER;
    }

    public Object insertaProductoNormalizado(ProductoDTO dto) {
		boolean existe = existeProductoNormalizado(dto);
		if(existe) {
			return this.insertarProductoNormalizado(dto);
		}else {
			throw new RuntimeException("Ya existe un producto con ese código");
		}
    }

	private boolean existeProductoNormalizado(ProductoDTO dto) {
		String codigoSistemaOrigen = dto.getCodigoSistemaOrigen() == null ? "NULL" : dto.getCodigoSistemaOrigen();
    	String sql = "SELECT * FROM HE0_DT_AU_NORMALIZ_PRODUCTOS WHERE COD_PAIS = '54' AND " + 
    			"COD_APLICATIVO_FUENTE = '" + codigoSistemaOrigen + "'  AND COD_PRODUCTO_OPERAC = '" + dto.getCodigoProducto() + 
    			"' AND COD_SUBPRODU_OPERAC = '" + dto.getCodigoSubproducto() + "'";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object> lista = query.getResultList();
		return !lista.isEmpty();
	}
    
    private Object insertarProductoNormalizado(ProductoDTO dto) {
    	String serie = "NULL";
        String codigoSistemaOrigen = dto.getCodigoSistemaOrigen() == null ? "NULL" : dto.getCodigoSistemaOrigen();
        String codigoProductoNormalizado = dto.getCodigoProductoNormalizado() == null ? "NULL" : dto.getCodigoProductoNormalizado();
        String codigoSubproductoNormalizado = dto.getCodigoSubproductoNormalizado() == null ? "NULL" : dto.getCodigoSubproductoNormalizado();
        String codigoProducto = dto.getCodigoProducto() == null ? "NULL" : dto.getCodigoProducto();
        String codigoSubproducto = dto.getCodigoSubproducto() == null ? "NULL" : dto.getCodigoSubproducto();
        String fechaAlta = dto.getFechaAlta() == null ? "NULL" : dto.getFechaAlta();
        String fechaBaja = dto.getFechaBaja() == null ? "NULL" : dto.getFechaBaja();
        String usuario = dto.getUsuario() == null ? "NULL" : dto.getUsuario();
        
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Inserta_Prod_Normalizado")
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
                
                .setParameter(1, serie)
                .setParameter(5, codigoPais)
                .setParameter(6, codigoSistemaOrigen)
                .setParameter(7, codigoProducto)
                .setParameter(8, codigoSubproducto)
                .setParameter(9, codigoProductoNormalizado)
                .setParameter(10, codigoSubproductoNormalizado)
                .setParameter(11, DateUtil.toDateString(fechaAlta))
                .setParameter(12, "")
                .setParameter(13, usuario)
                .setParameter(14, "");
        
        boolean isResult = query.execute();
        Object result = null;
        if(isResult) {
            result = query.getOutputParameterValue(2);
        }
        entityManager.close();

        return result;
	}

	public Object modificarProducto(ProductoDTO dto) {
        String serie = "NULL";
        String codigoSistemaOrigen = dto.getCodigoSistemaOrigen() == null ? "NULL" : dto.getCodigoSistemaOrigen();
        String prodOperac = dto.getCodigoProducto();
        String subProdOper = dto.getCodigoSubproducto();
        String desProducto = dto.getDescripcion();
        String desCtaCru = "CTA";
        String codigoProducto = dto.getCodigoProductoNormalizado() == null ? "NULL" : dto.getCodigoProductoNormalizado();
        String codigoSubproducto = dto.getCodigoSubproductoNormalizado() == null ? "NULL" : dto.getCodigoSubproductoNormalizado();
        
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_ODS_PRODUCTOS.Modifica_Producto")
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
                
                .setParameter(1, serie)
                .setParameter(5, codigoPais)
                .setParameter(6, codigoSistemaOrigen)
                .setParameter(7, prodOperac)
                .setParameter(8, subProdOper)
                .setParameter(9, desProducto)
                .setParameter(10, desCtaCru)
                .setParameter(11, codigoProducto)
                .setParameter(12, codigoSubproducto);
        
        boolean isResult = query.execute();
        Object result = null;
        if(isResult) {
            result = query.getOutputParameterValue(2);
        }
        entityManager.close();

        return result;
    }

    public PageResponse<ProductoDTO> getProductos(String codigoApli,
            String codigoProducto, String codigoSubproducto, String rowPage, String page, boolean isOperacional) {
        PageResponse<ProductoDTO> result;
        if(isOperacional) {
           result = this.getRecuperaProdGestion(codigoProducto, codigoSubproducto, rowPage, page);
        }else {
           result = this.getRecuperaProdNormalizado(codigoApli, codigoProducto, codigoSubproducto, rowPage, page);
        }
        return result;
    }

	public Object getRecuperaProductos(boolean isOperacional) {
		Object result = null;
		if(isOperacional) {
	           result = this.getRecuperaProductosGest();
	        }else {
	           result = this.getRecuperaProductosNormalizado();
	        }
	        return result;
	}

	public Object getRecuperaSubproductos(String codigoProducto,
			boolean isOperacional) {
		Object result = null;
		if(isOperacional) {
	           result = this.getRecuperaSubproductosGest(codigoProducto);
	        }else {
	           result = this.getRecuperaSubproductosNormalizado(codigoProducto);
	        }
	        return result;
	}

	public Object insertarProductoSegunDatos(ProductoDTO productodto) {
		if((productodto.getCodigoProductoNormalizado() == null || productodto.getCodigoProductoNormalizado().isEmpty())
				&& (productodto.getCodigoSubproductoNormalizado() == null || productodto.getCodigoSubproductoNormalizado().isEmpty())) {
			return this.insertaProducto(productodto);
		}else {
			return this.insertaProductoNormalizado(productodto);
		}
	}
	
	/**
	 * Inserta o actualiza producto normalizado desde aplicativo fuente
	 * @param dtos
	 * @return
	 */
	public Object insertarProductosNormalizados(List<ProductoDTO> dtos) {
		List<Object> result = new LinkedList<Object>();
		if(dtos == null || dtos.isEmpty()) {
			throw new RuntimeException("No se han enviado productos para grabar.");
		}else {
			for(ProductoDTO dto : dtos) {
				boolean existe = this.existeProductoNormalizado(dto);
				if(existe) {
					this.modificarProducto(dto);
				}else {
					this.insertarProductoNormalizado(dto);
				}
			}
		}
		return result;
	}

	public File generarArchivoProductos(String codigoApli, String codigoProducto, String codigoSubproducto, boolean isOperacional) {
		File generatedFile = null;
		PageResponse<ProductoDTO> result = null ; 
		LinkedList<String> header = new LinkedList<String>();
		header.add("codigoPais");
		header.add("codigoProducto");
		header.add("codigoSubproducto");
		header.add("codigoProductoNormalizado");
		header.add("codigoSubproductoNormalizado");
		header.add("fechaAlta");
		header.add("fechaBaja");
		header.add("usuario");
		header.add("descripcion");
		header.add("codigoSistemaOrigen");
		header.add("productoDestinatario");
		header.add("codigoAplicativo");
		if(isOperacional) {
			Long totalSize = this.getRecuperaProdGestion(codigoProducto, codigoSubproducto, "10", "1").getTotalSize();
			result = this.getRecuperaProdGestion(codigoProducto, codigoSubproducto, totalSize.toString(), "1");
		}else {
			Long totalSize = this.getRecuperaProdNormalizado(codigoApli, codigoProducto, codigoSubproducto, "10", "1").getTotalSize();
			result = this.getRecuperaProdNormalizado(codigoApli, codigoProducto, codigoSubproducto, totalSize.toString(), "1");
		}
		try {
			LinkedList<Object> elements = new LinkedList<Object>();
			for(ProductoDTO item : result.getItems()) {
				elements.add(item);
			}
			generatedFile = ExcelFileUtil.generate(header, elements, header);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generatedFile;
	}
	
	/***************************** FIN Producto *******************************/

}
