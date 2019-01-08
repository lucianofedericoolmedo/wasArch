import { Injectable } from '@angular/core';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RejectionJoin } from '../../interfaces/rejections-join.interface';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RejectionsJoinService extends GenericHttpRequestService {
  handleError: HandleError;

  constructor(public http: HttpClient, public httpErrorHandler: HttpErrorHandler) {
    super(http);
    this.handleError = httpErrorHandler.createHandleError('RejectionsJoinService');
  }

  getServiceBasePath(): string {
    return 'rechazos-join';
  }

  public queryRejected(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${ this.getServiceBasePath() }`;
    const pageNumberForBackend = (pageNumber).toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  getFilters(): Observable<any> {
    const url = `${ this.getServiceBasePath() }/filtro`;
    const params = this.constructHttpParameters({ });
    return this.http.get(url, {params: params});
  }

  getExcelReport(): Observable<any> {
    const url = `${ this.getServiceBasePath() }/file`;
    const getfileheaders = new HttpHeaders().set('Accept', 'application/vnd.ms-excel, text/plain, */*');
    return this.http.get(url, {responseType: 'blob', headers: getfileheaders});
  }

}
