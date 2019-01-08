import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { forEach } from '@angular/router/src/utils/collection';
import { Observable } from 'rxjs';

@Injectable()
export abstract class GenericHttpRequestService {

  protected DEFAULT_PAGE_SIZE = '10';

  constructor(protected _http: HttpClient) { }

  abstract getServiceBasePath(): string;

  protected getAll() {
    const url = this.getServiceBasePath();
    return this._http.get(url);
  }

  protected query(pageNumber: number, pageSize?: number, queryData?: any) {
    const url = `${ this.getServiceBasePath() }/query`;
    const pageNumberForBackend = (pageNumber - 1).toString();
    const pageSizeForBackend = pageSize ? pageSize.toString() : this.DEFAULT_PAGE_SIZE;
    const params = this.constructHttpParameters({ page: pageNumberForBackend, pageSize: pageSizeForBackend, ...queryData});
    return this._http.get(url, {params : params});
  }

  protected constructHttpParameters(data: any): HttpParams {
    let params = new HttpParams();
    const objectFields = Object.keys(data);
    objectFields.forEach(objectField => {
      const fieldData = data[objectField];
      if (fieldData !== null && fieldData !== undefined && (fieldData !== '')) {
        params = params.append(objectField, fieldData.toString());
      }
    });
    return params;
  }

  protected create(data: any): Observable<any> {
    return this._http.post(this.getServiceBasePath(), data);
  }

  protected update(data: any): any {
    return this._http.put(this.getServiceBasePath(), data);
  }

}
