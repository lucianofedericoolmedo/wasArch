import { Injectable } from '@angular/core';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProcessesLogService extends GenericHttpRequestService {
  handleError: HandleError;

  constructor(public http: HttpClient, public httpErrorHandler: HttpErrorHandler) {
    super(http);
    this.handleError = httpErrorHandler.createHandleError('ProcessesLogService');
  }

  getServiceBasePath(): string {
    return 'log-procesos';
  }

  public getAllProducts() {
    return super.getAll();
  }

  public queryLogs(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${ this.getServiceBasePath() }/ods/query`;
    const pageNumberForBackend = (pageNumber).toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  public getProcesosOdsFiltros(): Observable<any> {
    const url = `${ this.getServiceBasePath() }/ods/filtros`;
    return this.http.get(url);
  }

  getOdsProcessExcelReport(): Observable<any> {
    const url = `${ this.getServiceBasePath() }/ods/descarga-xls`;
    const getfileheaders = new HttpHeaders().set('Accept', 'application/vnd.ms-excel, text/plain, */*');
    return this.http.get(url, {responseType: 'blob', headers: getfileheaders});
  }

  public queryLogProcessApu(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${ this.getServiceBasePath() }/apu/query`;
    const pageNumberForBackend = pageNumber.toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  public getProcesosApuFiltros(): Observable<any> {
    const url = `${ this.getServiceBasePath() }/apu/filtros`;
    return this.http.get(url);
  }

  getApuProcessExcelReport(): Observable<any> {
    const url = `${ this.getServiceBasePath() }/apu/descarga-xls`;
    const getfileheaders = new HttpHeaders().set('Accept', 'application/vnd.ms-excel, text/plain, */*');
    return this.http.get(url, {responseType: 'blob', headers: getfileheaders});
  }

}
