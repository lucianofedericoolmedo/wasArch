export interface Product {
    codigoPais?: String;
    codigoSistemaOrigen?: String;
    codigoProductoOperacional: String;
    codigoSubproductoOperacional: String;
    codigoProductoNormalizado: String;
    codigoSubproductoNormalizado: String;
    fechaAlta: Date;
    fechaBaja: Date;
    descripcion: String;
    productoDestinatario: String;
}
