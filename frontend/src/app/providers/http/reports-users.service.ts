import { Injectable } from '@angular/core';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ReportsUser } from '../../interfaces/reports-user.interface';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportUsersService extends GenericHttpRequestService {
  handleError: HandleError;

  constructor(public http: HttpClient, public httpErrorHandler: HttpErrorHandler) {
    super(http);
    this.handleError = httpErrorHandler.createHandleError('ReportUsersService');
  }

  getServiceBasePath(): string {
    return 'informe-usuarios';
  }

  public getAllUsers() {
    return super.getAll();
  }

  public queryUsers(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${ this.getServiceBasePath() }/query`;
    const pageNumberForBackend = pageNumber.toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this.http.get(url, {params : params});
  }

  createUser(user: ReportsUser): Promise<any> {
    return super.create(user).toPromise();
  }


  update(user: ReportsUser): Promise<any> {
    return super.update(user).toPromise();
  }

  // TODO: CHANGE FOR APPROPIATE ONES
  getUseroGestion(isOperacional): Observable<any> {
    const url = `${ this.getServiceBasePath() }/recupera-usero-gest`;
    const params = this.constructHttpParameters({ isOperacional: isOperacional });
    return this.http.get(url, {params: params});
  }

  getSubuseroGestion(codigoUsero): Observable<any> {
    const url = `${ this.getServiceBasePath() }/recupera-subusero-gest`;
    const params = this.constructHttpParameters({ codigoUsero: codigoUsero });
    return this.http.get(url, {params: params});
  }

  getAplicativosNormalizados(): Observable<any> {
    const url = `${ this.getServiceBasePath() }/recupera-aplicativos-norm`;
    const params = this.constructHttpParameters({ });
    return this.http.get(url, {params: params});
  }

  getExcelReport(): Observable<any> {
    const url = `${ this.getServiceBasePath() }/file`;
    const getfileheaders = new HttpHeaders().set('Accept', 'application/vnd.ms-excel, text/plain, */*');
    return this.http.get(url, {responseType: 'blob', headers: getfileheaders});
  }

}
