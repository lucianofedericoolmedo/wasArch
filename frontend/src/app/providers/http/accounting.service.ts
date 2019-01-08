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
export class AccountingService extends GenericHttpRequestService {
  handleError: HandleError;

  constructor(public http: HttpClient, public httpErrorHandler: HttpErrorHandler) {
    super(http);
    this.handleError = httpErrorHandler.createHandleError('AccountingService');
  }

  getServiceBasePath(): string {
    return 'congelamientos';
  }

  public getFechasCongelamiento() {
    const url = `${ this.getServiceBasePath() }/fechas-congelamientos`;
    return this.http.get(url);
  }

  public altairFilterData() {
    const url = `${ this.getServiceBasePath() }/altair`;
    return this.http.get(url);
  }

  public subsidiaryFilterData() {
    const url = `${ this.getServiceBasePath() }/filial`;
    return this.http.get(url);
  }

  public queryAltair(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${this.getServiceBasePath()}/altair`;
    const pageNumberForBackend = pageNumber.toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  public querySubsidiary(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${this.getServiceBasePath()}/filial`;
    const pageNumberForBackend = pageNumber.toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  public download(urlPath: string, queryData?: any) {
    const url = `${this.getServiceBasePath()}/${urlPath}/file`;
    const getfileheaders = new HttpHeaders().set('Accept', 'application/vnd.ms-excel, text/plain, */*');
    const params = this.constructHttpParameters({...queryData});
    return this.http.get(url, {responseType: 'blob', headers: getfileheaders, params: params});
  }

}
