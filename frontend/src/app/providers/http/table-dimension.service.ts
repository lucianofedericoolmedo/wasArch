import { Injectable } from '@angular/core';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class TablesService extends GenericHttpRequestService {
  handleError: HandleError;

  constructor(public http: HttpClient, public httpErrorHandler: HttpErrorHandler) {
    super(http);
    this.handleError = httpErrorHandler.createHandleError('TablesService');
  }

  getServiceBasePath(): string {
    return 'tablas';
  }

  public getAllUsers() {
    return super.getAll();
  }

  public queryDimensionCenter(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${ this.getServiceBasePath() }/dimension/centro/query`;
    const pageNumberForBackend = pageNumber.toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  public queryAuxiliaryAccountTyping(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${ this.getServiceBasePath() }/auxiliar/tipificacion-cuentas/query`;
    const pageNumberForBackend = pageNumber.toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  public getAuxiliaryAccountTypingSelectorsValues() {
    const url = `${ this.getServiceBasePath() }/auxiliar/tipificacion-cuentas/values`;
    return this.http.get(url);
  }

  public getAuxiliaryAccountTypingFile(queryObject: any) {
    const url = `${ this.getServiceBasePath() }/auxiliar/tipificacion-cuentas/archivo`;
    const getfileheaders = new HttpHeaders().set('Accept', 'application/vnd.ms-excel, text/plain, */*');
    const params = this.constructHttpParameters({ ...queryObject});
    return this.http.get(url, {responseType: 'blob', headers: getfileheaders, params: params});
  }

}
