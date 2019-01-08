import { Injectable } from '@angular/core';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class ConciliationService extends GenericHttpRequestService {
  handleError: HandleError;

  constructor(public http: HttpClient, public httpErrorHandler: HttpErrorHandler) {
    super(http);
    this.handleError = httpErrorHandler.createHandleError('ProductsService');
  }

  getServiceBasePath(): string {
    return 'conciliaciones';
  }

  // Mensual con centro requests
  public queryMensualConCentro(pageNumber: number, pageSize?: number, queryData?: any) {
    return this.queryConciliacion('mensual-con-centro', pageNumber, pageSize, queryData);
  }

  public monthlyWithCenterFilteringData() {
    return this.filterConciliacion('mensual-con-centro');
  }

  public downloadMensualConCentro(queryData?: any) {
    return this.downloadConciliacion('mensual-con-centro', queryData);
  }

  // Mensual sin centro requests
  public queryMensualSinCentro(pageNumber: number, pageSize?: number, queryData?: any) {
    return this.queryConciliacion('mensual-sin-centro', pageNumber, pageSize, queryData);
  }

  public monthlyWithoutCenterFilteringData() {
    return this.filterConciliacion('mensual-sin-centro');
  }

  public downloadMensualSinCentro(queryData?: any) {
    return this.downloadConciliacion('mensual-sin-centro', queryData);
  }

  // Diaria con centro requests
  public queryDiarioConCentro(pageNumber: number, pageSize?: number, queryData?: any) {
    return this.queryConciliacion('diaria-con-centro', pageNumber, pageSize, queryData);
  }

  public dailyWithCenterFilteringData() {
    return this.filterConciliacion('diaria-con-centro');
  }

  public downloadDiarioConCentro(queryData?: any) {
    return this.downloadConciliacion('diaria-con-centro', queryData);
  }

  // Diaria sin centro requests
  public queryDiarioSinCentro(pageNumber: number, pageSize?: number, queryData?: any) {
    return this.queryConciliacion('diaria-sin-centro', pageNumber, pageSize, queryData);
  }

  public dailyWithoutCenterFilteringData() {
    return this.filterConciliacion('diaria-sin-centro');
  }

  public downloadDiarioSinCentro(queryData?: any) {
    return this.downloadConciliacion('diaria-sin-centro', queryData);
  }

  // Generic request methods
  public queryConciliacion(urlQuery: string, pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${this.getServiceBasePath()}/${urlQuery}/query`;
    const pageNumberForBackend = pageNumber.toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  public filterConciliacion(urlPath: string) {
    const url = `${this.getServiceBasePath()}/${urlPath}/filtro`;
    return this.http.get(url);
  }

  public downloadConciliacion(urlPath: string, queryData?: any) {
    const url = `${this.getServiceBasePath()}/${urlPath}/descarga-xls`;
    const getfileheaders = new HttpHeaders().set('Accept', 'application/vnd.ms-excel, text/plain, */*');
    const params = this.constructHttpParameters({...queryData});
    return this.http.get(url, {responseType: 'blob', headers: getfileheaders, params: params});
  }

}
