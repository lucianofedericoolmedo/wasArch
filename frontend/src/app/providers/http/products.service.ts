import { Injectable } from '@angular/core';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../../interfaces/product.interface';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductsService extends GenericHttpRequestService {
  handleError: HandleError;

  constructor(public http: HttpClient, public httpErrorHandler: HttpErrorHandler) {
    super(http);
    this.handleError = httpErrorHandler.createHandleError('ProductsService');
  }

  getServiceBasePath(): string {
    return 'productos';
  }

  public getAllProducts() {
    return super.getAll();
  }

  public queryProducts(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${ this.getServiceBasePath() }/recupera-prod`;
    const pageNumberForBackend = pageNumber.toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  public queryAplicativosNormalizados(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${ this.getServiceBasePath() }/aplicativos-norm/query`;
    const pageNumberForBackend = pageNumber.toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  createProduct(product: Product): Promise<any> {
    return super.create(product)
      .toPromise();
      // .then(data => console.log('success ', data))
      // .catch(data => console.log('error ', data));
  }

  createNormalizados(products: Product[]): Promise<any> {
    let url = `${ this.getServiceBasePath() }/normalizados`;
    return this.http.post(url, products).toPromise();
  }

  update(product: Product): Promise<any> {
    return super.update(product)
      .toPromise();
      // .then(data => console.log('success ', data))
      // .catch(data => console.log('error ', data));
  }

  getProductoGestion(isOperacional): Observable<any> {
    const url = `${ this.getServiceBasePath() }/recupera-producto-gest`;
    const params = this.constructHttpParameters({ isOperacional: isOperacional });
    return this.http.get(url, {params: params});
  }

  getSubproductoGestion(codigoProducto): Observable<any> {
    const url = `${ this.getServiceBasePath() }/recupera-subproducto-gest`;
    const params = this.constructHttpParameters({ codigoProducto: codigoProducto });
    return this.http.get(url, {params: params});
  }

  getAplicativosNormalizados(): Observable<any> {
    const url = `${ this.getServiceBasePath() }/recupera-aplicativos-norm`;
    const params = this.constructHttpParameters({ });
    return this.http.get(url, {params: params});
  }

  download(queryObject: any) {
    const url = `${ this.getServiceBasePath() }/file`;
    const getfileheaders = new HttpHeaders().set('Accept', 'application/vnd.ms-excel, text/plain, */*');
    const params = this.constructHttpParameters({ ...queryObject});
    return this.http.get(url, {responseType: 'blob', headers: getfileheaders, params: params});
  }

  getProductoAplicativoFuenteFile(queryObject: any) {
    const url = `${ this.getServiceBasePath() }/aplicativos-norm/descarga-xls`;
    const getfileheaders = new HttpHeaders().set('Accept', 'application/vnd.ms-excel, text/plain, */*');
    const params = this.constructHttpParameters({ ...queryObject});
    return this.http.get(url, {responseType: 'blob', headers: getfileheaders, params: params});
  }

}
